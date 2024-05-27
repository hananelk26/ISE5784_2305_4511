package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a sphere in a 3D space.
 * A sphere is defined by its radius and its center point.
 */
public class Sphere extends RadialGeometry {
    /**
     * The center point of the sphere.
     */
    final private Point center;

    /**
     * Constructs a sphere with the specified radius and center point.
     *
     * @param radius the radius of the sphere
     * @param center the center point of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;   
    }

    @Override
    public Vector getNormal(Point point) {
        return (point.subtract(center)).normalize();// Returns a vector whose beginning is the center point of the sphere and its end is the point for which you want a normal vector.
    }
}
