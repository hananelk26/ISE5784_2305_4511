package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

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

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // Find intersections of the ray with the plane of the triangle
        final var intersectionsPoints = plane.findGeoIntersections(ray, maxDistance);
        //intersectionsPoints is null that means that there are no cut points
        if (intersectionsPoints == null) return null;

        // Get the origin point and direction vector of the ray
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        // Calculate vectors from the ray origin to the triangle vertices
        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        // Calculate the normals to the edges of the triangle
        Vector n1 = v1.crossProduct(v2).normalize();
        // Calculate the dot products of the ray direction with the edge normals
        double vn1 = alignZero(v.dotProduct(n1));
        if (vn1 == 0) return null;

        Vector v3 = vertices.get(2).subtract(p0);
        Vector n2 = v2.crossProduct(v3).normalize();
        double vn2 = alignZero(v.dotProduct(n2));

        // If the ray crosses between the normals (n1 and n2) in different directions, it doesn't intersect the triangle
        if (vn1 * vn2 <= 0) return null;

        Vector n3 = v3.crossProduct(v1).normalize();
        double vn3 = alignZero(v.dotProduct(n3));
        if (vn1 * vn3 <= 0) return null;

        return List.of(new GeoPoint(this, intersectionsPoints.getFirst().point));
    }
}
