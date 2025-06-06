package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * SimpleRayTracer is a basic implementation of the RayTracerBase class,
 * responsible for tracing rays in a scene and determining the color of the closest intersection point.
 */
public class SimpleRayTracer extends RayTracerBase {

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

    /**
     * Initial attenuation factor for calculating light intensity.
     * This constant is used in various lighting calculations to initialize the attenuation factor.
     */
    private static final Double3 INITIAL_K = Double3.ONE;

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

    /**
     * Calculate the color at a specific point considering the ray and the maximum recursion level.
     *
     * @param geoPoint the intersection point of the geometry and the ray.
     * @param ray      the ray that intersects the geometry.
     * @return the calculated color including ambient light.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculate the color at a specific point considering the ray, recursion level, and attenuation factor.
     *
     * @param geoPoint the intersection point of the geometry and the ray.
     * @param ray      the ray that intersects the geometry.
     * @param level    the current recursion level.
     * @param k        the attenuation factor.
     * @return the calculated color.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    /**
     * Calculate the global effect of a ray considering the attenuation factor and recursion level.
     *
     * @param ray   the ray to calculate the global effect for.
     * @param kx    the material's attenuation factor.
     * @param level the current recursion level.
     * @param k     the accumulated attenuation factor.
     * @return the calculated color including global effects.
     */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint geoPoint = findClosestIntersection(ray);
        return (geoPoint == null ? scene.background : calcColor(geoPoint, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * Calculate the global effects of reflection and refraction at a specific point.
     *
     * @param geoPoint the intersection point of the geometry and the ray.
     * @param ray      the ray that intersects the geometry.
     * @param level    the current recursion level.
     * @param k        the attenuation factor.
     * @return the calculated color including reflection and refraction effects.
     */
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
     * @param k   double3 for the production
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
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color il = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(il.scale(calcDiffusive(material, nl)
                            .add(calcSpecular(material, n, l, nl, v))));
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

    /**
     * Finds the closest intersection point between a ray and the scene's geometries.
     *
     * @param ray the ray for which to find the closest intersection
     * @return the closest GeoPoint intersection, or null if no intersections are found
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);
    }

    /**
     * Constructs a refracted ray at the given GeoPoint.
     *
     * @param gp the GeoPoint where the ray refracts
     * @param v  the direction of the incoming ray
     * @return the refracted ray
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v) {
        Vector n = gp.geometry.getNormal(gp.point);
        return new Ray(gp.point, v, n);
    }

    /**
     * Constructs a reflected ray at the given GeoPoint.
     *
     * @param gp the GeoPoint where the ray reflects
     * @param v  the direction of the incoming ray
     * @return the reflected ray, or null if the incoming ray is parallel to the surface
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector v) {
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = n.dotProduct(v);
        if (isZero(nv)) return null;
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
        // Create a ray from the adjusted point in the direction of the light
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);

        Double3 ktr = Double3.ONE;
        // Find intersections of the light ray with geometries in the scene
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return ktr;

        // The distance between lightSource and the point on the body
        double distance = lightSource.getDistance(geoPoint.point);
        //if one of the intersection point is before the body than return false
        for (var intersectionPoint : intersections) {
            if (geoPoint.point.distance(intersectionPoint.point) < distance) {
                ktr = ktr.product(intersectionPoint.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K))
                    return Double3.ZERO;
            }
        }
        return ktr;
    }

}


