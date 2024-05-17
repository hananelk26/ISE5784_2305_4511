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

    /**
     * Computes the normal vector to the sphere at a given point.
     * <p>
     * Note: This method currently returns null, which indicates that
     * the implementation is not yet completed.
     *
     * @param point the point on the surface of the sphere
     * @return the normal vector to the sphere at the given point (currently null)
     */
    @Override
    public Vector getNormal(Point point) {
        return null; // Placeholder implementation
    }
}
