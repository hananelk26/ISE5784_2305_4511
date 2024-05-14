package geometries;

import primitives.Ray;

public class Cylinder extends Tube{
    private double height;

    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }



}
