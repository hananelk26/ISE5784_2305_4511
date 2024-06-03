package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

/**
 * Represents a sphere in a 3D space.
 * A sphere is defined by its radius and its center point.
 */
public class Sphere extends RadialGeometry {
    /**
     * The center point of the sphere.
     */
    final private Point center;

    /**
     * Constructs a sphere with the specified radius and center point.
     *
     * @param radius the radius of the sphere
     * @param center the center point of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {
        return (point.subtract(center)).normalize();// Returns a vector whose beginning is the center point of the sphere and its end is the point for which you want a normal vector.
    }

    public List<Point> findIntersections(Ray ray){
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();
        if (center.equals(p0)){

        }
        Vector u = center.subtract(p0);
        double tm = v.dotProduct(u);
        double d = sqrt(u.lengthSquared() - tm*tm);
        if (d >= radius) return null;
        double th = sqrt(radius*radius - d*d);
        double t1 = tm - th;
        double t2 = tm + th;
        if (t1 <= 0 && t2<= 0) return null;
        List<Point> intersectionsPoints = new ArrayList<>();
        if (t1 > 0) intersectionsPoints.add(p0.add(v.scale(t1)));
        if (t2 > 0) intersectionsPoints.add(p0.add(v.scale(t2)));
        return intersectionsPoints;

    }
}
