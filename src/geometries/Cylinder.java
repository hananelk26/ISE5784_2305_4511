package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Represents a cylinder in a 3D space.
 * A cylinder is defined by its radius, central axis (as a Ray), and height.
 */
public class Cylinder extends Tube {
    /**
     * The height of the cylinder.
     */
    final private double height;

    /**
     * Constructs a cylinder with the specified radius, axis ray, and height.
     *
     * @param radius the radius of the cylinder
     * @param axis   the central axis ray of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        if (height <= 0)
            throw new IllegalArgumentException("A height can't be <= 0");
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        Point p0 = axis.getHead();
        Vector v = axis.getDirection();

        // check whether the point is the center of the lower base
        if (point.equals(p0)) return v;

        // Calculate the projection of the point on the cylinder's axis direction
        double t = (point.subtract(p0)).dotProduct(v);
        // Check where the point is on one of the bases
        if (isZero(t) || isZero(t - height)) return v;

        return point.subtract(axis.getPoint(t)).normalize();
    }

}
