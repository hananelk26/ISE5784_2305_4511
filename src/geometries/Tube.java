package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a tube in a 3D space.
 * A tube is defined by a radius and a central axis ray.
 */
public class Tube extends RadialGeometry {
    /**
     * The central axis of the tube.
     */
    final protected Ray axis;

    /**
     * Constructs a tube with the specified radius and axis ray.
     *
     * @param radius the radius of the tube
     * @param axis   the central axis ray of the tube
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    /**
     * Computes the normal vector to the tube at a given point.
     * <p>
     * Note: This method currently returns null, which indicates that
     * the implementation is not yet completed.
     *
     * @param point the point on the surface of the tube
     * @return the normal vector to the tube at the given point (currently null)
     */
    @Override
    public Vector getNormal(Point point) {
        return null; // Placeholder implementation
    }
}
