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
    public Triangle(Point p1,Point p2 ,Point p3) {
        super(p1,p2,p3);
    }
}
