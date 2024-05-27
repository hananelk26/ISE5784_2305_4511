package test.unittests.geometries;

import geometries.Cylinder;
import org.junit.jupiter.api.Test;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

public class CylinderTest {

   @Test
    public void testConstructor(){
        Ray axis=new Ray(new Point(1,0,1),new Vector(0,1,1));
        assertDoesNotThrow(()->new Cylinder(2,axis,1),
                "Failed constructing a correct polygon");
    }
    @Test
    public void testGetNormal(){

    }

}