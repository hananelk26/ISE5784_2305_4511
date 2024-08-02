package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a plane in a 3D space.
 * A plane is defined by either three points or a point and a normal vector.
 */
public class Plane extends Geometry {
    /**
     * A point on the plane.
     */
    final private Point p;

    /**
     * The normal vector to the plane.
     */
    final private Vector normal;

    /**
     * Constructs a plane with three points.
     * The normal vector is computed as the cross product of vectors formed by these points.
     *
     * @param p1 the first point on the plane
     * @param p2 the second point on the plane
     * @param p3 the third point on the plane
     * @throws IllegalArgumentException when the points are convergent or co-linear
     */
    public Plane(Point p1, Point p2, Point p3) {
        Vector vector1 = p2.subtract(p1);
        Vector vector2 = p3.subtract(p2);
        this.normal = vector1.crossProduct(vector2).normalize();
        this.p = p1;
    }

    /**
     * Constructs a plane with a point and a normal vector.
     *
     * @param p      the point on the plane
     * @param normal the normal vector to the plane
     */
    public Plane(Point p, Vector normal) {
        this.normal = normal.normalize();
        this.p = p;
    }

    /**
     * returns the normal to the plane
     *
     * @return normal to the plane
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector direction = ray.getDirection();
        Point p0 = ray.getHead();
        if (p.equals(p0)) return null; // The ray start on the plane (0 points)

        double nv = normal.dotProduct(direction);
        if (isZero(nv)) return null; // the ray is parallel to the plane (0 points)

        double npMinusP0 = normal.dotProduct(p.subtract(p0));
        double t = alignZero(npMinusP0 / nv);
        return (t <= 0 || alignZero(maxDistance - t) <= 0)
                // if t<0 then the ray not cut the plane and if t == 0 then the ray lie start on the plane.        if (t > 0){
                ? null
                : List.of(new GeoPoint(this, ray.getPoint(t)));
    }

    @Override
    public void calcBoundingBox() {}

}
