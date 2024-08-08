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
        double boxMinX = min.getX();
        double boxMinY = min.getY();
        double boxMinZ = min.getZ();
        double boxMaxX = max.getX();
        double boxMaxY = max.getY();
        double boxMaxZ = max.getZ();

        Point head = ray.getHead();
        Vector direction = ray.getDirection();
        double headX = head.getX();
        double headY = head.getY();
        double headZ = head.getZ();
        double dirX = direction.getX();
        double dirY = direction.getY();
        double dirZ = direction.getZ();

        double tMin = Double.NEGATIVE_INFINITY, tMax = Double.POSITIVE_INFINITY;

        if (dirX != 0) {
            double t1 = (boxMinX - headX) / dirX;
            double t2 = (boxMaxX - headX) / dirX;
            tMin = max(tMin, min(t1, t2));
            tMax = min(tMax, max(t1, t2));
        } else if (headX <= boxMinX || headX >= boxMaxX) {
            return false;
        }

        if (dirY != 0) {
            double t1 = (boxMinY - headY) / dirY;
            double t2 = (boxMaxY - headY) / dirY;
            tMin = max(tMin, min(t1, t2));
            tMax = min(tMax, max(t1, t2));
        } else if (headY <= boxMinY || headY >= boxMaxY) {
            return false;
        }

        if (dirZ != 0) {
            double t1 = (boxMinZ - headZ) / dirZ;
            double t2 = (boxMaxZ - headZ) / dirZ;
            tMin = max(tMin, min(t1, t2));
            tMax = min(tMax, max(t1, t2));
        } else if (headZ <= boxMinZ || headZ >= boxMaxZ) {
            return false;
        }

        return tMax >= tMin;
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

