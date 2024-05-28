
import geometries.Triangle;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct triangle with vertices in correct order
        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);
        assertDoesNotThrow(() -> new Triangle(p1, p2, p3),
                "ERROR: Failed constructing a correct polygon");

        // TC02: triangle with same points
        assertThrows(IllegalArgumentException.class,
                () -> new Triangle(p1, p2, p2),
                "ERROR: Failed to throw error in constructor with the same points");

        // TC03: triangle with 3 three points in the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Triangle(new Point(0.5, 0.5, 0), p2, p3),
                "ERROR:  Failed to throw error in constructor with 3 points on the same line");
    }

    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)};
        Triangle t = new Triangle(pts[0], pts[1], pts[2]);

        // ensure there are no exceptions
        assertDoesNotThrow(() -> t.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = t.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 2; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : 0])), DELTA,
                    "Polygon's normal is not orthogonal to one of the edges");

    }
}