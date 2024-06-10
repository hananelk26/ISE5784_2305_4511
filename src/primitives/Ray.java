package primitives;

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
     * The function returns a point which is a point and another vector times a number
     *
     * @param t The number of times we will multiply the vector
     *
     * @return point = Point + Vector * t
     */
    public Point getPoint(double t) {
        return isZero(t) ? this.head : this.head.add(direction.scale(t));
    }
}
