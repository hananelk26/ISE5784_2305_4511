package geometries;

import geometries.Cylinder;
import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Cylinder
 */
public class CylinderTest {

    /**
     * Test method for {@link geometries.Cylinder#Cylinder(double, primitives.Ray, double)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: constructor with regular values
        Ray axis = new Ray(new Point(1, 0, 1), new Vector(0, 1, 1));
        assertDoesNotThrow(() -> new Cylinder(2, axis, 1),
                "ERROR: constructor of cylinder with correct values failed");

        // TC02: constructor with negative radius
        assertThrows(IllegalArgumentException.class,
                () -> new Cylinder(-2, axis, 1),
                "ERROR: Failed constructor with negative radius");

        // TC03: constructor with negative height
        assertThrows(IllegalArgumentException.class,
                () -> new Cylinder(2, axis, -1),
                "ERROR: Failed constructor with negative height");
    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: get normal with the right values
        Ray axis = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        Cylinder cyl = new Cylinder(4, axis, 10);
        assertEquals(new Vector(1, 0, 0), cyl.getNormal(new Point(4, 0, 2)),
                "ERROR: get normal of tube failed");

        //TC02: The point you want to calculate its normal is at the bottom base
        assertEquals(axis.getDirection(), cyl.getNormal(new Point(3, 0, 0)));

        //TC03: The point you want to calculate its normal is at the upper base
        assertEquals(axis.getDirection(), cyl.getNormal(new Point(3, 0, 10)));

        //TC04: The point you want to calculate the normal is in the seam between the lower base and the height of the cylinder
        assertEquals(axis.getDirection(), cyl.getNormal(new Point(4, 0, 0)));
    }

}