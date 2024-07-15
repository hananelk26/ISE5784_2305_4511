package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
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
            .setVpSize(500, 500).setVpDistance(200).setNumOfRays(100).setApertureSize(7).setFocalDistance(50);

    /**
     * points for scene
     */
    private final Point[] points = {
            //gate1
            new Point(-120, -200, 0),
            new Point(-115, -200, 0),
            new Point(-115, -200, 70),
            new Point(-120, -200, 70),

            //gate2
            new Point(120, -200, 0),
            new Point(115, -200, 0),
            new Point(115, -200, 70),
            new Point(120, -200, 70),

            //gate3
            new Point(-120, -200, 70),
            new Point(-120, -200, 75),
            new Point(120, -200, 75),
            new Point(120, -200, 70),

            //gate4
            new Point(-80, -180, 0),
            new Point(-80, -180, 3),
            new Point(80, -180, 3),
            new Point(80, -180, 0),

            //gate5
            new Point(-115, -200, 0),
            new Point(-115, -200, 3),
            new Point(-80, -180, 3),
            new Point(-80, -180, 0),

            //gate6
            new Point(115, -200, 0),
            new Point(115, -200, 3),
            new Point(80, -180, 3),
            new Point(80, -180, 0),

            //gate7
            new Point(120, -200, 74),
            new Point(120, -200, 71),
            new Point(100, -200, 61),
            new Point(100, -200, 64),

            //gate8
            new Point(-120, -200, 74),
            new Point(-120, -200, 71),
            new Point(-100, -200, 61),
            new Point(-100, -200, 64),

            //gate9
            new Point(100, -200, 60),
            new Point(100, -200, 65),
            new Point(80, -180, 5),
            new Point(80, -180, 0),

            //gate10
            new Point(-100, -200, 60),
            new Point(-100, -200, 65),
            new Point(-80, -180, 5),
            new Point(-80, -180, 0),

            //grass1

            new Point(200, -200, 0),
            new Point(280, -200, 0),
            new Point(750, -200, -400),
            new Point(550, -200, -400),

            //grass2
            new Point(120, -200, 0),
            new Point(200, -200, 0),
            new Point(550, -200, -400),
            new Point(350, -200, -400),

            //grass3
            new Point(40, -200, 0),
            new Point(120, -200, 0),
            new Point(350, -200, -400),
            new Point(150, -200, -400),

            //grass4
            new Point(-40, -200, 0),
            new Point(40, -200, 0),
            new Point(150, -200, -400),
            new Point(-50, -200, -400),

            //grass5
            new Point(-120, -200, 0),
            new Point(-40, -200, 0),
            new Point(-50, -200, -400),
            new Point(-250, -200, -400),

            //grass6
            new Point(-200, -200, 0),
            new Point(-120, -200, 0),
            new Point(-250, -200, -400),
            new Point(-450, -200, -400),

            //grass7
            new Point(-280, -200, 0),
            new Point(-200, -200, 0),
            new Point(-450, -200, -400),
            new Point(-650, -200, -400),

            //centerForBall1
            new Point(70, -180, -90),

            //centerForBall2
            new Point(10, -180, -80),

            //centerForBall3
            new Point(70, -180, -170),

            //centerForBall4
            new Point(-70, -180, -120),

            //centerForBall5
            new Point(-50, -180, -50),

            //centerForBall6
            new Point(0, -180, -120)
    };

    /**
     * polygons for the gate
     */
    private final Geometry[] polygonsForGate = {
            new Polygon(points[0], points[1], points[2], points[3]).setEmission(new Color(WHITE)),
            new Polygon(points[4], points[5], points[6], points[7]).setEmission(new Color(WHITE)),
            new Polygon(points[8], points[9], points[10], points[11]).setEmission(new Color(WHITE)),
            new Polygon(points[12], points[13], points[14], points[15]).setEmission(new Color(WHITE)),
            new Polygon(points[16], points[17], points[18], points[19]).setEmission(new Color(WHITE)),
            new Polygon(points[20], points[21], points[22], points[23]).setEmission(new Color(WHITE)),
            new Polygon(points[24], points[25], points[26], points[27]).setEmission(new Color(WHITE)),
            new Polygon(points[28], points[29], points[30], points[31]).setEmission(new Color(WHITE)),
            new Polygon(points[32], points[33], points[34], points[35]).setEmission(new Color(WHITE)),
            new Polygon(points[36], points[37], points[38], points[39]).setEmission(new Color(WHITE))
    };

    /**
     * polygon for the grass
     */
    private final Geometry[] polygonsForGrass = {
            new Polygon(
                    points[40], points[41], points[42], points[43]
            ).setMaterial(new Material().setKR(0).setKs(0.2).setKT(0).setShininess(5000).setKd(0.5)),

            new Polygon(
                    points[44], points[45], points[46], points[47]
            )
                    .setMaterial(new Material().setKR(0).setKs(0.2).setKT(0).setShininess(5000).setKd(0.8)),

            new Polygon(
                    points[48], points[49], points[50], points[51]
            )
                    .setMaterial(new Material().setKR(0).setKs(0.2).setKT(0).setShininess(5000).setKd(0.5)),

            new Polygon(
                    points[52], points[53], points[54], points[55]
            )
                    .setMaterial(new Material().setKR(0).setKs(0.2).setKT(0).setShininess(5000).setKd(0.8)),

            new Polygon(
                    points[56], points[57], points[58], points[59]
            )
                    .setMaterial(new Material().setKR(0).setKs(0.2).setKT(0).setShininess(5000).setKd(0.5)),

            new Polygon(
                    points[60], points[61], points[62], points[63]
            )
                    .setMaterial(new Material().setKR(0).setKs(0.2).setKT(0).setShininess(5000).setKd(0.8)),

            new Polygon(
                    points[64], points[65], points[66], points[67]
            )
                    .setMaterial(new Material().setKR(0).setKs(0.2).setKT(0).setShininess(5000).setKd(0.5))
    };

    /**
     * spheres for balls
     */
    private final Geometry[] ballsForScene = {
            new Sphere(15, points[68])
                    .setMaterial(new Material().setKd(0.42).setKs(0.5).setShininess(100).setKT(0.4).setKR(0)).setEmission(new Color(88, 19, 91)),

            new Sphere(10, points[69])
                    .setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(100)).setEmission(new Color(136, 223, 216)),

            new Sphere(15, points[70])
                    .setMaterial(new Material().setKd(0.51).setKs(0.5).setShininess(100)).setEmission(new Color(34, 56, 0)),

            new Sphere(15, points[71])
                    .setMaterial(new Material().setKd(0.6222).setKs(0.5).setShininess(100)).setEmission(new Color(GRAY)),

            new Sphere(15, points[72])
                    .setMaterial(new Material().setKd(0.55).setKs(0.5).setShininess(100)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKT(0.1).setKR(0.3)).setEmission(new Color(BLUE)),


            new Sphere(12, points[73])
                    .setMaterial(new Material().setKd(0.43).setKs(0.5).setShininess(100).setKT(0).setKR(0.2)).setEmission(new Color(RED)),

    };

    /**
     * Create a multi-object scene with transparency and reflection
     */
    @Test
    public void multiObjectImage() {
        scene.geometries.add(polygonsForGate[0], polygonsForGate[1], polygonsForGate[2], polygonsForGate[3],
                polygonsForGate[4], polygonsForGate[5], polygonsForGate[6], polygonsForGate[7], polygonsForGate[8], polygonsForGate[9]
        );

        scene.geometries.add(polygonsForGrass[0], polygonsForGrass[1], polygonsForGrass[2], polygonsForGrass[3],
                polygonsForGrass[4], polygonsForGrass[5], polygonsForGrass[6]
        );

        scene.geometries.add(ballsForScene[0], ballsForScene[1], ballsForScene[2], ballsForScene[3],
                ballsForScene[4], ballsForScene[5]
        );

        scene.geometries.add(new Triangle(new Point(-70, -180, -180),

                //centerForBall5
                new Point(-50, -180, -150),

                //centerForBall6
                new Point(-30, -180, -180)).setEmission(new Color(BLUE)).setMaterial(new Material().setKs(0.4).setKR(0.4).setKd(0.9)));

        scene.lights.add(new DirectionalLight(new Vector(0, -60, 20), new Color(0, 220, 0)));
        scene.lights.add(new SpotLight(new Vector(0, -1, 0), new Point(50, -30, 50), new Color(WHITE)).setKl(0.00001).setKq(0.00001));
        scene.lights.add(new PointLight(new Point(-30, -200, -180), new Color(YELLOW)));
        camera.setRayTracer(new SimpleRayTracer(scene)).setImageWriter(new ImageWriter("try", 720, 850))
                .build().renderImage().writeToImage();


    }
}
