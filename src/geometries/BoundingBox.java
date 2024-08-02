package geometries;

import primitives.Double3;
import primitives.Point;

public class BoundingBox {
    private Point min = new Point(new Double3(Double.POSITIVE_INFINITY));
    private Point max = new Point(new Double3(Double.NEGATIVE_INFINITY));



    public BoundingBox(Point min, Point max) {
        this.min = min;
        this.max = max;
    }
}
