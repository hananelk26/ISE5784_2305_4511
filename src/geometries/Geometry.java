package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Interface for geometric objects in a 3D space.
 * Any geometric object should be able to provide the normal vector at a given point on its surface.
 * extends Intersectable class.
 */
public abstract class Geometry extends Intersectable {

    /**
     * The emission color of the geometry.
     */
    protected Color emission = Color.BLACK;

    /**
     * Returns the emission color of the geometry.
     *
     * @return the emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param c the new emission color
     * @return the geometry itself, for chaining purposes
     */
    public Geometry setEmission(Color c) {
        emission = c;
        return this;
    }


    /**
     * Returns the normal vector to the geometric object at the given point.
     *
     * @param point the point on the geometric object
     * @return the normal vector to the geometric object at the given point
     */
    public abstract Vector getNormal(Point point);
}
