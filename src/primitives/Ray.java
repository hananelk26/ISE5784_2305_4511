package primitives;

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
        if (head == null || direction == null) {
            throw new NullPointerException("head and direction cannot be null");
        }
        this.head = head;
        this.direction = direction.normalize();
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
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
}
