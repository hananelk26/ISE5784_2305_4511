import geometries.Sphere;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Comparator;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#Sphere(double, primitives.Point)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        Point p = new Point(1, 2, 1);

        // TC01: constructor with regular values
        assertDoesNotThrow(() -> new Sphere(2, p),
                "ERROR: Failed constructing a correct cylinder");

        // TC02: constructor with negative radius
        assertThrows(IllegalArgumentException.class,
                () -> new Sphere(-2, p),
                "ERROR: Failed to throw error cylinder with negative radius");
    }

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: get normal with the right values
        Sphere s1 = new Sphere(5, new Point(1, 1, 1));
        assertEquals(new Vector(0, 1, 0), s1.getNormal(new Point(1, 6, 1)),
                "ERROR: getNormal of plane failed");

    }

    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, p001);
        final Point gp1 = new Point(2.0 / 3, 1.0 / 3, 1.0 / 3);
        final Point gp2 = new Point(0, 1, 1);
        final var exp = List.of(gp1, gp2);
        final Vector v_111 = new Vector(-1, 1, 1);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p_100 = new Point(-1, 0, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p_100, v110)),
                "Fail ray's line is outside the sphere (0 points TC01)");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p100, v_111));
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Fail ray starts before and crosses the sphere (2 points TC02)");

        // TC03: Ray starts inside the sphere (1 point)
        assertEquals(List.of(new Point(0, sqrt(0.75), 1)), sphere.findIntersections
                        (new Ray(new Point(0, 0, 0.5), new Vector(0, 1, 0))),
                "Fail ray starts inside the sphere (1 point TC03)");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p100, v110)),
                "Fail ray starts after the sphere (0 points TC04)");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        Point p0_11 = new Point(0, -1, 1);
        Point p101 = new Point(1, 0, 1);
        Point p000 = new Point(0, 0, 0);
        Point p002 = new Point(0, 0, 2);
        Vector v100 = new Vector(1, 0, 0);

        // TC11: Ray starts at sphere and goes inside (1 point)
        assertEquals(List.of(p0_11), sphere.findIntersections(new Ray(p101, new Vector(1, 1, 0))),
                "Fail ray starts at sphere and goes inside (1 point TC11)");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p101, new Vector(-1, -1, 0))),
                "Fail ray starts at sphere and goes outside (0 points TC12)");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        final var result2 = sphere.findIntersections(new Ray(new Point(0, 0, -1), v001))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p_100))).toList();
        assertEquals(2, result2.size(), "Wrong number of points");
        assertEquals(List.of(p000, p002), result2,
                "Fail ray starts before the sphere (2 points TC13)");

        // TC14: Ray starts at sphere and goes inside (1 point)
        assertEquals(List.of(p002), sphere.findIntersections(new Ray(p000, v001)),
                "Fail ray starts at sphere and goes inside (1 point TC14)");

        // TC15: Ray starts inside (1 point)
        assertEquals(List.of(p002), sphere.findIntersections(new Ray(new Point(0, 0, 0.5), v001)),
                "Fail Ray starts inside (1 point TC15)");

        // TC16: Ray starts at the center (1 point)
        assertEquals(List.of(p002), sphere.findIntersections(new Ray(p001, v001)),
                "Fail ray starts at the center (1 point TC16)");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p002, v001)),
                "Fail ray starts at sphere and goes outside (0 points TC17)");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0, 0, 3), v001)),
                "Fail ray starts after sphere (0 points TC18)");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(p_100, v100)),
                "Fail ray starts before the tangent point (0 points TC19)");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(p002, v100)),
                "Fail ray starts at the tangent point (0 points TC20)");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(p_100, new Vector(-1, 0, 0))),
                "Fail ray starts after the tangent point (0 points TC21)");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(2,0,1),v001)),
                "Fail ray's line is outside, ray is orthogonal to ray start to sphere's center line (0 points TC22)");

    }
}
