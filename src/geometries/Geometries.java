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
    }

    /**
     * Constructor that initializes the collection with one or more geometries.
     *
     * @param geometries One or more geometries to add to the collection.
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
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
            var temp = body.findGeoIntersections(ray,maxDistance);
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
            for (Intersectable g : geometries) {
                boundingBox = boundingBox.union(g.getBoundingBox());
            }
        }
    }

/** Calculate the bounding box for the geometries */
public void makeCBR() {
    for (var g : geometries)
        g.calcBoundingBox();
}


/** Store the geometries as a BVH */
public void makeBVH() {
    makeCBR();
    buildBVH();
}

/** Build a BVH tree from a list of intersectable geometries */
public void buildBVH() {
    if (geometries.size() <= 3) {
        // if there are 3 or fewer geometries, use them as the bounding box
        return;
    }

    // extract infinite geometries into a separate list
    List<Intersectable> infiniteGeometries = new LinkedList<>();
    for (int i = 0; i < geometries.size(); i++) {
        var g = geometries.get(i);
        if (g.getBoundingBox() == null) {
            infiniteGeometries.add(g);
            geometries.remove(i);
            i--;
        }
    }

    // sort geometries based on their bounding box centroids along an axis (e.g., x-axis)
    geometries.sort(Comparator.comparingDouble(g -> g.getBoundingBox().getCenter().getX()));

    // split the list into two halves
    int mid = geometries.size() / 2;
    Geometries leftGeometries = new Geometries(geometries.subList(0, mid));
    Geometries rightGeometries = new Geometries(geometries.subList(mid, geometries.size()));

    // recursively build the BVH for the two halves
    leftGeometries.buildBVH();
    rightGeometries.buildBVH();

    // calculate the bounding box for the two halves
    leftGeometries.calcBoundingBox();
    rightGeometries.calcBoundingBox();

    // create a combined bounding box
    Geometries combined = new Geometries(leftGeometries);
    combined.add(rightGeometries);
    combined.calcBoundingBox();

    // return the list of geometries
    List<Intersectable> result = new LinkedList<>(infiniteGeometries);
    result.add(combined);
    geometries.clear();
    geometries.addAll(result);
}

}