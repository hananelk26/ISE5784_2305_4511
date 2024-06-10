package geometries;

import geometries.Plane;
import primitives.Ray;
import primitives.Vector;
import org.junit.jupiter.api.Test;
import primitives.Point;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Testing Plane
 */
class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#Plane(Point, Point, Point)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        /**
         * point for test
         */
        Point p1 = new Point(1, 0, 0);
        /**
         * point for test
         */
        Point p2 = new Point(1, 1, 0);
        /**
         * point for test
         */
        Point p3 = new Point(1, 1, 1);
        // TC01: constructor with regular values
        assertDoesNotThrow(() -> new Plane(p1, p2, p3),
                "ERROR: constructor of plane with correct values failed");

        // TC02: second constructor with regular values
        assertDoesNotThrow(() -> new Plane(p1, new Vector(1, 2, 0)),
                "ERROR: constructor of plane with correct values failed");


        // =============== Boundary Values Tests ==================
        // TC04: constructor with same points
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p2, p2),
                "ERROR: Failed to throw error cylinder with same points");

        // TC05: constructor with points on the same line
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p2, new Point(1, 0.5, 0)),
                "ERROR: Failed to throw error cylinder with points on the same line");

    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: check if the func get normal returns the right vector
        Plane p1 = new Plane(new Point(1, 0, 0), new Point(1, 1, 0), new Point(1, 0, 1));
        assertEquals(new Vector(1, 0, 0), p1.getNormal(new Point(0, 3, 0)),
                "ERROR: getNormal of plane failed");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============

        Point p348 = new Point(3, 4, 8);
        Vector n = new Vector(-24, 54, -15);
        Plane plane = new Plane(p348, n);
        //TC01: checks a ray that cuts a plane return 1 point.
        Ray ray1 = new Ray(new Point(-1, -1, -1), new Vector(1, 2, 3));
        assertEquals(List.of(new Point(0, 1, 2)), plane.findIntersections(ray1), "The function findIntsersections returns an incorrect breakpoint.");

        //TC02: checks A ray that starts after the plane and therefore does not cut it (0 points)
        Ray ray2 = new Ray(new Point(2, 0, 0), new Vector(1, 0, 0));
        assertNull(plane.findIntersections(ray2), "The function returns a cut point even though there is no cut");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line parallel to a plane

        //TC03: check the case that the rsy is parallel to the plane and lies in the plane (0 points)
        Ray ray3 = new Ray(p348, new Vector(3, 3, 6));
        assertNull(plane.findIntersections(ray3), "Returns intersection points even though the line is contained in the plane");

        //TC04: check the case that the ray is parallel to the plane but is not in the plane (0 points)
        Ray ray4 = new Ray(new Point(2, 0, 0), new Vector(3, 3, 6));
        assertNull(plane.findIntersections(ray4), "The line is parallel to the plane and the function findIntsersections does not return null.");

        // **** Group: The ray line is perpendicular to the plane

        //TC11: We are checking a case where the ray line is perpendicular to the plane and starts before the plane (1 point)
        Ray ray5 = new Ray(new Point(24, -53, 17), new Vector(-24, 54, -15));
        assertEquals(List.of(new Point(0, 1, 2)), plane.findIntersections(ray5), "The function findIntsersections returns an incorrect breakpoint.");

        //TC12: We check a case where the ray line is perpendicular to the plane and starts after the plane (0 points)
        Ray ray6 = new Ray(new Point(-24, 55, -13), new Vector(-24, 54, -15));
        assertNull(plane.findIntersections(ray6), "The function findIntersections returns an incorrect breakpoint.");

        //TC13: We are checking a case where the ray line is perpendicular to the plane and starts at a point in the plane (0 points)
        Ray ray7 = new Ray(new Point(-1, 0, 0), new Vector(-24, 54, -15));
        assertNull(plane.findIntersections(ray7), "The function returns an intersection point even though the ray line starts from the plane");

        // **** Group: The beam line is neither perpendicular nor parallel to the plane

        //TC14: checks case that ray line starts from the point by which the plane was defined (0 points)
        Ray ray8 = new Ray(new Point(3, 4, 8), new Vector(2, -13, 5));
        assertNull(plane.findIntersections(ray8), "The function returns an intersection point even though the ray line starts from the plane");

        //TC15: checks case that the ray start from the plane (0 points)
        Ray ray9 = new Ray(new Point(0, 1, 2), new Vector(2, -13, 5));
        assertNull(plane.findIntersections(ray9), "The function returns an intersection point even though the ray line starts from the plane");
    }
}