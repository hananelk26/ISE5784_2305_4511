package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import primitives.Util;

import static java.lang.Math.max;
import static java.lang.Math.pow;

/**
 * Class representing a spot light source, which is a type of point light source with a directional beam.
 * A spot light has a specific position, direction, and intensity that diminishes with distance and beam direction.
 */
public class SpotLight extends PointLight {

    /**
     * the direction of the light
     */
    Vector direction;

    /**
     * The narrowness factor of the spot light's beam.
     */
    private double narrowBeam = 1;

    /**
     * Sets the narrowness factor of the spot light's beam.
     *
     * @param narrowBeam the narrowness factor (1 for default, higher values for narrower beams)
     * @return the current instance of {@code SpotLight} for method chaining
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    /**
     * Constructs a spot light with the specified direction, position, and intensity.
     *
     * @param direction the direction of the spot light's beam
     * @param position  the position of the spot light
     * @param intensity the intensity of the spot light
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
    public PointLight setKQ(double kQ) {
        return super.setKq(kQ);
    }

    /**
     * Sets the linear attenuation coefficient.
     *
     * @param kL the linear attenuation coefficient
     * @return the current instance of {@code SpotLight} for method chaining
     */
    public PointLight setKL(double kL) {
        return super.setKl(kL);
    }

    /**
     * Sets the constant attenuation coefficient.
     *
     * @param kC the constant attenuation coefficient
     * @return the current instance of {@code SpotLight} for method chaining
     */
    public PointLight setKC(double kC) {
        return super.setKC(kC);
    }

    @Override
    public Color getIntensity(Point p) {
        double numOfDotProduct = Util.alignZero(super.getL(p).dotProduct(direction));
        return super.getIntensity(p).scale(numOfDotProduct > 0 ? (narrowBeam != 1 ? pow(numOfDotProduct, narrowBeam) : numOfDotProduct) : 0);
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
