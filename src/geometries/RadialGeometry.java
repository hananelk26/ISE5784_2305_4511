package geometries;

/**
 * Represents a radial geometry in a 3D space.
 * Radial geometry is defined by a radius.
 */
public abstract class RadialGeometry implements Geometry {
    /**
     * The radius of the radial geometry.
     */

    final protected double radius;

    /**
     * Constructs a radial geometry with the specified radius.
     *
     * @param radius the radius of the radial geometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
