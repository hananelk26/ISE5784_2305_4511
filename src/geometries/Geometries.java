package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
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
     * Adds one or more geometries to the collection.
     *
     * @param geometries One or more geometries to add to the collection.
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

//    @Override
//    public List<Point> findIntersections(Ray ray) {
//        List<Point> listOfPoint = null;
//        for (var body : geometries) { // pass on collection of geometries
//            var temp = body.findIntersections(ray);
//            if (temp != null) { // need to add the points of temp to listOfPoint.
//                if (listOfPoint == null)
//                    listOfPoint = new LinkedList<>(temp);
//                else
//                    listOfPoint.addAll(temp);
//            }
//        }
//        return listOfPoint;
//    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> listOfPoint = null;
        for (var body : geometries) { // pass on collection of geometries
            var temp = body.findGeoIntersections(ray);
            if (temp != null) { // need to add the points of temp to listOfPoint.
                if (listOfPoint == null)
                    listOfPoint = new LinkedList<>(temp);
                else
                    listOfPoint.addAll(temp);
            }
        }
        return listOfPoint;
    }
}
