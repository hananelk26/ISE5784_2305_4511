package geometries;

import primitives.Point;

/**
 * Represents a triangle in a 3D space.
 * A triangle is a polygon with exactly three vertices.
 */
public class Triangle extends Polygon {
    /**
     * Constructs a Triangle with all the points of the triangle.
     * @param vertices the points of the triangle
     * @throws IllegalArgumentException if the number of vertices is not equal to 3
     */
    public Triangle(Point... vertices) {
        super(vertices);
        if (vertices.length != 3) {
            throw new IllegalArgumentException("A triangle must have exactly 3 vertices");
        }
    }
}
