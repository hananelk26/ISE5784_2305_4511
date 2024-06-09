package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;
import java.util.MissingResourceException;

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
                throw new IllegalArgumentException("Vectors must be orthogonal");
            }
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
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
            if(distance < 0.0 || isZero(distance))   {
                throw new IllegalArgumentException("Distance must be greater than 0");
            }
            camera.distance = distance;
            return this;
        }

        public Camera build() {
            if (this.camera.p0 == null) throw new MissingResourceException("Missing rendering data", "Camera", "location");
            if (this.camera.vTo == null) throw new MissingResourceException("Missing rendering data", "Camera", "vTo");
            if (this.camera.vUp == null) throw new MissingResourceException("Missing rendering data", "Camera", "vUp");
            if (this.camera.width == 0.0) throw new MissingResourceException("Missing rendering data", "Camera", "width");
            if (this.camera.height == 0.0) throw new MissingResourceException("Missing rendering data", "Camera", "height");
            if (this.camera.distance == 0) throw new MissingResourceException("Missing rendering data", "Camera", "distance");
            this.camera.vRight = this.camera.vTo.crossProduct(this.camera.vUp).normalize();

            try {
                return (Camera) this.camera.clone();
            }
            catch(CloneNotSupportedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    private Camera() {
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }




}
