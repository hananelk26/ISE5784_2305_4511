package geometries;

import geometries.Cylinder;
import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

public class CylinderTest {

   @Test
    public void testConstructor(){
       // ============ Equivalence Partitions Tests ==============
       // TC01: constructor with regular values
        Ray axis=new Ray(new Point(1,0,1),new Vector(0,1,1));
        assertDoesNotThrow(()->new Cylinder(2,axis,1),
                "ERROR: constructor of cylinder with correct values failed");

       // TC02: constructor with negative radius
       assertThrows(IllegalArgumentException.class,
               ()->new Cylinder(-2,axis,1),
               "ERROR: Failed constructor with negative radius");

       // TC03: constructor with negative height
       assertThrows(IllegalArgumentException.class,
               ()->new Cylinder(2,axis,-1),
               "ERROR: Failed constructor with negative height");
    }
    @Test
    public void testGetNormal(){
        // ============ Equivalence Partitions Tests ==============
        // TC01: get normal with the right values
        Ray axis = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        Cylinder cyl=new Cylinder(4, axis,10);
        assertEquals(new Vector(1,0,0),cyl.getNormal(new Point(4,0,0)),
                "ERROR: get normal of tube failed");

        // TC02: get normal with point outside the Cylinder
        assertThrows(IllegalArgumentException.class, ()->cyl.getNormal(new Point(5,8,1)),
                "ERROR: Failed to throw error point outside the Cylinder");

        // TC03: get normal with point inside the cylinder
        assertThrows(IllegalArgumentException.class, ()->cyl.getNormal(new Point(1,0,3)),
                "ERROR: Failed to throw error point inside the cylinder");

        //TC04: The point you want to calculate its normal is at the bottom base
        assertEquals(axis.getDirection(),cyl.getNormal(new Point(3,0,0)));

        //TC05: The point you want to calculate its normal is at the upper base
        assertEquals(axis.getDirection(),cyl.getNormal(new Point(3,0,10)));

        //TC06: The point you want to calculate the normal is in the seam between the lower base and the height of the cylinder
        assertEquals(axis.getDirection(),cyl.getNormal(new Point(4,0,0)));
    }

}