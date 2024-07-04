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
    Color getIntensity(Point p);

    /**
     * Gets the direction vector from the light source to a specific point.
     *
     * @param p the point to which the direction vector is to be calculated
     * @return the direction vector from the light source to the given point
     */
    Vector getL(Point p);

    /**
     * @param point get the point on the geometry body
     * @return the distance from the point to the light source
     */
    double getDistance(Point point);

}
