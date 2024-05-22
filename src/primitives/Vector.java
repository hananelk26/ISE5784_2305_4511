package primitives;

import static java.lang.System.out;

/**
 * Represents a vector in a 3D space.
 * A vector is defined by its coordinates (x, y, z) and inherits from Point.
 * The zero vector (0, 0, 0) is not allowed.
 */
public class Vector extends Point {

    /**
     * Constructs a vector with the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @throws IllegalArgumentException if the vector is the zero vector
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("The vector can't be zero vector");
        }
    }

    /**
     * Constructs a vector with the specified Double3 object.
     *
     * @param double3 the Double3 object containing the coordinates
     * @throws IllegalArgumentException if the vector is the zero vector
     */
    public Vector(Double3 double3) {
        super(double3);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("The vector can't be zero vector");
        }
    }

    /**
     * Adds another vector to this vector and returns a new vector.
     *
     * @param other the vector to add
     * @return a new Vector resulting from the addition
     */
    @Override
    public Vector add(Vector other) {
        return new Vector(xyz.add(other.xyz));
    }

    /**
     * Scales this vector by a scalar and returns a new vector.
     *
     * @param scalar the scalar to scale by
     * @return a new Vector resulting from the scaling
     */
    public Vector scale(Double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * Computes the dot product of this vector and another vector.
     *
     * @param other the other vector
     * @return the dot product of the two vectors
     */
    public Double dotProduct(Vector other) {
        return xyz.d1 * other.xyz.d1 + xyz.d2 * other.xyz.d2 + xyz.d3 * other.xyz.d3;
    }

    /**
     * Computes the cross product of this vector and another vector.
     *
     * @param other the other vector
     * @return a new Vector resulting from the cross product
     */
    public Vector crossProduct(Vector other) {
        return new Vector(other.xyz.d2 * xyz.d3 - other.xyz.d3 * xyz.d2,
                other.xyz.d3 * xyz.d1 - other.xyz.d1 * xyz.d3,
                other.xyz.d1 * xyz.d2 - other.xyz.d2 * xyz.d1);
    }

    /**
     * Computes the squared length of this vector.
     *
     * @return the squared length of the vector
     */
    public Double lengthSquared() {
        return dotProduct(this);
    }

    /**
     * Computes the length of this vector.
     *
     * @return the length of the vector
     */
    public Double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes this vector and returns a new unit vector.
     *
     * @return a new Vector that is the normalized version of this vector
     */
    public Vector normalize() {
        Double length = length();
        return new Vector(xyz.d1 / length, xyz.d2 / length, xyz.d3 / length);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Vector) && (super.equals(obj));
    }
//    public static void main(String[] args) {
//        Point v=new Point(1,2,3);
//        Vector v1=new Vector(1,2,3);
//        out.println(v.equals(v1));
//    }

    @Override
    public String toString() {
        return super.toString();
    }
}
