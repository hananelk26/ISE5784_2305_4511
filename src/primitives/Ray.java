package primitives;

import java.util.List;

import static primitives.Util.*;

/**
 * Represents a ray in a 3D space.
 * A ray is defined by a starting point (head) and a direction vector.
 */
public class Ray {
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
     * calculate the closest point to the head of the ray
     *
     * @param listOfPoints is the list of all the points that we need to check
     * @return the closest point
     */
    public Point findClosestPoint(List<Point> listOfPoints) {
        if (listOfPoints.isEmpty()) return null;

        Point headOfRay = getHead();
        Point closetPoint = listOfPoints.getFirst();
        double smallest = closetPoint.distanceSquared(headOfRay);
        for (Point point : listOfPoints) {
            double distance = point.distanceSquared(headOfRay);
            if (distance < smallest) {
                closetPoint = point;
                smallest = distance;
            }

        }
        return closetPoint;

    }


}
