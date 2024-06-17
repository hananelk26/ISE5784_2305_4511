package renderer;

import primitives.Color;
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
     * ImageWriter field
     */
    private ImageWriter imageWriter;

    /**
     * RayTracerBase field
     */
    private RayTracerBase rayTracer;

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
         * Sets the ImageWriter for the Camera.
         *
         * @param imageWriter the ImageWriter to set
         * @return the Builder instance for chaining
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            this.camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the RayTracer for the Camera.
         *
         * @param rayTracer the RayTracerBase to set
         * @return the Builder instance for chaining
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            this.camera.rayTracer = rayTracer;
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
            if (!isZero(this.camera.vTo.dotProduct(this.camera.vUp)))
                throw new IllegalArgumentException("Vectors must be orthogonal");
            if (this.camera.imageWriter == null)
                throw new MissingResourceException("Missing rendering data", "Camera", "imageWriter");
            if (this.camera.rayTracer == null)
                throw new MissingResourceException("Missing rendering data", "Camera", "rayTracer");
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

    /**
     * Renders the image by casting rays for each pixel.
     *
     * @return The current Camera instance (for method chaining).
     */
    public Camera renderImage() {
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                castRay(imageWriter.getNx(), imageWriter.getNy(), i, j);
            }
        }
        return this;
    }

    /**
     * Casts a ray for a specific pixel and writes the color to the image.
     *
     * @param nX The number of pixels in the x-direction.
     * @param nY The number of pixels in the y-direction.
     * @param j  The x-coordinate of the pixel.
     * @param i  The y-coordinate of the pixel.
     */
    private void castRay(int nX, int nY, int j, int i) {
        Ray rayConstruct = this.constructRay(nX, nY, j, i);
        Color pixelColor = rayTracer.traceRay(rayConstruct);
        imageWriter.writePixel(j, i, pixelColor);
    }


    /**
     * Prints a grid on the image with the specified interval and color.
     *
     * @param interval The interval between grid lines.
     * @param color    The color of the grid lines.
     * @return The current Camera instance (for method chaining).
     */
    public Camera printGrid(int interval, Color color) {
        for (int i = 0; i < imageWriter.getNx(); i += interval) {
            for (int j = 0; j < imageWriter.getNy(); j += 1) {
                imageWriter.writePixel(i, j, color);
            }
        }

        for (int i = 0; i < imageWriter.getNx(); i += 1) {
            for (int j = 0; j < imageWriter.getNy(); j += interval) {
                imageWriter.writePixel(i, j, color);
            }
        }
        return this;
    }

    /**
     * Writes the image to the file system.
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

}
