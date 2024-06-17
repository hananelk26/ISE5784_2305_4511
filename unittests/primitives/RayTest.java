package primitives;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Ray
 */
class RayTest {

    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void getPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Regular test that the function works well
        Ray ray = new Ray(new Point(1, 0, 0), new Vector(0, 1, 0));
        assertEquals(new Point(1, 4, 0), ray.getPoint(4),
                "Fail getPoint doesn't return the correct point t positive");

        assertEquals(new Point(1, -2, 0), ray.getPoint(-2),
                "Fail getPoint doesn't return the correct point t negative");

        // =============== Boundary Values Tests ==================
        // TC02: Test with t=0
        assertEquals(new Point(1, 0, 0), ray.getPoint(0),
                "Fail getPoint with t=0");
    }

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(java.util.List)}.
     */
    @Test
    void findClosestPointTest() {
        Point p123 = new Point(1, 2, 3);
        Point p555 = new Point(5, 5, 5);
        Point pm1m70 = new Point(-1, -7, 0);
        Vector v100 = new Vector(1, 0, 0);
        Ray ray1 = new Ray(new Point(4, 5, 5), v100);

        LinkedList<Point> points = new LinkedList<>();
        points.add(p123);
        points.add(p555);
        points.add(pm1m70);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Regular test to find the closest point
        assertEquals(p555, ray1.findClosestPoint(points),
                "Failed: findClosestPoint (TC01)");

        // =============== Boundary Values Tests ==================
        // TC02: Test with an empty list
        assertNull(ray1.findClosestPoint(new LinkedList<Point>()),
                "Failed: findClosestPoint (TC02)");

        // TC03: Test with the first point being the closest
        assertEquals(p123, new Ray(new Point(1, 2, 4), v100).findClosestPoint(points),
                "Failed: findClosestPoint (TC03)");

        // TC04: Test with the last point being the closest
        assertEquals(pm1m70, new Ray(new Point(-1, -7, -1), v100).findClosestPoint(points),
                "Failed: findClosestPoint (TC04)");
    }
}
