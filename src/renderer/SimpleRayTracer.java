package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;


import java.util.Formattable;
import java.util.LinkedList;
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
     * Constructs a SimpleRayTracer with the given scene.
     *
     * @param scene The scene to be ray traced.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }


    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);
        return points == null ? scene.background : calcColor(ray.findClosestGeoPoint(points),ray);
    }

    /**
     * Calculates the color at a given intersection point.
     *
     * @param geoPoint The intersection point.
     * @return The color at the intersection point.
     */
    private Color calcColor(GeoPoint geoPoint,Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(geoPoint, ray));
    }

    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        Color color = gp.geometry.getEmission();
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for(LightSource lightSource : scene.lights){
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl*nv > 0){
                Color il = lightSource.getIntensity(gp.point);
                color = color.add(il.scale(calcDiffusive(material,nl).add(calcSpecular(material,n,l,nl,v))));
            }
        }
        return color;
    }

    private Double3 calcSpecular(Material material,Vector n, Vector l, double nl, Vector v){
        Vector r = l.add(n.scale(-2*nl));
        double mVR = alignZero(v.scale(-1).dotProduct(r));
        return material.kS.scale( pow( (mVR > 0? mVR:0),material.Shininess));
    }

    private Double3 calcDiffusive(Material material,double nl){
        return material.kD.scale( abs(nl));
    }
}
