package primitives;

import primitives.Point;
import primitives.Vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    /** Test method for {@link primitives.Point#add(primitives.Vector)}.*/
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: add vector to point and check if it added correctly
        Point p = new Point(1, 2, 3);
        assertEquals(new Point(2, 3, 4), p.add(new Vector(1, 1, 1)),
                "ERROR: failed to add vector to point");
    }

    /** Test method for {@link primitives.Point#subtract(primitives.Point)}.*/
    @Test
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: subtract points and check if it subtracted correctly
        Point p = new Point(7, 7, 7);
        assertEquals(new Vector(3, 0, -2), p.subtract(new Point(4, 7, 9)),
                "ERROR: failed to subtract point from point");

        // =============== Boundary Values Tests ==================

        //TC02: checks that have exception when we subtract point p from point p.
        assertThrows(IllegalArgumentException.class,()->p.subtract(p),"create zero vector.");
    }

    /** Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.*/
    @Test
    public void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: check tha the distance squared between 2 points is correct
        Point p = new Point(5, 0, 6);
        assertEquals(50,p.distanceSquared(new Point(0,3,2)),
                "ERROR: distance squared between 2 points is wrong");

        // =============== Boundary Values Tests ==================

        //TC02:checks distanceSquared from a point to itself
        assertEquals(0,p.distanceSquared(p),"checks distanceSquared from a point to itself that it 0");
    }

    /** Test method for {@link primitives.Point#distance(primitives.Point)}.*/
    @Test
    public void testDistance() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: check that the distance between 2 points is correct
        Point p = new Point(0, 0, 6);
        assertEquals(5,p.distance(new Point(0,3,2)),
                "ERROR: distance between 2 points is wrong");

        //TC02:checks distance from a point to itself
        assertEquals(0,p.distance(p),"checks distance from a point to itself that it 0");
    }

}