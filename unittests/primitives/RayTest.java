package primitives;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Ray
 */
class RayTest {

    /**
     * Test method for {@link primitives.Ray#getPoint(double)} (primitives.Ray)}.
     */
    @Test
    void getPoint() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: regular test that the function works well
        Ray ray = new Ray(new Point(1, 0, 0), new Vector(0, 1, 0));
        assertEquals(new Point(1, 4, 0), ray.getPoint(4),
                "Fail getPoint doesn't return the correct point t positive");

        assertEquals(new Point(1, -2, 0), ray.getPoint(-2),
                "Fail getPoint doesn't return the correct point t negative");

        // =============== Boundary Values Tests ==================
        //tc02:
        assertEquals(new Point(1, 0, 0), ray.getPoint(0),
                "Fail getPoint with t=0");

    }
}