package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

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
        Point p0 = axis.getHead();
        Vector v = axis.getDirection();

        // Calculate the projection of the point on the cylinder's axis direction
        double t = (point.subtract(p0)).dotProduct(v);
        // If the projection distance is zero, the projection point is the head of the axis
        // If the projection distance is not zero, find the projection point on the axis
        return point.subtract(axis.getPoint(t)).normalize();
    }

//    @Override
//    public List<Point> findIntersections(Ray ray) {
//        return null;
//    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray){return null;}
}
