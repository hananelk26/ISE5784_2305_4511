package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.*;

import static primitives.Util.*;

/**
 * Represents a ray in a 3D space.
 * A ray is defined by a starting point (head) and a direction vector.
 */
public class Ray {

    /**
     * filed to move the ray
     */
    private static final double DELTA = 0.1;

    /**
     * The starting point of the ray.
     */
    final private Point head;

    /**
     * The direction vector of the ray.
     * The direction vector is always normalized.
     */
    final private Vector direction;

    /**
     * Constructs a Ray with the specified head point and direction vector.
     *
     * @param head      the starting point of the ray
     * @param direction the direction vector of the ray
     * @throws NullPointerException if either head or direction is null
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    /**
     * Constructs a new Ray.
     * If the vector v is not orthogonal to the normal n, the head of the ray is moved slightly along the normal direction
     * to avoid numerical inaccuracies and ensure the ray originates just outside the surface it originates from.
     *
     * @param p the origin point of the ray
     * @param v the direction vector of the ray - must be <b><i>normalized</i></b>!
     * @param n the normal vector to determine the offset direction for the head of the ray
     */
    public Ray(Point p, Vector v, Vector n) {
        double res = v.dotProduct(n);
        head = res > 0 ? p.add(n.scale(DELTA)) : p.add(n.scale(-DELTA));
        this.direction = v;
    }

    /**
     * get function
     *
     * @return head field
     */
    public Point getHead() {
        return head;
    }

    /**
     * get function
     *
     * @return direction field
     */
    public Vector getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    @Override
    public String toString() {
        return "head" + "->" + direction;
    }

    /**
     * Calculates a point along the ray at a given distance.
     *
     * @param t the distance from the ray's origin to the desired point.
     *          If the distance is zero, the origin point of the ray is returned.
     * @return the point along the ray at the given distance.
     * If the distance is zero, the origin point of the ray.
     */
    public Point getPoint(double t) {
        return isZero(t) ? this.head : this.head.add(direction.scale(t));
    }

    /**
     * Finds the closest point from a list of points to the ray origin.
     *
     * @param points a list of points to evaluate
     * @return the closest point to the ray origin, or null if the list is empty or null
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * calculate the closest GeoPoint to the head of the ray
     *
     * @param listOfGeoPoints is the list of all the Geopoints that we need to check
     * @return the closest GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> listOfGeoPoints) {
        if (listOfGeoPoints == null || listOfGeoPoints.isEmpty()) return null;

        GeoPoint closetGeoPoint = null;
        double smallest = Double.POSITIVE_INFINITY;

        for (GeoPoint geoPoint : listOfGeoPoints) {
            double distance = geoPoint.point.distanceSquared(head);
            if (distance < smallest) {
                closetGeoPoint = geoPoint;
                smallest = distance;
            }
        }

        return closetGeoPoint;
    }


}
