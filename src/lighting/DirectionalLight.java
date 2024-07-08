package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class representing a directional light source.
 * A directional light has a specific direction and a uniform intensity across all points.
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * the direction of the light
     */
    private final Vector direction;

    /**
     * Constructs a directional light with the specified direction and intensity.
     *
     * @param direction the direction of the light
     * @param intensity the intensity of the light
     */
    public DirectionalLight(Vector direction, Color intensity) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
