package lighting;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Interface representing a light source.
 * Any light source should be able to provide its intensity at a given point and the direction vector to that point.
 */
public interface LightSource {

    /**
     * Gets the intensity of the light at a specific point (the IL).
     *
     * @param p the point at which the light intensity is to be calculated
     * @return the intensity of the light at the given point
     */
    public Color getIntensity(Point p);

    /**
     * Gets the direction vector from the light source to a specific point.
     *
     * @param p the point to which the direction vector is to be calculated
     * @return the direction vector from the light source to the given point
     */
    public Vector getL(Point p);



}
