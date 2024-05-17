package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface for geometric objects in a 3D space.
 * Any geometric object should be able to provide the normal vector at a given point on its surface.
 */
public interface Geometry {
    /**
     * Returns the normal vector to the geometric object at the given point.
     *
     * @param point the point on the geometric object
     * @return the normal vector to the geometric object at the given point
     */
    public Vector getNormal(Point point);
}
