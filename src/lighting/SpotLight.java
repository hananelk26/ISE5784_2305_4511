package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static java.lang.Math.max;

public class SpotLight extends PointLight {
    Vector direction;

    public SpotLight(Vector direction, Point position, Color intensity) {
        super(position, intensity);
        this.direction = direction;
    }

    public PointLight setKQ(double kQ) {
        return super.setKQ(kQ);
    }

    public PointLight setKL(double kL) {
        return super.setKL(kL);
    }

    public PointLight setKC(double kC) {
        return super.setKC(kC);
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(max(0,super.getL(p).dotProduct(direction)));
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
