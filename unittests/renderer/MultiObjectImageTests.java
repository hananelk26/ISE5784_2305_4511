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
            .setAmbientLight(new AmbientLight(new Color(black), new Double3(0.1))).setBackground(new Color(173, 216, 230));

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


    private final Polygon rec1 = (Polygon) new Polygon(new Point(-120, -200, 0), new Point(-115, -200, 0), new Point(-115, -200, 70), new Point(-120, -200, 70)).setEmission(new Color(WHITE));
    private final Polygon rec2 = (Polygon) new Polygon(new Point(120, -200, 0), new Point(115, -200, 0), new Point(115, -200, 70), new Point(120, -200, 70)).setEmission(new Color(WHITE));
    private final Polygon rec3 = (Polygon) new Polygon(new Point(-120, -200, 70), new Point(-120, -200, 75), new Point(120, -200, 75), new Point(120, -200, 70)).setEmission(new Color(WHITE));
    private final Polygon rec4 = (Polygon) new Polygon(new Point(-80, -180, 0), new Point(-80, -180, 3), new Point(80, -180, 3), new Point(80, -180, 0)).setEmission(new Color(WHITE));
    private final Polygon rec5 = (Polygon) new Polygon(new Point(-115, -200, 0), new Point(-115, -200, 3), new Point(-80, -180, 3), new Point(-80, -180, 0)).setEmission(new Color(WHITE));
    private final Polygon rec6 = (Polygon) new Polygon(new Point(115, -200, 0), new Point(115, -200, 3), new Point(80, -180, 3), new Point(80, -180, 0)).setEmission(new Color(WHITE));
    private final Polygon rec7 = (Polygon) new Polygon(new Point(120, -200, 74), new Point(120, -200, 71), new Point(100, -200, 61), new Point(100, -200, 64)).setEmission(new Color(WHITE));
    private final Polygon rec8 = (Polygon) new Polygon(new Point(-120, -200, 74), new Point(-120, -200, 71), new Point(-100, -200, 61), new Point(-100, -200, 64)).setEmission(new Color(WHITE));
    private final Polygon rec9 = (Polygon) new Polygon(new Point(100, -200, 60), new Point(100, -200, 65), new Point(80, -180, 5), new Point(80, -180, 0)).setEmission(new Color(WHITE));
    private final Polygon rec10 = (Polygon) new Polygon(new Point(-100, -200, 60), new Point(-100, -200, 65), new Point(-80, -180, 5), new Point(-80, -180, 0)).setEmission(new Color(WHITE));

    private final Polygon grass0 = (Polygon) new Polygon(
            new Point(200, -200, 0),
            new Point(280, -200, 0),
            new Point(750, -200, -400),
            new Point(550, -200, -400))
            .setEmission(new Color(34, 139, 34)); // Dark green

    private final Polygon grass1 = (Polygon) new Polygon(
            new Point(120, -200, 0),
            new Point(200, -200, 0),
            new Point(550, -200, -400),
            new Point(350, -200, -400))
            .setEmission(new Color(0, 128, 0));

    private final Polygon grass2 = (Polygon) new Polygon(
            new Point(40, -200, 0),
            new Point(120, -200, 0),
            new Point(350, -200, -400),
            new Point(150, -200, -400))
            .setEmission(new Color(34, 139, 34)); // Dark green

    private final Polygon grass3 = (Polygon) new Polygon(
            new Point(-40, -200, 0),
            new Point(40, -200, 0),
            new Point(150, -200, -400),
            new Point(-50, -200, -400)  // Adjusted Z coordinate to be on the same plane
    ).setEmission(new Color(0, 128, 0));

    private final Polygon grass4 = (Polygon) new Polygon(
            new Point(-120, -200, 0),
            new Point(-40, -200, 0),
            new Point(-50, -200, -400),
            new Point(-250, -200, -400)  // Adjusted Z coordinate to be on the same plane
    ).setEmission(new Color(34, 139, 34));

    private final Polygon grass5 = (Polygon) new Polygon(
            new Point(-200, -200, 0),
            new Point(-120, -200, 0),
            new Point(-250, -200, -400),
            new Point(-450, -200, -400))
            .setEmission(new Color(0, 128, 0));

    private final Polygon grass6 = (Polygon) new Polygon(
            new Point(-280, -200, 0),
            new Point(-200, -200, 0),
            new Point(-450, -200, -400),
            new Point(-650, -200, -400))
            .setEmission(new Color(34, 139, 34)); // Dark green



    Sphere ball = (Sphere) new Sphere(30, new Point(0, -500, -50))  // יצירת כדור מרכזי
            .setEmission(new Color(WHITE))
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(1000));

    //
    Polygon hex1 = (Polygon) new Polygon(
            new Point(0, -250, 0),
            new Point(43.3, -275, 0),
            new Point(43.3, -325, 0),
            new Point(0, -350, 0),
            new Point(-43.3, -325, 0),
            new Point(-43.3, -275, 0))
            .setEmission(new Color(BLACK))
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(1000));

    //
    Polygon pent1 = (Polygon) new Polygon(
            new Point(0, -260, 0),
            new Point(38.2, -287.6, 0),
            new Point(23.5, -332.4, 0),
            new Point(-23.5, -332.4, 0),
            new Point(-38.2, -287.6, 0))
            .setEmission(new Color(BLACK))
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(1000));


    /**
     * Create a multi-object scene with transparency and reflection
     */
    @Test
    public void multiObjectImage() {
        scene.geometries.add(rec1, rec2, rec3, rec4, rec5, rec6, rec7, rec8, rec9, rec10);
        scene.geometries.add(grass0,grass1, grass2, grass3, grass4, grass5, grass6);
        scene.geometries.add(ball,pent1,hex1);
        scene.lights.add(new DirectionalLight(new Vector(0, -1, -1), new Color(white)));
        camera.setRayTracer(new SimpleRayTracer(scene)).setImageWriter(new ImageWriter("try", 720, 850))
                .build().renderImage().writeToImage();


    }
}
