package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static primitives.Util.alignZero;

/**
 * SimpleRayTracer is a basic implementation of the RayTracerBase class,
 * responsible for tracing rays in a scene and determining the color of the closest intersection point.
 */
public class SimpleRayTracer extends RayTracerBase {

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
     * Calculates the color at a given intersection point.
     *
     * @param geoPoint The intersection point.
     * @param ray      The intersection ray.
     * @return The color at the intersection point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(geoPoint, ray));
    }

    /**
     * Calculates the local illumination effects at a given intersection point.
     *
     * @param gp  The intersection point.
     * @param ray The ray that intersected with the point.
     * @return The color contribution from local illumination effects at the intersection point.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
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
}
