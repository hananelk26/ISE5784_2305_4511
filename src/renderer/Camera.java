package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

public class Camera implements Cloneable {
    Point p0;
    Vector vTo;
    Vector vUp;
    Vector vRight;
    double height = 0.0;
    double width = 0.0;
    double distance = 0.0;

    public static class Builder {
        private final Camera camera = new Camera();

        public Builder setLocation(Point p0) {
            camera.p0 = p0;
            return this;
        }

        public Builder setDirection(Vector vTo, Vector vUp) {
            if(!isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("Vectors must be ortogonall");
            }
            camera.vTo = vTo;
            camera.vUp = vUp;
            return this;
        }

        public Builder setVpSize(double height, double width) {
            if(height < 0.0 || isZero(height))   {
                throw new IllegalArgumentException("Height must be greater than 0");
            }
            if(width < 0.0 || isZero(width)) {
                throw new IllegalArgumentException("Width must be greater than 0");
            }
            camera.height = height;
            camera.width = width;
            return this;
        }

        public Builder setVpDistance(double distance) {
            if(isZero(distance) || distance < 0.0)   {
                throw new IllegalArgumentException("Distance must be greater than 0");
            }
            camera.distance = distance;
            return this;
        }

        public Camera build() {
            if (this.camera.location.equals(new Point(0, 0, 0))) throw new MissingResourceException("Missing rendering data", Camera.class.getName(), "location");
            if (this.camera.vTo.equals(new Vector(0, 0, 0))) throw new MissingResourceException("Missing rendering data", Camera.class.getName(), "vTo");
            if (this.camera.vUp.equals(new Vector(0, 0, 0))) throw new MissingResourceException("Missing rendering data", Camera.class.getName(), "vUp");
            if (this.camera.viewPlaneWidth == 0) throw new MissingResourceException("Missing rendering data", Camera.class.getName(), "viewPlaneWidth");
            if (this.camera.viewPlaneHeight == 0) throw new MissingResourceException("Missing rendering data", Camera.class.getName(), "viewPlaneHeight");
            if (this.camera.viewPlaneDistance == 0) throw new MissingResourceException("Missing rendering data", Camera.class.getName(), "viewPlaneDistance");

            return this.camera.clone();
        }
    }

    private Camera() {
    }

    public Builder getBuilder() {
        return new Builder();
    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }




}
