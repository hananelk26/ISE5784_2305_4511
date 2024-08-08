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

    public double getX() {
        return xyz.d1;
    }

    public double getY() {
        return xyz.d2;
    }

    public double getZ() {
        return xyz.d3;
    }

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
        double x = xyz.d1 - other.xyz.d1;
        double y = xyz.d2 - other.xyz.d2;
        double z = xyz.d3 - other.xyz.d3;
        return x * x + y * y + z * z;
    }

    /**
     * Computes the distance between this point and another point.
     *
     * @param other the other point
     * @return the distance between the two points
     * @throws NullPointerException if other is null
     */
    public double distance(Point other) {
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

    /**
     * get the x value of the point
     *
     * @return the x value of the point
     */
    public double getX() {
        return xyz.d1;
    }

    /**
     * get the y value of the point
     *
     * @return the y value of the point
     */
    public double getY() {
        return xyz.d2;
    }

    /**
     * get the z value of the point
     *
     * @return the z value of the point
     */
    public double getZ() {
        return xyz.d3;
    }

}
