package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Represents a triangle in a 3D space.
 * A triangle is a polygon with exactly three vertices.
 */
public class Triangle extends Polygon {
    /**
     * Constructs a Triangle with all the points of the triangle.
     *
     * @param p1 first vertex
     * @param p2 second vertex
     * @param p3 third vertex
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    public List<Point> findIntersections(Ray ray) {

        final var intersectionsPoints = plane.findIntersections(ray);
        if (intersectionsPoints == null) return null;
        Point p = intersectionsPoints.getFirst();
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();
        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        double vn1 = v.dotProduct(n1);
        double vn2 = v.dotProduct(n2);
        double vn3 = v.dotProduct(n3);
        if (vn1 > 0 && vn2 > 0 && vn3 > 0 || vn1 < 0 && vn2 < 0 && vn3 < 0)
            return intersectionsPoints;
        return null;
    }
}
