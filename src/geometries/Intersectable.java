package geometries;


import primitives.Point;
import primitives.Ray;

import java.util.List;


/**
 * The Intersectable interface represents geometric objects that can be intersected by a ray.
 */
public interface Intersectable {

    /**
     * Finds all intersection points between a given ray and the geometric object.
     *
     * @param ray The ray to intersect with the geometric object.
     * @return A list of intersection points, or null if there are no intersections.
     */
    List<Point> findIntersections(Ray ray);
}
