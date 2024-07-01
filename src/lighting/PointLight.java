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
    private Point position;

    /**
     * Attenuation coefficients for light sources.
     * kQ represents the quadratic attenuation coefficient.
     * kL represents the linear attenuation coefficient.
     * kC represents the constant attenuation coefficient.
     */
    private double kQ = 0, kL = 0, kC = 1;

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
    public PointLight setKC(double kC) {
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
}
