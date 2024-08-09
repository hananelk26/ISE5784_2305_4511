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

        double[] minCut = {Double.NEGATIVE_INFINITY};
        double[] maxCut = {Double.POSITIVE_INFINITY};

        boolean xIntersect = intersectsOneAxis(direction.getX(), min.getX(), max.getX(), head.getX(), minCut, maxCut);
        if (!xIntersect) return false;

        boolean yIntersect = intersectsOneAxis(direction.getY(), min.getY(), max.getY(), head.getY(), minCut, maxCut);
        if (!yIntersect) return false;

        boolean zIntersect = intersectsOneAxis(direction.getZ(), min.getZ(), max.getZ(), head.getZ(), minCut, maxCut);
        if (!zIntersect) return false;

        return maxCut[0] >= minCut[0];

    }

    boolean intersectsOneAxis(double dir, double boxMin, double boxMax, double head, double[] minCut, double[] maxCut) {
        if (dir != 0) {
            double entryPoint = (boxMin - head) / dir;
            double exitPoint = (boxMax - head) / dir;
            minCut[0] = Math.max(minCut[0], Math.min(entryPoint, exitPoint));
            maxCut[0] = Math.min(maxCut[0], Math.max(entryPoint, exitPoint));
        } else if (head <= boxMin || head >= boxMax) {
            return false;
        }
        return true;
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