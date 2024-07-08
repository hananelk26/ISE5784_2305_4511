package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering an image with multiple objects, transparency, and reflection.
 */
public class MultiObjectImageTests {
    /**
     * Scene for the test
     */
    private final Scene scene = new Scene("Multi Object Test Scene")
            .setAmbientLight(new AmbientLight(new Color(black), new Double3(0.1)));

    /**
     * Camera builder for the test
     */
    private final Camera.Builder camera = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene))
            .setLocation(new Point(0, 0, -100))  // Move the camera further back
            .setDirection(new Vector(0, -1, 0), new Vector(0, 0, 1))
            .setVpSize(500, 500).setVpDistance(200);

    /**
     * Shininess value for most of the geometries in the tests
     */
    private static final int SHININESS = 1000;
    /**
     * Diffusion attenuation factor for some of the geometries in the tests
     */
    private static final double KD = 0.5;
    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private static final double KS = 0.5;

    /**
     * Transparency and reflection factors for some of the geometries in the tests
     */
    private static final double KT = 0;
    private static final double KR = 0.2;


   private final Polygon rec1= (Polygon) new Polygon(new Point(-120,-200,0),new Point(-115,-200,0),new Point(-115,-200,70),new Point(-120,-200,70)).setEmission(new Color(WHITE));
   private final Polygon rec2= (Polygon) new Polygon(new Point(120,-200,0),new Point(115,-200,0),new Point(115,-200,70),new Point(120,-200,70)).setEmission(new Color(WHITE));
   private final Polygon rec3= (Polygon) new Polygon(new Point(-120,-200,70),new Point(-120,-200,75),new Point(120,-200,75),new Point(120,-200,70)).setEmission(new Color(WHITE));
   private final Polygon rec4= (Polygon) new Polygon(new Point(-80,-180,0),new Point(-80,-180,3),new Point(80,-180,3),new Point(80,-180,0)).setEmission(new Color(WHITE));
   private final Polygon rec5= (Polygon) new Polygon(new Point(-115,-200,0),new Point(-115,-200,3),new Point(-80,-180,3),new Point(-80,-180,0)).setEmission(new Color(WHITE));
   private final Polygon rec6= (Polygon) new Polygon(new Point(115,-200,0),new Point(115,-200,3),new Point(80,-180,3),new Point(80,-180,0)).setEmission(new Color(WHITE));
   private final Polygon rec7= (Polygon) new Polygon(new Point(120,-200,74),new Point(120,-200,71),new Point(100,-200,61),new Point(100,-200,64)).setEmission(new Color(WHITE));
   private final Polygon rec8= (Polygon) new Polygon(new Point(-120,-200,74),new Point(-120,-200,71),new Point(-100,-200,61),new Point(-100,-200,64)).setEmission(new Color(WHITE));
   private final Polygon rec9= (Polygon) new Polygon(new Point(100,-200,60),new Point(100,-200,65),new Point(80,-180,5),new Point(80,-180,0)).setEmission(new Color(WHITE));
   private final Polygon rec10= (Polygon) new Polygon(new Point(-100,-200,60),new Point(-100,-200,65),new Point(-80,-180,5),new Point(-80,-180,0)).setEmission(new Color(WHITE));

    private final Polygon grass1= (Polygon) new Polygon(new Point(120,-200,0),new Point(150,-200,0),new Point(720,-340,0),new Point(720,-500,0)).setEmission(new Color(0, 128, 0));
    private final Polygon grass2= (Polygon) new Polygon(new Point(120,-200,0),new Point(50,-200,0),new Point(720,-800,0),new Point(720,-500,0)).setEmission(new Color(34, 139, 34)); // Dark green
    private final Triangle tri1 = (Triangle) new Triangle(new Point(720,-800,0),new Point(720,-500,0),new Point(1200,-700,-350)).setEmission(new Color(34, 139, 34)); // Dark green;
    private final Polygon grass3 = (Polygon) new Polygon(
            new Point(50, -200, 0),
            new Point(-20, -200, 0),
            new Point(200, -1000, 0),
            new Point(700, -500, 0)  // Adjusted Z coordinate to be on the same plane
    ).setEmission(new Color(0, 128, 0));

    private final Polygon grass4 = (Polygon) new Polygon(
            new Point(700, -500, 0),
            new Point(800, -800, 0),
            new Point(600, -1000, 0),
            new Point(200, -1000, 0)  // Adjusted Z coordinate to be on the same plane
    ).setEmission(new Color(0, 128, 0));

    private final Triangle tri2 = (Triangle) new Triangle(new Point(470, -580, 20),new Point(200, -1000, 0),new Point(1200,-700,-1500)).setEmission(new Color(0,128,0)); // Dark green;

    private final Triangle tri3 = (Triangle) new Triangle(new Point(445, -580, 20),new Point(1200,-700,-1500),new Point(1200,-700,-350)).setEmission(new Color(0,128,0)); // Dark green;

    private final Polygon grass5= (Polygon) new Polygon(new Point(-120,-200,0),new Point(-150,-200,0),new Point(-720,-340,0),new Point(-720,-500,0)).setEmission(new Color(0, 128, 0));

    private final Polygon grass6= (Polygon) new Polygon(new Point(-120,-200,0),new Point(-50,-200,0),new Point(-720,-800,0),new Point(-1500,-700,0)).setEmission(new Color(34, 139, 34)); // Dark green

    private final Triangle tri4 = (Triangle) new Triangle(new Point(-900,-700,0),new Point(-720,-800,0),new Point(1200,-700,-90000)).setEmission(new Color(34,139,34)); // Dark green;


    /**
     * Create a multi-object scene with transparency and reflection
     */
    @Test
    public void multiObjectImage() {
        scene.geometries.add(rec1,rec2,rec3,rec4,rec5,rec6,rec7,rec8,rec9,rec10);
        scene.geometries.add(grass1,grass2,tri1,grass3,grass4,tri2,tri3,grass5,grass6,tri4);
        scene.lights.add(new DirectionalLight(new Vector(0,-1,-1),new Color(white)));
        camera.setRayTracer(new SimpleRayTracer(scene)).setImageWriter(new ImageWriter("try",720,850))
                .build().renderImage().writeToImage();



    }
}
