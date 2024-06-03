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

        // Find intersections of the ray with the plane of the triangle
        final var intersectionsPoints = plane.findIntersections(ray);
        //intersectionsPoints is null that means that there are no cut points
        if (intersectionsPoints == null) return null;

        // Get the origin point and direction vector of the ray
        Point p = intersectionsPoints.getFirst();
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        // Calculate vectors from the ray origin to the triangle vertices
        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);

        // Calculate the normals to the edges of the triangle
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        // Calculate the dot products of the ray direction with the edge normals
        double vn1 = v.dotProduct(n1);
        double vn2 = v.dotProduct(n2);
        double vn3 = v.dotProduct(n3);

        // Check if the intersection point is inside the triangle
        if (vn1 > 0 && vn2 > 0 && vn3 > 0 || vn1 < 0 && vn2 < 0 && vn3 < 0)
            return intersectionsPoints;

        // If not inside the triangle, return null
        return null;
    }
}
