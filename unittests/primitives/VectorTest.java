import primitives.Vector;
import primitives.Point;
import primitives.Double3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorTest {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    Vector v1 = new Vector(2, 3, 4);
    Vector v2 = new Vector(-2, -3, -4);
    Vector unit = new Vector(1, 0, 0);

    /** Test method for {@link primitives.Vector#Vector(double, double, double)}.*/
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Create an instance of a vector.
        assertDoesNotThrow(() -> new Vector(2, 3, 4), "Failed constructing a correct vector");

        // =============== Boundary Values Tests ==================

        // TC02: zero vector in the
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "Constructed a zero vector");

        // TC03: zero vector in the second constructor
        assertThrows(IllegalArgumentException.class, () -> new Vector(new Double3(0, 0, 0)), "Constructed a zero vector");

    }

    /** Test method for {@link primitives.Vector#add(primitives.Vector)}.*/
    @Test
    public void testAdd() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: Checks that a vector can be added to another vector
        Vector v3 = new Vector(4,5,7);
        assertDoesNotThrow(() -> v1.add(v3), "Failed adding a vector to a other vector");

        //TC02: checks that a negative vector can be added to positive vector
        Vector minusVec = new Vector(-1,-1,-1);
        assertEquals(new Vector(1,2,3), v1.add(minusVec), "Failed adding a negative vector to a positive vector");

        // =============== Boundary Values Tests ==================

        // TC03: Checking that the program throws an exception when the result is the zero vector
        assertThrows(IllegalArgumentException.class, () -> v1.add(v2), "The function return a zero vector.");
    }

    /** Test method for {@link primitives.Vector#scale(double)}.*/
    @Test
    public void testScale() {
        // =============== Equivalence Partitions Tests ==================

        // TC01: Checking that the vector is indeed multiplied by the positive scalar as it should be
        Vector newVector = v1.scale(2.0);
        assertEquals(new Vector(4,6,8), newVector,"Failed to multiply vector by positive scalar on x component");

        // TC02: Checking that the vector is indeed multiplied by the negative scalar as it should be
        newVector = v1.scale(-2.0);
        assertEquals(new Vector(-4,-6,-8), newVector,"Failed to multiply vector by negative scalar on x component");

        // =============== Boundary Values Tests ==================

        //TC03: Checking that the program throws an exception when the result is the zero vector
        assertThrows(IllegalArgumentException.class, ()->v1.scale(0.0), "The function return a zero vector.");

    }

    /** Test method for {@link primitives.Vector#subtract(primitives.Point)}.*/
    @Test
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: Checks that a vector can be subtracted to another vector
        Vector newVector = new Vector(1,2,3);
        assertEquals(newVector,v1.subtract(new Point(1,1,1)), "Failed subtracting a vector to a other vector");

        //TC02: checks that a negative vector can be subtracted to positive vector
        assertEquals(new Vector(3,4,5), v1.subtract(new Point(-1.0,-1.0,-1.0)), "Failed adding a negative vector to a positive vector");

        // =============== Boundary Values Tests ==================

        // TC03: Checking that the program throws an exception when the result is the zero vector
        assertThrows(IllegalArgumentException.class, () -> v1.add(v2), "The function return a zero vector.");
    }

    /** Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.*/
    @Test
    public void dotProduct() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: checks a dot product between vector to other vector.
        assertEquals(-29,v1.dotProduct(v2), "Failed making dot product between vector to a other vector");

        // =============== Boundary Values Tests ==================

        //TC02: checks when the result of dot product is 0.
        Vector v3 = new Vector(-3,2,0);
        assertEquals(0,v1.dotProduct(v3),DELTA, "Failed making dot product between vector to a negative vector");

        //TC03: checks dot product of unit vector with other vector.
        assertEquals(2,unit.dotProduct(v1),"Failed making dot product between vector to a unit vector");
    }

    /** Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.*/
    @Test
    public void crossProduct() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: checks cross product between two vectors.
        Vector v3 = new Vector(1,8,-3);
        Vector result = new Vector(-41,10,13);
        assertEquals(result,v1.crossProduct(v3),"Failed making cross product between vector to an other vector");

        // =============== Boundary Values Tests ==================

        //TC02: Tests a cross product of two vectors in the same direction
        Vector v4 = new Vector(4.0,6.0,8.0);
        assertThrows(IllegalArgumentException.class,()->v1.crossProduct(v4),"Not failed making cross product between two vectors in same direction");

        //TC03: Tests a cross product of two vectors in Tests a cross product of two vectors in opposite direction
        assertThrows(IllegalArgumentException.class,()->v1.crossProduct(v2),"Not failed making cross product between two vectors in opposite direction");

        //TC04: Tests a cross product of two equal vectors.
        assertThrows(IllegalArgumentException.class,()-> v1.crossProduct(v1),"Not failed making cross product between two two equal vectors");
    }


    /** Test method for {@link primitives.Vector#lengthSquared()}.*/
    @Test
    public void testLengthSquared() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: checks length Squared on vector.
        assertEquals(29,v1.lengthSquared(),"Failed making length squared on vector.");

    }

    /** Test method for {@link primitives.Vector#length()}.*/
    @Test
    public void Testlength() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: checks length on vector.
        Vector vec = new Vector(5,0,0);
        assertEquals(5,vec.length(),"Failed making length on vector.");
    }

    /** Test method for {@link primitives.Vector#normalize()}.*/
    @Test
    public void TestNormalize() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: checks normalize on vector.
        Vector vec = new Vector(5,0,0);
        Vector result = new Vector(1,0,0);
        assertEquals(result,vec.normalize(),"Failed making a normalize vector.");


        //TC01: checks normalize a unit vector.
        assertEquals(unit,unit.normalize(),"Failed making a normalize vector from unit vector.");

    }

}