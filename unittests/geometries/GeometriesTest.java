import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    private final Point p001 = new Point(0, 0, 1);

    @Test
    void add() {
    }

    @Test
    void findIntersections() {

        Ray ray1 = new Ray(new Point(0, 1, 0), new Vector(0, 0, 1));
        Ray ray2 = new Ray(new Point(-3, 0, 0), new Vector(0, 0, 1));
        Ray ray3 = new Ray(new Point(0, 4, 0), new Vector(1, 0, 0));
        Ray ray4 = new Ray(new Point(0, 0, 3), new Vector(1, 0, 0));
        Ray ray5 = new Ray(new Point(-2, 0, 1), new Vector(1, 0, 0));
        Sphere sphere = new Sphere(1d, p001);
        Point p2_10 = new Point(2, -1, 0);
        Point p210 = new Point(2, 1, 0);
        Point p204 = new Point(2, 0, 4);
        Triangle triangle = new Triangle(p2_10, p210, p204);
        Plane plane = new Plane(new Point(3, 0, 0), new Vector(1, 0, 0));

        Geometries geometries1 = new Geometries();
        geometries1.add(sphere, triangle, plane);
        Geometries geometries2 = new Geometries();

        // ============ Equivalence Partitions Tests ==============

        //TC01: Some shapes (but not all) are cut.
        assertEquals(2, geometries1.findIntersections(ray4).size(), "The function not return the correct number of points.");


        // =============== Boundary Values Tests ==================

        //TC02: case of An empty body collection.
        assertNull(geometries2.findIntersections(ray1), "The function returns intersection points even though there are no bodies in the collection.");

        //TC03: No shape is cut
        assertNull(geometries1.findIntersections(ray2), "The function returns intersection points even though No shape is cut");

        //TC04: Only one shape is cut
        assertEquals(1, geometries1.findIntersections(ray3).size(), "The function not return one point even though Only one shape is cut ");

        //TC05: All shapes are cut.
        assertEquals(4, geometries1.findIntersections(ray5).size(), "The ray cat all the shapes but dont return the correct number of points");


    }
}