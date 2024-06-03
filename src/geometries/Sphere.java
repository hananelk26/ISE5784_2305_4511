package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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

    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();
        if (center.equals(p0))
            return List.of(center.add(v.scale(radius)));
        Vector u = center.subtract(p0);  // Vector from ray start to sphere center
        double tm = v.dotProduct(u); // Projection of u on the ray direction
        double d = sqrt(u.lengthSquared() - tm * tm); // Square of the distance from the sphere center to the ray
        // If the distance from the ray to the sphere center is greater than the radius, no intersections
        if (d >= radius) return null;
        double th = sqrt(radius * radius - d * d);// Distance from the intersection points to the point where the ray is closest to the sphere center
        double t1 = tm - th;// Distance from the ray start to the first intersection point
        double t2 = tm + th;// Distance from the ray start to the second intersection point

        // If both intersection points are behind the ray start, no intersections
        if (t1 <= 0 && t2 <= 0) return null;
        List<Point> intersectionsPoints = new java.util.ArrayList<>(List.of());

        // Add the intersection points to the list if they are in front of the ray start
        if (t1 > 0) intersectionsPoints.add(ray.getPoint(t1));
        if (t2 > 0) intersectionsPoints.add(ray.getPoint(t2));
        return intersectionsPoints;

    }
}
