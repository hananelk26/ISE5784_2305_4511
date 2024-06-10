package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.MissingResourceException;

/**
 * Represents a Camera with position, direction, and view plane parameters.
 */
public class Camera implements Cloneable {
    /**
     * The position of the camera in 3D space.
     */
    private Point p0;

    /**
     * The forward direction vector of the camera.
     */
    private Vector vTo;

    /**
     * The upward direction vector of the camera.
     */
    private Vector vUp;

    /**
     * The rightward direction vector of the camera.
     */
    private Vector vRight;

    /**
     * The height of the view plane.
     */
    private double height = 0.0;

    /**
     * The width of the view plane.
     */
    private double width = 0.0;

    /**
     * The distance from the camera to the view plane.
     */
    private double distance = 0.0;

    /**
     * Builder class for constructing a Camera object.
     */
    public static class Builder {
        /**
         * The object of Camera class
         */
        private final Camera camera = new Camera();

        /**
         * Sets the location of the camera.
         *
         * @param p0 the position of the camera
         * @return the Builder instance
         */
        public Builder setLocation(Point p0) {
            camera.p0 = p0;
            return this;
        }

        /**
         * Sets the direction of the camera using vTo and vUp vectors.
         * The vectors must be orthogonal.
         *
         * @param vTo the forward direction vector
         * @param vUp the upward direction vector
         * @return the Builder instance
         * @throws IllegalArgumentException if the vectors are not orthogonal
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (!isZero(vTo.dotProduct(vUp)))
                throw new IllegalArgumentException("vTo dotProduct must be orthogonal");
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * Sets the size of the view plane.
         *
         * @param height the height of the view plane
         * @param width  the width of the view plane
         * @return the Builder instance
         * @throws IllegalArgumentException if height or width are not greater than 0
         */
        public Builder setVpSize(double height, double width) {
            if (alignZero(height) <= 0.0) {
                throw new IllegalArgumentException("Height must be greater than 0");
            }
            if (alignZero(width) <= 0.0) {
                throw new IllegalArgumentException("Width must be greater than 0");
            }
            camera.height = height;
            camera.width = width;
            return this;
        }

        /**
         * Sets the distance from the camera to the view plane.
         *
         * @param distance the distance to the view plane
         * @return the Builder instance
         * @throws IllegalArgumentException if distance is not greater than 0
         */
        public Builder setVpDistance(double distance) {
            if (distance < 0.0 || isZero(distance)) {
                throw new IllegalArgumentException("Distance must be greater than 0");
            }
            camera.distance = distance;
            return this;
        }

        /**
         * Builds the Camera object after validating all necessary fields are set.
         *
         * @return the constructed Camera object
         * @throws MissingResourceException if any required fields are missing
         */
        public Camera build() {
            if (this.camera.p0 == null)
                throw new MissingResourceException("Missing rendering data", "Camera", "location");
            if (this.camera.vTo == null) throw new MissingResourceException("Missing rendering data", "Camera", "vTo");
            if (this.camera.vUp == null) throw new MissingResourceException("Missing rendering data", "Camera", "vUp");
            if (isZero(this.camera.width))
                throw new MissingResourceException("Missing rendering data", "Camera", "width");
            if (isZero(this.camera.height))
                throw new MissingResourceException("Missing rendering data", "Camera", "height");
            if (isZero(this.camera.distance))
                throw new MissingResourceException("Missing rendering data", "Camera", "distance");
            this.camera.vRight = this.camera.vUp.crossProduct(this.camera.vTo).normalize();
            if (!isZero(this.camera.vTo.dotProduct(this.camera.vUp))) {
                throw new IllegalArgumentException("Vectors must be orthogonal");
            }

            try {
                return (Camera) this.camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * A private default constructor for the Camera class.
     * This constructor is private to prevent instantiation without parameters.
     * Use the parameterized constructor to create a Camera instance.
     */
    private Camera() {
    }

    /**
     * Returns a new Builder instance for constructing a Camera.
     *
     * @return a new Camera.Builder instance
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray through a given pixel on the view plane.
     *
     * @param nX number of horizontal pixels
     * @param nY number of vertical pixels
     * @param j  the column index of the pixel
     * @param i  the row index of the pixel
     * @return a Ray object passing through the specified pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pIJ = p0.add(vTo.scale(distance));
        // Calculate distance on x,y axes to the designated point
        double yI = (((nY - 1) / 2.0) - i) * (height / nY);
        double xJ = (((nX - 1) / 2.0) - j) * (width / nX);
        // Avoiding creation of zero vector (which is unnecessary anyway)
        if (!isZero(xJ))
            pIJ = pIJ.add(vRight.scale(xJ));
        if (!isZero(yI))
            pIJ = pIJ.add(vUp.scale(yI));
        return new Ray(p0, pIJ.subtract(p0));
    }


}
