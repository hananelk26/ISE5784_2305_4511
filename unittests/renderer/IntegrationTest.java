package renderer;

import geometries.Intersectable;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {

    /**
     * Camera builder for the tests
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
            .setVpDistance(10);
    Camera camera = cameraBuilder.setVpSize(3, 3).build();

    private LinkedList<Ray> constructRayOnAllPixels() {
        final var listOfRays = new LinkedList<Ray>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                listOfRays.add(camera.constructRay(3, 3, j, i));
            }
        }
        return listOfRays;
    }

   final private   LinkedList<Ray> listOfRays = constructRayOnAllPixels();

    private int countIntersectionSphere(Sphere sphere)
    {
        int sum=0;
        for (Ray ray :listOfRays) {
            List<Point> temp;
            temp = sphere.findIntersections(ray);
            sum += temp == null ? 0 : temp.size();
        }
        return sum;
    }

    @Test
    void testSphereIntegration() {



        //TC01 intersection points in the case of a sphere of size 1 pixel and located in front of the center pixel
        Sphere sphere1 = new Sphere(1, new Point(0, 0, -3));
        assertEquals(2, countIntersectionSphere(sphere1),
                "Fail to return 2 intersection points in the case of a sphere of size 1 pixel and located in front of the center pixel (TC01)");

        //TC02 intersection points in the case of a sphere that in size of the view plan
        camera = cameraBuilder.setLocation(new Point(0, 0, 0.5)).build();
        Sphere sphere2 = new Sphere(2.5, new Point(0, 0, -2.5));
       assertEquals(18,countIntersectionSphere(sphere2),
               "Fail to return 18 intersection points in the case of a sphere that in size of the view plan (TC02) ");

    }


}
