package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Camera implements Cloneable {
    Point p;
    Vector vTo;
    Vector vUp;
    Vector vRight;
    double height = 0.0;
    double width = 0.0;
    double distance = 0.0;

    public static class Builder {
        private final Camera camera = new Camera();
    }

    private Camera() {
    }

    public Builder getBuilder() {
        return null;
    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }

    public Builder setLocation(Point point) {
        return null;
    }

    public Builder setDirection(Vector vTo, Vector vUp) {
        return null;
    }

    public Builder setVpSize(double height, double width) {
        return null;
    }

    public Builder setVpDistance(double distance) {
        return null;
    }

    public Camera build() {
        return null;
    }


}
