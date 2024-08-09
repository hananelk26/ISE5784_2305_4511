package geometries;

import primitives.Ray;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * The Geometries class represents a collection of geometric shapes that can be intersected by a ray.
 */
public class Geometries extends Intersectable {

    /**
     * list of geometries bodies
     */
    private final List<Intersectable> geometries = new LinkedList<>();//collection of geometries

    /**
     * Default constructor that creates an empty collection of geometries.
     */
    public Geometries() {
        calcBoundingBox();
    }

    /**
     * Constructor that initializes the collection with one or more geometries.
     *
     * @param geometries One or more geometries to add to the collection.
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
        calcBoundingBox();
    }

    /**
     * Constructor with parameters
     *
     * @param geometries list of geometries
     */
    public Geometries(List<Intersectable> geometries) {
        add(geometries);
    }

    /**
     * Adds one or more geometries to the collection.
     *
     * @param geometries One or more geometries to add to the collection.
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * Add geometries to the list
     *
     * @param geometries list of geometries
     */
    public void add(List<Intersectable> geometries) {
        this.geometries.addAll(geometries);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> listOfPoint = null;
        for (var body : geometries) { // pass on collection of geometries
            var temp = body.findGeoIntersections(ray, maxDistance);
            if (temp != null) { // need to add the points of temp to listOfPoint.
                if (listOfPoint == null)
                    listOfPoint = new LinkedList<>(temp);
                else
                    listOfPoint.addAll(temp);
            }
        }
        return listOfPoint;
    }

    @Override
    public void calcBoundingBox() {
        if (geometries.isEmpty()) {
            boundingBox = null;
        } else {
            boundingBox = geometries.getFirst().getBoundingBox();
            for (Intersectable body : geometries) {
                boundingBox = boundingBox.union(body.getBoundingBox());
            }
        }
    }

    /**
     * Calculate the bounding box for the geometries
     */
    public void makeCBR() {
        for (var body : geometries)
            body.calcBoundingBox();
    }


    /**
     * Store the geometries as a BVH
     */
    public void makeBVH() {
        makeCBR();
        buildBVH();
    }

    /**
     * Build the geometries as a BVH
     */
    private void buildBVH() {
        // extract infinite geometries into a separate list
        List<Intersectable> infiniteGeometries = geometries.stream()
                .filter(g -> g.boundingBox == null)
                .peek(geometries::remove).toList();

        // sort geometries based on their bounding box centroids along an axis (e.g. x-axis)
        geometries.sort(Comparator.comparingDouble(g -> g.boundingBox.getCenter().getX()));

        // combine each 4 geometries into a bounding box
        while (geometries.size() >= 4)
            geometries.add(new Geometries(geometries.removeFirst(),
                    geometries.removeFirst(), geometries.removeFirst(), geometries.removeFirst()));

        geometries.addAll(infiniteGeometries); // combine the infinite geometries back
    }

}