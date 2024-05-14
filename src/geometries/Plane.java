package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{
    private Point p;
    private Vector normal;

    public Plane(Point p1,Point p2,Point p3) {
        this.normal = null;
        this.p = p1;
    }

    public Plane(Point p,Vector normal) {
        this.normal = normal.normalize();
        this.p = p;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }


}
