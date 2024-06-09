package renderer;

import geometries.Intersectable;
import geometries.Sphere;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

public class IntegrationTest {

    /** Camera builder for the tests */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
            .setVpDistance(10);
    Camera camera = cameraBuilder.setVpSize(3, 3).build();

    int sum = 0;

    private LinkedList<Ray> constructRayOnAllPixels()
    {
        final var rays = new LinkedList<Ray>();
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rays.add(camera.constructRay(3,3,j,i));
            }
        }
        return rays;
    }

    void testSphereInCenterOfPixel()
    {
        Sphere sphere = new Sphere(1,new Point(0,0,-3));
        List<Point> temp;
        for(Ray ray : constructRayOnAllPixels()) {
            temp = sphere.findIntersections(ray);
            sum += temp == null? 0 : temp.size();
        }
    }



















}
