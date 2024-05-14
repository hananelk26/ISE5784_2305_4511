package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{
    final private Point p;
    final private Vector normal;

    public Plane(Point p1,Point p2,Point p3) {
        Vector vector1 = p2.subtract(p1);
        Vector vector2 = p3.subtract(p2);
        this.normal = vector1.crossProduct(vector2).normalize();
        this.p = p1;
    }

    public Plane(Point p,Vector normal) {
        this.normal = normal.normalize();
        this.p = p;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }


}
