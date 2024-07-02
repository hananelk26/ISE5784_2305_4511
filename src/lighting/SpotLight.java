package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import primitives.Util;

import static java.lang.Math.pow;

/**
 * Class representing a spotlight source, which is a type of point light source with a directional beam.
 * A spotlight has a specific position, direction, and intensity that diminishes with distance and beam direction.
 */
public class SpotLight extends PointLight {

    /**
     * the direction of the light
     */
    Vector direction;

    /**
     * The narrowness factor of the spotlight's beam.
     */
    private double narrowBeam = 1;

    /**
     * Sets the narrowness factor of the spotlight's beam.
     *
     * @param narrowBeam the narrowness factor (1 for default, higher values for narrower beams)
     * @return the current instance of {@code SpotLight} for method chaining
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    /**
     * Constructs a spotlight with the specified direction, position, and intensity.
     *
     * @param direction the direction of the spotlight's beam
     * @param position  the position of the spotlight
     * @param intensity the intensity of the spotlight
     */
    public SpotLight(Vector direction, Point position, Color intensity) {
        super(position, intensity);
        this.direction = direction.normalize();
    }

    /**
     * Sets the quadratic attenuation coefficient.
     *
     * @param kQ the quadratic attenuation coefficient
     * @return the current instance of {@code SpotLight} for method chaining
     */
    @Override
    public SpotLight setKq(double kQ) {
        return (SpotLight) super.setKq(kQ);
    }

    /**
     * Sets the linear attenuation coefficient.
     *
     * @param kL the linear attenuation coefficient
     * @return the current instance of {@code SpotLight} for method chaining
     */
    @Override
    public SpotLight setKl(double kL) {
        return (SpotLight) super.setKl(kL);
    }

    /**
     * Sets the constant attenuation coefficient.
     *
     * @param kC the constant attenuation coefficient
     * @return the current instance of {@code SpotLight} for method chaining
     */
    @Override
    public SpotLight setKc(double kC) {
        return (SpotLight) super.setKc(kC);
    }

    @Override
    public Color getIntensity(Point p) {
        double cosine = Util.alignZero(super.getL(p).dotProduct(direction));
        return cosine <= 0 ? Color.BLACK
                : super.getIntensity(p).scale(narrowBeam != 1 ? pow(cosine, narrowBeam) : cosine);
    }
}
