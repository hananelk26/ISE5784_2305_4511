package geometries;

import static primitives.Util.alignZero;

/**
 * Represents a radial geometry in a 3D space.
 * Radial geometry is defined by a radius.
 */
public abstract class RadialGeometry extends Geometry {
    /**
     * The radius of the radial geometry.
     */
    final protected double radius;

    /**
     * The squared radius of the radial geometry.
     */
    final protected double radiusSquared;

    /**
     * Constructs a radial geometry with the specified radius.
     *
     * @param radius the radius of the radial geometry
     */
    public RadialGeometry(double radius) {
        if (alignZero(radius) <= 0)
            throw new IllegalArgumentException("A radius can't be <= 0");
        this.radius = radius;
        radiusSquared = radius * radius;
    }

    @Override
    public void calcBoundingBox() {}
}
