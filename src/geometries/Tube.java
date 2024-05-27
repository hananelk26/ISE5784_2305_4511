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
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;



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



        // Declare a variable to hold the projection point on the cylinder's axis
        Point o;

        // Calculate the projection of the point on the cylinder's axis direction
        double t = (point.subtract(axis.getHead())).dotProduct(axis.getDirection());

        if (t != 0) {
            // If the projection distance is not zero, find the projection point on the axis
            o = axis.getHead().add(axis.getDirection().scale(t));
        } else {
            // If the projection distance is zero, the projection point is the head of the axis
            o = axis.getHead();
        }

        // Calculate the distance from the point to the projection point on the axis
        double distance = point.distance(o);

        // Check if the distance is equal to the radius
        if (Math.abs(distance - getRadius()) > DELTA) {
            throw new IllegalArgumentException("Point is not on the surface of the tube");
        }

        // Subtract the projection point from the given point to get the vector
        // from the axis to the given point, then normalize it to get the normal vector
        return point.subtract(o).normalize();
    }
}
