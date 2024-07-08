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
    private final Scene scene = new Scene("Multi Object Test Scene").setBackground(new Color(50, 50, 70))
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
     * Polygon representing a part of the scene.
     */
    private final Polygon rec1 = (Polygon) new Polygon(new Point(-120, -200, 0), new Point(-115, -200, 0), new Point(-115, -200, 70), new Point(-120, -200, 70)).setEmission(new Color(WHITE));
    /**Polygon representing a part of the scene.*/
    private final Polygon rec2 = (Polygon) new Polygon(new Point(120, -200, 0), new Point(115, -200, 0), new Point(115, -200, 70), new Point(120, -200, 70)).setEmission(new Color(WHITE));
    /**Polygon representing a part of the scene.*/
    private final Polygon rec3 = (Polygon) new Polygon(new Point(-120, -200, 70), new Point(-120, -200, 75), new Point(120, -200, 75), new Point(120, -200, 70)).setEmission(new Color(WHITE));
    /**Polygon representing a part of the scene.*/
    private final Polygon rec4 = (Polygon) new Polygon(new Point(-80, -180, 0), new Point(-80, -180, 3), new Point(80, -180, 3), new Point(80, -180, 0)).setEmission(new Color(WHITE));
    /**Polygon representing a part of the scene.*/
    private final Polygon rec5 = (Polygon) new Polygon(new Point(-115, -200, 0), new Point(-115, -200, 3), new Point(-80, -180, 3), new Point(-80, -180, 0)).setEmission(new Color(WHITE));
    /**Polygon representing a part of the scene.*/
    private final Polygon rec6 = (Polygon) new Polygon(new Point(115, -200, 0), new Point(115, -200, 3), new Point(80, -180, 3), new Point(80, -180, 0)).setEmission(new Color(WHITE));
    /**Polygon representing a part of the scene.*/
    private final Polygon rec7 = (Polygon) new Polygon(new Point(120, -200, 74), new Point(120, -200, 71), new Point(100, -200, 61), new Point(100, -200, 64)).setEmission(new Color(WHITE));
    /**Polygon representing a part of the scene.*/
    private final Polygon rec8 = (Polygon) new Polygon(new Point(-120, -200, 74), new Point(-120, -200, 71), new Point(-100, -200, 61), new Point(-100, -200, 64)).setEmission(new Color(WHITE));
    /**Polygon representing a part of the scene.*/
    private final Polygon rec9 = (Polygon) new Polygon(new Point(100, -200, 60), new Point(100, -200, 65), new Point(80, -180, 5), new Point(80, -180, 0)).setEmission(new Color(WHITE));
    /**Polygon representing a part of the scene.*/
    private final Polygon rec10 = (Polygon) new Polygon(new Point(-100, -200, 60), new Point(-100, -200, 65), new Point(-80, -180, 5), new Point(-80, -180, 0)).setEmission(new Color(WHITE));

    /**Polygon representing a part of the grass.*/
    private final Polygon grass0 = (Polygon) new Polygon(
            new Point(200, -200, 0),
            new Point(280, -200, 0),
            new Point(750, -200, -400),
            new Point(550, -200, -400))
            .setMaterial(new Material().setKR(0).setKs(0.2).setKT(0).setShininess(5000).setKd(0.5)); // Dark green

    /**Polygon representing a part of the grass.*/
    private final Polygon grass1 = (Polygon) new Polygon(
            new Point(120, -200, 0),
            new Point(200, -200, 0),
            new Point(550, -200, -400),
            new Point(350, -200, -400))
            .setMaterial(new Material().setKR(0).setKs(0.2).setKT(0).setShininess(5000).setKd(0.8));

    /**Polygon representing a part of the grass.*/
    private final Polygon grass2 = (Polygon) new Polygon(
            new Point(40, -200, 0),
            new Point(120, -200, 0),
            new Point(350, -200, -400),
            new Point(150, -200, -400))
            .setMaterial(new Material().setKR(0).setKs(0.2).setKT(0).setShininess(5000).setKd(0.5)); // Dark green

    /**Polygon representing a part of the grass.*/
    private final Polygon grass3 = (Polygon) new Polygon(
            new Point(-40, -200, 0),
            new Point(40, -200, 0),
            new Point(150, -200, -400),
            new Point(-50, -200, -400))
            .setMaterial(new Material().setKR(0).setKs(0.2).setKT(0).setShininess(5000).setKd(0.8)); // Adjusted Z coordinate to be on the same plane

    /**Polygon representing a part of the grass.*/
    private final Polygon grass4 = (Polygon) new Polygon(
            new Point(-120, -200, 0),
            new Point(-40, -200, 0),
            new Point(-50, -200, -400),
            new Point(-250, -200, -400))
            .setMaterial(new Material().setKR(0).setKs(0.2).setKT(0).setShininess(5000).setKd(0.5)); // Adjusted Z coordinate to be on the same plan

    /**Polygon representing a part of the grass.*/
    private final Polygon grass5 = (Polygon) new Polygon(
            new Point(-200, -200, 0),
            new Point(-120, -200, 0),
            new Point(-250, -200, -400),
            new Point(-450, -200, -400))
            .setMaterial(new Material().setKR(0).setKs(0.2).setKT(0).setShininess(5000).setKd(0.8));

    /**Polygon representing a part of the grass.*/
    private final Polygon grass6 = (Polygon) new Polygon(
            new Point(-280, -200, 0),
            new Point(-200, -200, 0),
            new Point(-450, -200, -400),
            new Point(-650, -200, -400))
            .setMaterial(new Material().setKR(0).setKs(0.2).setKT(0).setShininess(5000).setKd(0.5));


    /**Sphere representing a part of the scene.*/
    Sphere ball = (Sphere) new Sphere(15, new Point(70, -180, -90))
            .setMaterial(new Material().setKd(0.42).setKs(0.5).setShininess(100).setKT(0.4).setKR(0)).setEmission(new Color(88, 19, 91));

    /**Sphere representing a part of the scene.*/
    Sphere ball1 = (Sphere) new Sphere(10, new Point(10, -180, -80))
            .setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(100)).setEmission(new Color(136, 223, 216));

    /**Sphere representing a part of the scene.*/
    Sphere ball2 = (Sphere) new Sphere(15, new Point(70, -180, -170))
            .setMaterial(new Material().setKd(0.51).setKs(0.5).setShininess(100)).setEmission(new Color(34, 56, 0));

    /**Sphere representing a part of the scene.*/
    Sphere ball3 = (Sphere) new Sphere(15, new Point(-70, -180, -120))
            .setMaterial(new Material().setKd(0.6222).setKs(0.5).setShininess(100)).setEmission(new Color(GRAY));

    /**Sphere representing a part of the scene.*/
    Sphere ball4 = (Sphere) new Sphere(15, new Point(-50, -180, -50))
            .setMaterial(new Material().setKd(0.55).setKs(0.5).setShininess(100)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKT(0.1).setKR(0.3)).setEmission(new Color(BLUE));

    /**Sphere representing a part of the scene.*/
    Sphere ball5 = (Sphere) new Sphere(8, new Point(0, -180, -120))
            .setMaterial(new Material().setKd(0.43).setKs(0.5).setShininess(100).setKT(0).setKR(0.7)).setEmission(new Color(RED));

    /**
     * Create a multi-object scene with transparency and reflection
     */
    @Test
    public void multiObjectImage() {
        scene.geometries.add(rec1, rec2, rec3, rec4, rec5, rec6, rec7, rec8, rec9, rec10);
        scene.geometries.add(grass0, grass1, grass2, grass3, grass4, grass5, grass6);
        scene.geometries.add(ball, ball1, ball2, ball3, ball4, ball5);
        scene.lights.add(new DirectionalLight(new Vector(0, -60, 20), new Color(0, 220, 0)));
        scene.lights.add(new SpotLight(new Vector(0, -1, 0), new Point(50, -30, 50), new Color(WHITE)).setKl(0.00001).setKq(0.00001));
        camera.setRayTracer(new SimpleRayTracer(scene)).setImageWriter(new ImageWriter("try", 720, 850))
                .build().renderImage().writeToImage();


    }
}
