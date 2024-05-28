import geometries.Plane;
import primitives.Vector;


import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
}