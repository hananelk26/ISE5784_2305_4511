package geometries;
import geometries.Sphere;
import primitives.Vector;

import org.junit.jupiter.api.Test;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void testGetNormal(){
        // ============ Equivalence Partitions Tests ==============

        // TC01: check if the func get normal returns the right vector
        Sphere s1=new Sphere(5,new Point(1,1,1));
        assertEquals(  new Vector(0,1,0),s1.getNormal(new Point(1,6,1)),
                "ERROR: getNormal of plane failed");




    }
}