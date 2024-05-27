package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
        if(height<0)
            throw new IllegalArgumentException("A height can't be <= 0");
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {

    }
}
