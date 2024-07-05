package renderer;

import geometries.Geometry;
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
        var point = ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
        return point == null ? scene.background : calcColor(point, ray);
    }



    /**
     * Calculates the color at a certain point of intersection.
     *
     * @param p intersection point
     * @return color at the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)).add(scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    private Color calcGlobalEffect(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Material material = geoPoint.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(geoPoint,ray),material.kR,level,k)
                .add(calcGlobalEffect(constructReflectedRay(geoPoint,ray),material.kt,level,k));
    }

    private Color calcGlobalEffects(Ray ray, int level, Double3 k,Double3 kx) {
        Double3 kkx = k.product(kx);
        if(kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint geoPoint = findClosestIntersection(ray);
        return (geoPoint == null? scene.background:calcColor(geoPoint,ray,level-1,kkx)).scale(kx);
    }

    /**
     * Calculates the local illumination effects at a given intersection point.
     *
     * @param gp  The intersection point.
     * @param ray The ray that intersected with the point.
     * @return The color contribution from local illumination effects at the intersection point.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray,double k) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        Color color = gp.geometry.getEmission();
        if (nv == 0) return color;

        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0 && unshaded(gp, l, n, lightSource)) {
                Color il = lightSource.getIntensity(gp.point);
                color = color.add(il.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
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

    /**
     * Checks if a given point is unshaded by any geometry in the scene.
     *
     * @param gp          the geo-point to check for shading
     * @param l           the direction vector from the light source to the point
     * @param n           the normal vector at the geo-point
     * @param lightSource source of the light
     * @return true if the point is unshaded, false otherwise
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {
        Vector lightDirection = l.scale(-1);  // Direction from the point towards the light source
        double nl = n.dotProduct(l);          // Dot product of the normal and the light direction

        // Adjust point slightly along the normal to avoid self-shadowing
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);

        // Create a ray from the adjusted point in the direction of the light
        Ray lightRay = new Ray(point, lightDirection);

        // Find intersections of the light ray with geometries in the scene
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        // Return true if no intersections are found, meaning the point is unshaded
        if (intersections == null)
            return true;

        // The distance between lightSource and the point on the body
        double distance = lightSource.getDistance(gp.point);

        //if one of the intersection point is before the body than return false
        for (var intersectionPoint : intersections) {
            if (gp.point.distance(intersectionPoint.point) < distance)
                return false;
        }

        // There are no intersection point between the lightSource and the body
        return true;
    }

    private GeoPoint findClosestIntersection(Ray ray){
        var intersections = scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(intersections);
    }

    private Ray constructRefractedRay(GeoPoint gp, Vector dir, Vector n) {
        return new Ray(gp.point, dir, n);
    }

    private Ray constructReflectedRay(GeoPoint gp, Vector dir, Vector n) {
        return new Ray(gp.point, dir.mirror(n, dir.dotProduct(n)), n);
    }


}


