package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.sqrt;
import static primitives.Util.alignZero;

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
        calcBoundingBox();
    }

    @Override
    public Vector getNormal(Point point) {
        return (point.subtract(center)).normalize();// Returns a vector whose beginning is the center point of the sphere and its end is the point for which you want a normal vector.
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();
        if (center.equals(p0))
            return List.of(new GeoPoint(this, center.add(v.scale(radius))));

        Vector u = center.subtract(p0);  // Vector from ray start to sphere center
        double tm = v.dotProduct(u); // Projection of u on the ray direction
        // Square of the distance from the sphere center to the ray
        double dSquared = u.lengthSquared() - tm * tm;
        double thSquared = radiusSquared - dSquared;
        // If the distance from the ray to the sphere center is greater than the radius, no intersections
        if (alignZero(thSquared) <= 0) return null;

        double th = sqrt(thSquared);// Distance from the intersection points to the point where the ray is closest to the sphere center
        double t1 = tm - th;// Distance from the ray start to the first intersection point
        double t2 = tm + th;// Distance from the ray start to the second intersection point
        if (alignZero(t2) <= 0|| alignZero(maxDistance - t1)<= 0) return null; // t1 < t2 <= 0

        if (t1 <= 0)
            return alignZero(maxDistance - alignZero(t2)) <= 0
                    ? null
                    : List.of(new GeoPoint(this, ray.getPoint(t2)));
        else
            return alignZero(maxDistance - alignZero(t2)) <= 0
                    ? List.of(new GeoPoint(this, ray.getPoint(t1)))
                    : List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
    }

    @Override
    public void calcBoundingBox() {
        this.boundingBox = new BoundingBox(
                new Point(center.getX() - radius, center.getY() - radius, center.getZ() - radius),
                new Point(center.getX() + radius, center.getY() + radius, center.getZ() + radius)
                );
}
}
