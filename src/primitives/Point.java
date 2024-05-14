package primitives;

import java.util.Objects;

public class Point {
    final protected Double3 xyz;
    final public static Point ZERO = new Point(0, 0, 0);

    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    public Point(Double3 double3) {
        xyz = double3;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "Point" + xyz.toString();
    }

    public Point add(Vector vec) {
        return new Point(this.xyz.add(vec.xyz));
    }

    public Vector subtract(Point other) {
        return new Vector(xyz.subtract(other.xyz));
    }

    public double distanceSquared(Point other) {
        return (xyz.d1 - other.xyz.d1) * (xyz.d1 - other.xyz.d1) +
                (xyz.d2 - other.xyz.d2) * (xyz.d2 - other.xyz.d2) +
                (xyz.d3 - other.xyz.d3) * (xyz.d3 - other.xyz.d3);
    }

    public double distance(Point other){
        return Math.sqrt(distanceSquared(other));
    }
}
