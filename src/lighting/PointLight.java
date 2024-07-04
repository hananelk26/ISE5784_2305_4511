package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class representing a point light source.
 * A point light has a specific position in space and its intensity diminishes with distance.
 */
public class PointLight extends Light implements LightSource {

    /**
     * the position of light
     */
    private final Point position;

    /**
     * The quadratic attenuation coefficient (kQ).
     * This coefficient affects how the intensity of the light decreases over distance squared.
     * Initialized to 0.
     */
    private double kQ = 0;

    /**
     * The linear attenuation coefficient (kL).
     * This coefficient affects how the intensity of the light decreases linearly over distance.
     * Initialized to 0.
     */
    private double kL = 0;

    /**
     * The constant attenuation coefficient (kC).
     * This coefficient provides a base level of attenuation, independent of distance.
     * Initialized to 1.
     */
    private double kC = 1;

    /**
     * Constructs a point light with the specified position and intensity.
     *
     * @param position  the position of the light
     * @param intensity the intensity of the light
     */
    public PointLight(Point position, Color intensity) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the quadratic attenuation coefficient.
     *
     * @param kQ the quadratic attenuation coefficient
     * @return the current instance of {@code PointLight} for method chaining
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Sets the linear attenuation coefficient.
     *
     * @param kL the linear attenuation coefficient
     * @return the current instance of {@code PointLight} for method chaining
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the constant attenuation coefficient.
     *
     * @param kC the constant attenuation coefficient
     * @return the current instance of {@code PointLight} for method chaining
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }


    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return intensity.reduce(kC + kL * d + kQ * d * d);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point){
        return position.distance(point);
    }
}
