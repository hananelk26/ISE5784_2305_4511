package geometries;

import primitives.*;

import static java.lang.Math.*;


/**
 * Class to represent a bounding box
 */
public class BoundingBox {
    /**
     * The minimum point of the bounding box
     */
    public Point max;

    /**
     * The maximum point of the bounding box
     */
    public Point min;

    /**
     * Constructor for the bounding box
     *
     * @param min the minimum point of the bounding box
     * @param max the maximum point of the bounding box
     */
    public BoundingBox(Point min, Point max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Check if a ray intersects the bounding box
     *
     * @param ray the ray to check
     * @return true if the ray intersects the bounding box, false otherwise
     */
    public boolean hasIntersections(Ray ray) {

        Point head = ray.getHead();
        Vector direction = ray.getDirection();

        double[] tMin = {Double.NEGATIVE_INFINITY};
        double[] tMax = {Double.POSITIVE_INFINITY};

        boolean xIntersect = intersectsOneAxis(direction.getX(), min.getX(), max.getX(), head.getX(), tMin, tMax);
        if (!xIntersect) return false;

        boolean yIntersect = intersectsOneAxis(direction.getY(), min.getY(), max.getY(), head.getY(), tMin, tMax);
        if (!yIntersect) return false;

        boolean zIntersect = intersectsOneAxis(direction.getZ(), min.getZ(), max.getZ(), head.getZ(), tMin, tMax);
        if (!zIntersect) return false;

        return tMax[0] >= tMin[0];
    }

    /**
     * Checks if a ray intersects the bounding box along a specific axis (X, Y, or Z).
     * This method calculates the entry and exit points of the ray for the given axis
     * and updates the minimum and maximum intersection distances accordingly.
     *
     * @param dir    The direction component of the ray along the axis being checked.
     * @param boxMin The minimum coordinate of the bounding box along the axis.
     * @param boxMax The maximum coordinate of the bounding box along the axis.
     * @param head   The coordinate of the ray's origin (head) along the axis.
     * @param tMin   An array holding the current minimum intersection distance (updated in-place).
     * @param tMax   An array holding the current maximum intersection distance (updated in-place).
     * @return true if the ray intersects the bounding box along the given axis, false otherwise.
     */
    boolean intersectsOneAxis(double dir, double boxMin, double boxMax, double head, double[] tMin, double[] tMax) {
        if (dir != 0) {
            double tEntry = (boxMin - head) / dir;
            double tExit = (boxMax - head) / dir;
            tMin[0] = max(tMin[0], min(tEntry, tExit));
            tMax[0] = min(tMax[0], max(tEntry, tExit));
            return true;
        }
        return head > boxMin && head < boxMax;
    }

    /**
     * Get the center of the bounding box
     *
     * @return the center of the bounding box
     */
    public Point getCenter() {
        return new Point(
                (max.getX() + min.getX()) / 2.0,
                (max.getY() + min.getY()) / 2.0,
                (max.getZ() + min.getZ()) / 2.0
        );
    }

    /**
     * Union of two bounding boxes
     *
     * @param box the other bounding box
     * @return the union of the two bounding boxes
     */
    public BoundingBox union(BoundingBox box) {
        return new BoundingBox(
                new Point(
                        min(min.getX(), box.min.getX()),
                        min(min.getY(), box.min.getY()),
                        min(min.getZ(), box.min.getZ())
                ),
                new Point(
                        max(max.getX(), box.max.getX()),
                        max(max.getY(), box.max.getY()),
                        max(max.getZ(), box.max.getZ())
                )
        );
    }
}