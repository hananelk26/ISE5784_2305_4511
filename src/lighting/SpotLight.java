package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import primitives.Util;

import static java.lang.Math.max;
import static java.lang.Math.pow;

public class SpotLight extends PointLight {
    Vector direction;
    private double narrowBeam = 1;

    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    public SpotLight(Vector direction, Point position, Color intensity) {
        super(position, intensity);
        this.direction = direction.normalize();
    }

    public PointLight setKQ(double kQ) {
        return super.setKq(kQ);
    }

    public PointLight setKL(double kL) {
        return super.setKl(kL);
    }

    public PointLight setKC(double kC) {
        return super.setKC(kC);
    }

    @Override
    public Color getIntensity(Point p) {
        double numOfDotProduct = Util.alignZero( super.getL(p).dotProduct(direction));
        return super.getIntensity(p).scale(numOfDotProduct > 0? (narrowBeam != 1? pow(numOfDotProduct,narrowBeam): numOfDotProduct):0);
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
