package primitives;

public class Vector extends Point {

    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("The vector can't be zero vector");
        }

    }

    public Vector(Double3 double3) {
        super(double3);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("The vector can't be zero vector");
        }
    }

    @Override
    public Vector add(Vector other) {
        return new Vector(xyz.add(other.xyz));
    }

    public Vector scale(Double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    public Double dotProduct(Vector other) {
        return xyz.d1 * other.xyz.d1 + xyz.d2 * other.xyz.d2 + xyz.d3 * other.xyz.d3;
    }

    public Vector crossProduct(Vector other) {
        return new Vector(other.xyz.d2 * xyz.d3 - other.xyz.d3 * xyz.d2,
                other.xyz.d3 * xyz.d1 - other.xyz.d1 * xyz.d3,
                other.xyz.d1 * xyz.d2 - other.xyz.d2 * xyz.d1);
    }

    public Double lengthSquared(){
        return dotProduct(this);
    }

    public Double length(){
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize(){
        Double length = length();
        return new Vector(xyz.d1/length,xyz.d2/length,xyz.d3/length);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

