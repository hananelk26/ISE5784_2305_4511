package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    private Point position;
    private double kQ = 0, kL = 0, kC = 1;

    public PointLight(Point position, Color intensity) {
        super(intensity);
        this.position = position;
    }

    public PointLight setKQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    public PointLight setKL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKC(double kC) {
        this.kC = kC;
        return this;
    }


    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return intensity.reduce(kC + kL * d + kQ * d * d);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }
}
