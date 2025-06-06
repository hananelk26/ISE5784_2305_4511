package primitives;

import primitives.Point;
import primitives.Vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Point
 */
class PointTest {

    /**
     * Test method for {@link primitives.Point#add(Vector)}.
     */
    @Test
    public void testAdd() {
        // TC01: add vector to point and check if it added correctly
        Point p = new Point(1, 2, 3);
        assertEquals(new Point(2, 3, 4), p.add(new Vector(1, 1, 1)),
                "ERROR: failed to add vector to point");
    }

    /**
     * Test method for {@link primitives.Point#subtract(Point)} .
     */
    @Test
    public void testSubtract() {
        // TC01: subtract points and check if it subtracted correctly
        Point p = new Point(7, 7, 7);
        assertEquals(new Vector(3, 0, -2), p.subtract(new Point(4, 7, 9)),
                "ERROR: failed to subtract point from point");

        //TC02: subtract of point from itself.
        assertThrows(IllegalArgumentException.class, () -> p.subtract(p));
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)}.
     */
    @Test
    public void testDistanceSquared() {
        // TC01: check tha the distance squared between 2 points is correct
        Point p = new Point(5, 0, 6);
        assertEquals(50, p.distanceSquared(new Point(0, 3, 2)),
                "ERROR: distance squared between 2 points is wrong");
        //TC02: distance of point from itself.
        assertEquals(0, p.distanceSquared(p));
    }

    /**
     * Test method for {@link primitives.Point#distance(Point)}.
     */
    @Test
    public void testDistance() {
        // TC01: check tha the distance between 2 points is correct
        Point p = new Point(0, 0, 6);
        assertEquals(5, p.distance(new Point(0, 3, 2)),
                "ERROR: distance between 2 points is wrong");

        //TC02: distance of point from itself.
        assertEquals(0, p.distance(p));
    }

}