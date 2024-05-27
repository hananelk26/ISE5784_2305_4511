package geometries;
import geometries.Plane;
import primitives.Vector;


import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: check if the func get normal returns the right vector
        Plane p1=new Plane(new Point(1,0,0),new Point(1,1,0),new Point(1,0,1));
        assertEquals(new Vector(-1,0,0),p1.getNormal(new Point(1,0,0)),
                "ERROR: getNormal of plane failed");


    }
}