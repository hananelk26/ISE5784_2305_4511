package primitives;

/**
 * Represents a point in a 3D space.
 */
public class Point {
    /**
     * The coordinates of the point.
     */
    final protected Double3 xyz;

    /**
     * A constant representing the origin point (0, 0, 0).
     */
    final public static Point ZERO = new Point(0, 0, 0);

    /**
     * Constructs a Point with the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a Point with the specified Double3 object.
     *
     * @param double3 the Double3 object containing the coordinates
     * @throws NullPointerException if double3 is null
     */
    public Point(Double3 double3) {
        if (double3 == null) {
            throw new NullPointerException("double3 cannot be null");
        }
        this.xyz = double3;
    }

    /**
     * Adds a vector to this point and returns a new point.
     *
     * @param vec the vector to add
     * @return a new Point resulting from adding the vector to this point
     * @throws NullPointerException if vec is null
     */
    public Point add(Vector vec) {
        if (vec == null) {
            throw new NullPointerException("vec cannot be null");
        }
        return new Point(this.xyz.add(vec.xyz));
    }

    /**
     * Subtracts another point from this point and returns a vector.
     *
     * @param other the point to subtract
     * @return a Vector resulting from subtracting the other point from this point
     * @throws NullPointerException if other is null
     */
    public Vector subtract(Point other) {
        if (other == null) {
            throw new NullPointerException("other cannot be null");
        }
        return new Vector(xyz.subtract(other.xyz));
    }

    /**
     * Computes the squared distance between this point and another point.
     *
     * @param other the other point
     * @return the squared distance between the two points
     * @throws NullPointerException if other is null
     */
    public double distanceSquared(Point other) {
        if (other == null) {
            throw new NullPointerException("other cannot be null");
        }
        return (xyz.d1 - other.xyz.d1) * (xyz.d1 - other.xyz.d1) +
                (xyz.d2 - other.xyz.d2) * (xyz.d2 - other.xyz.d2) +
                (xyz.d3 - other.xyz.d3) * (xyz.d3 - other.xyz.d3);
    }

    /**
     * Computes the distance between this point and another point.
     *
     * @param other the other point
     * @return the distance between the two points
     * @throws NullPointerException if other is null
     */
    public double distance(Point other) {
        if (other == null) {
            throw new NullPointerException("other cannot be null");
        }
        return Math.sqrt(distanceSquared(other));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "Point" + xyz;
    }
}
