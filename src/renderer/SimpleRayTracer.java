package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static primitives.Util.alignZero;

/**
 * SimpleRayTracer is a basic implementation of the RayTracerBase class,
 * responsible for tracing rays in a scene and determining the color of the closest intersection point.
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * filed to move the ray
     */
    private static final double DELTA = 0.1;

    /**
     * The maximum level of color calculations.
     * This constant defines how many recursive color calculations can be performed.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * The minimum color coefficient for calculations.
     * This constant sets the threshold below which color calculations are considered insignificant.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    private static final double INITIAL_K = 1.0;

    /**
     * Constructs a SimpleRayTracer with the given scene.
     *
     * @param scene The scene to be ray traced.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        var point = findClosestIntersection(ray);
        return point == null ? scene.background : calcColor(point, ray);
    }


    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)).add(scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint geoPoint = findClosestIntersection(ray);
        return (geoPoint == null ? scene.background : calcColor(geoPoint, ray, level - 1, kkx)).scale(kx);
    }

    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Material material = geoPoint.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(geoPoint, ray.getDirection()), material.kT, level, k)
                .add(calcGlobalEffect(constructReflectedRay(geoPoint, ray.getDirection()), material.kR, level, k));
    }

    /**
     * Calculates the local illumination effects at a given intersection point.
     *
     * @param gp  The intersection point.
     * @param ray The ray that intersected with the point.
     * @return The color contribution from local illumination effects at the intersection point.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        Color color = gp.geometry.getEmission();
        if (nv == 0) return color;

        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Double3 ktr = transparency(gp, lightSource, l, n);
                if(!ktr.product(k).lowerThan(MIN_CALC_COLOR_K))
                {
                    Color il= lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(il.scale(calcDiffusive(material,nl)
                            .add(calcSpecular(material,n,l,nl,v))));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the specular reflection contribution using the Phong model.
     *
     * @param material The material of the intersected geometry.
     * @param n        The normal vector at the intersection point.
     * @param l        The direction vector from the light source to the intersection point.
     * @param nl       The dot product of n and l.
     * @param v        The view direction vector.
     * @return The specular reflection contribution color as Double3 (RGB).
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.add(n.scale(-2 * nl));
        double mVR = alignZero(-v.dotProduct(r));
        return mVR <= 0 ? Double3.ZERO : material.kS.scale(pow(mVR, material.Shininess));
    }

    /**
     * Calculates the diffuse reflection contribution.
     *
     * @param material The material of the intersected geometry.
     * @param nl       The dot product of n and l.
     * @return The diffuse reflection contribution color as Double3 (RGB).
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(abs(nl));
    }



    private GeoPoint findClosestIntersection(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return null;
        return ray.findClosestGeoPoint(intersections);
    }

    private Ray constructRefractedRay(GeoPoint gp, Vector v) {
        Vector n = gp.geometry.getNormal(gp.point);
        return new Ray(gp.point, v, n);
    }

    private Ray constructReflectedRay(GeoPoint gp, Vector v) {
        Vector n = gp.geometry.getNormal(v);
        double nv = n.dotProduct(v);
        if (nv == 0) return null;
        Vector vec = v.subtract(n.scale(2 * nv));
        return new Ray(gp.point, vec, n);
    }

    /**
     * Checks if a given point is unshaded by any geometry in the scene.
     *
     * @param geoPoint    the geo-point to check for shading
     * @param l           the direction vector from the light source to the point
     * @param n           the normal vector at the geo-point
     * @param lightSource source of the light
     * @return white if the point is unshaded, the color otherwise
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource lightSource, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1);  // Direction from the point towards the light source
        double nl = n.dotProduct(l);          // Dot product of the normal and the light direction

        // Adjust point slightly along the normal to avoid self-shadowing
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = geoPoint.point.add(epsVector);

        // Create a ray from the adjusted point in the direction of the light
        Ray lightRay = new Ray(point, lightDirection);

        // Find intersections of the light ray with geometries in the scene
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        Double3 ktr = Double3.ONE;
        if (intersections == null)
            return ktr;

        // The distance between lightSource and the point on the body
        double distance = lightSource.getDistance(geoPoint.point);

        // Promotes transparency
        Double3 kt = geoPoint.geometry.getMaterial().kT;

        //if one of the intersection point is before the body than return false
        for (var intersectionPoint : intersections) {
            if (geoPoint.point.distance(intersectionPoint.point) < distance) {
                ktr = ktr.product(intersectionPoint.geometry.getMaterial().kT);
                if (ktr.lowerThan(DELTA))
                    break;
            }
        }
        return ktr;

    }


}


