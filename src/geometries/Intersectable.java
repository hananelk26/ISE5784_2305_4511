package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The Intersectable class represents geometric objects that can be intersected by a ray.
 */
public abstract class Intersectable {

    /**
     * GeoPoint class represents a point on a geometry.
     * It contains the geometry and the specific point on the geometry.
     */
    public static class GeoPoint {
        /**
         * The geometry of the point.
         */
        public Geometry geometry;
        /**
         * The specific point on the geometry.
         */
        public Point point;

        /**
         * Constructs a GeoPoint with the specified geometry and point.
         *
         * @param geometry the geometry of the point
         * @param point the specific point on the geometry
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return (obj instanceof GeoPoint other)
                    && this.point.equals(other.point)
                    && this.geometry.equals(other.geometry);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }


    /**
     * Finds all intersection points between a given ray and the geometric object.
     *
     * @param ray The ray to intersect with the geometric object.
     * @return A list of intersection points, or null if there are no intersections.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds the geometric intersections of the given ray with the geometry.
     *
     * @param ray the ray to find intersections with
     * @return a list of intersection points, or null if there are no intersections
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * Helper method to find geometric intersections of the given ray.
     * This method is meant to be implemented by subclasses.
     *
     * @param ray the ray to find intersections with
     * @return a list of intersection points, or null if there are no intersections
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
