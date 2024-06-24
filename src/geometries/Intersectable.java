package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The Intersectable interface represents geometric objects that can be intersected by a ray.
 */
public abstract class Intersectable {

    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        public GeoPoint(Geometry geometry, Point point){
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
    public final List<Point> findIntersections(Ray ray);

    public final List<GeoPoint> findGeoIntersections(Ray ray)
    {
        return findGeoIntersectionsHelper(ray);
    }

    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
