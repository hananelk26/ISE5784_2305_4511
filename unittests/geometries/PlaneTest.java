
import geometries.Plane;
import primitives.Ray;
import primitives.Vector;


import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PlaneTest {

    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1, 0, 0);
        Point p2 = new Point(1, 1, 0);
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

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: check if the func get normal returns the right vector
        Plane p1 = new Plane(new Point(1, 0, 0), new Point(1, 1, 0), new Point(1, 0, 1));
        assertEquals(new Vector(1, 0, 0), p1.getNormal(new Point(0, 3, 0)),
                "ERROR: getNormal of plane failed");
    }

    @Test
    public void testFindIntsersections(){
        // ============ Equivalence Partitions Tests ==============

        //TC01: A ray that cuts a plane
        Ray ray1 = new Ray(new Point(-1,-1,-1),new Vector(1,2,3));
        Plane plane = new Plane(new Point(3,4,8),new Vector(-24,54,-15));
        assertEquals(new Point(0,1,2),plane.findIntsersections(ray1),"The function findIntsersections returns an incorrect breakpoint.");

        //TC02: A ray that starts after the plane and therefore does not cut it
        Ray ray2 = new Ray(new Point(2,0,0),new Vector(1,0,0));
        assertNull(plane.findIntsersections(ray2),"The function returns a cut point even though there is no cut");

        // =============== Boundary Values Tests ==================

        //TC03: A ray contained in a plane
        Ray ray3 = new Ray(new Point(3,4,8),new Vector(3,3,6));
        assertNull(plane.findIntsersections(ray3),"Returns intersection points even though the line is contained in the plane");

        //TC04:


    }
}