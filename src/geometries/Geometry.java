package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Abstract class for geometric objects in a 3D space.
 * Any geometric object should be able to provide the normal vector at a given point on its surface.
 * Extends the {@code Intersectable} class.
 * This class provides a foundation for all geometric shapes, ensuring they can be intersected and have a normal vector calculated.
 */
public abstract class Geometry extends Intersectable {

    /**
     * The emission color of the geometry.
     */
    protected Color emission = Color.BLACK;

    /**
     * The material of the geometric object.
     */
    private Material material = new Material();

    /**
     * Gets the material of the geometric object.
     *
     * @return the material of the geometric object
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material of the geometric object.
     *
     * @param material the new material to set
     * @return the current instance of {@code Geometry} for method chaining
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

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
