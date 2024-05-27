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

    @Override
    public Vector getNormal(Point point) {
        double t = (point.subtract(axis.getHead())).dotProduct(axis.getDirection());
        Point o = axis.getHead().add((axis.getDirection()).scale(t));
        return point.subtract(o).normalize();
    }
}
