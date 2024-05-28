import geometries.Sphere;
import primitives.Ray;
import primitives.Vector;

import org.junit.jupiter.api.Test;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /** Test method for {@link geometries.Sphere#Sphere(double, primitives.Point)}.*/
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        Point p=new Point(1,2,1);

        // TC01: constructor with regular values
        assertDoesNotThrow(() -> new Sphere(2, p),
                "ERROR: Failed constructing a correct cylinder");

        // TC02: constructor with negative radius
        assertThrows(IllegalArgumentException.class,
                () -> new Sphere(-2, p),
                "ERROR: Failed to throw error cylinder with negative radius");
    }

    /** Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.*/
    @Test
    void testGetNormal(){
        // ============ Equivalence Partitions Tests ==============

        // TC01: get normal with the right values
        Sphere s1 = new Sphere(5,new Point(1,1,1));
        assertEquals(  new Vector(0,1,0),s1.getNormal(new Point(1,6,1)),
                "ERROR: getNormal of plane failed");

        // TC02: get normal with point outside the sphere
        assertThrows(IllegalArgumentException.class, ()->s1.getNormal(new Point(1,7,1)),
                "ERROR: Failed to throw error point outside the sphere");

        // TC03: get normal with point inside the sphere
        assertThrows(IllegalArgumentException.class, ()->s1.getNormal(new Point(1,5,1)),
                "ERROR: Failed to throw error point inside the sphere");

    }
}