package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;

/**
 * Test rendering an image with depth of field.
 */
public class DepthOfFieldTests {
    /**
     * Scene for the test
     */
    private final Scene scene = new Scene("Depth of Field Test Scene").setBackground(new Color(30, 30, 50))
            .setAmbientLight(new AmbientLight(new Color(black), new Double3(0.1)));

    /**
     * Camera builder for the test
     */
    private final Camera.Builder camera = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene))
            .setLocation(new Point(0, 0, -200))
            .setDirection(new Vector(0, 0, 1), new Vector(0, -1, 0))
            .setVpSize(200, 200).setVpDistance(100)
            .setFocalDistance(150)  // Distance to the focal plane
            .setApertureSize(7)    // Aperture size for depth of field effect
            .setNumOfRays(100);


    /**
     * Spheres for depth of field demonstration
     */
    private final Geometry[] spheres = {
            new Sphere(10, new Point(-50, 0, 50))  // Closer to camera (out of focus)
                    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
            new Sphere(10, new Point(-30, 10, 100))  // In focus (on focal plane)
                    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
            new Sphere(10, new Point(-10, -10, 150))  // Farther from camera (out of focus)
                    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
            new Sphere(10, new Point(10, 0, 200))  // Even farther from camera (out of focus)
                    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
            new Sphere(10, new Point(30, 10, 250))  // Further away
                    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))
    };

    /**
     * Create a scene to demonstrate depth of field
     */
    @Test
    public void depthOfFieldImage() {
        scene.geometries.add(spheres[0], spheres[1], spheres[2], spheres[3], spheres[4]);

        scene.lights.add(new DirectionalLight(new Vector(0,0,1), new Color(WHITE)));

        camera.setRayTracer(new SimpleRayTracer(scene)).setImageWriter(new ImageWriter("depthOfField", 1800, 1800))
                .build().renderImage().writeToImage();
    }
}
