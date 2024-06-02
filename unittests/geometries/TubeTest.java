
import geometries.Tube;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: constructor with regular values
        Ray axis = new Ray(new Point(1, 2, 3), new Vector(1, 0, 1));
        assertDoesNotThrow(() -> new Tube(2, axis),
                "ERROR: Failed constructing a correct cylinder");

        // TC02: constructor with negative radius
        assertThrows(IllegalArgumentException.class,
                () -> new Tube(-2, axis),
                "ERROR: Failed to throw error cylinder with negative radius");
    }

    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: get normal with the right values
        Ray axis = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        Tube tube = new Tube(4, axis);
        assertEquals(new Vector(1, 0, 0), tube.getNormal(new Point(4, 0, 3)),
                "ERROR: get normal of tube failed");

        // =============== Boundary Values Tests ==================

        //TC02: checks when (point - head ) orthogonal to direction.
        assertEquals(new Vector(1, 0, 0), tube.getNormal(new Point(4, 0, 0)),
                "ERROR: get normal of tube failed");

    }




}