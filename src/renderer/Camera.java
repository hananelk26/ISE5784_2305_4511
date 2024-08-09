package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.LinkedList;
import java.util.List;
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
     * The level of blur in the image
     */
    private double apertureSize = 1.0;

    /**
     * The distance of the focus point
     */
    private double focalDistance = 10.0;

    /**
     * additional rays for the depth of field
     */
    private int numOfAdditionalRays = 0;

    /**
     * Use BVH for rendering
     */
    private boolean usingInBVH = true;

    /**
     * Use CBR for rendering
     */
    private boolean usingInCBR = false;

    /**
     * Pixel manager for supporting:
     * <ul>
     * <li>multi-threading</li>
     * <li>debug print of progress percentage in Console window/tab</li>
     * </ul>
     */
    private PixelManager pixelManager;

    /**
     * Number of threads to use for rendering
     */
    private int threadsCount = 4;


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
         * Sets the aperture size for the Camera.
         *
         * @param apertureSize the aperture size to set
         * @return the Builder instance for chaining
         */
        public Builder setApertureSize(double apertureSize) {
            this.camera.apertureSize = apertureSize;
            return this;
        }

        /**
         * Sets the focal distance for the Camera.
         *
         * @param focalDistance the focal distance to set
         * @return the Builder instance for chaining
         */
        public Builder setFocalDistance(double focalDistance) {
            this.camera.focalDistance = focalDistance;
            return this;
        }

        /**
         * Sets the number of rays for depth of field effect.
         *
         * @param numOfRays the number of rays to set
         * @return the Builder instance for chaining
         */
        public Builder setNumOfRays(int numOfRays) {
            this.camera.numOfAdditionalRays = numOfRays;
            return this;
        }

        /**
         * Set the BVH usage
         *
         * @param use true to use BVH, false otherwise
         * @return the camera builder
         */
        public Builder useBVH(boolean use) {
            camera.usingInBVH = use;
            return this;
        }

        /**
         * Sets the usage of the Constant Background Ray (CBR) feature.
         *
         * @param use true to enable CBR; false to disable
         * @return the builder instance for method chaining
         */
        public Builder useCBR(boolean use) {
            camera.usingInCBR = use;
            return this;
        }

        /**
         * Set the number of threads to use for rendering
         *
         * @param threadsCount the number of threads to use for rendering
         * @return the camera builder
         */
        public Builder setMultithreading(int threadsCount) {

            camera.threadsCount = threadsCount;
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

            if (isZero(this.camera.width))
                throw new MissingResourceException("Missing rendering data", "Camera", "width");
            if (isZero(this.camera.height))
                throw new MissingResourceException("Missing rendering data", "Camera", "height");
            if (isZero(this.camera.distance))
                throw new MissingResourceException("Missing rendering data", "Camera", "distance");

            if (this.camera.vTo == null) throw new MissingResourceException("Missing rendering data", "Camera", "vTo");
            if (this.camera.vUp == null) throw new MissingResourceException("Missing rendering data", "Camera", "vUp");
            if (!isZero(this.camera.vTo.dotProduct(this.camera.vUp)))
                throw new IllegalArgumentException("Vectors must be orthogonal");
            this.camera.vRight = this.camera.vUp.crossProduct(this.camera.vTo).normalize();

            if (this.camera.imageWriter == null)
                throw new MissingResourceException("Missing rendering data", "Camera", "imageWriter");
            if (this.camera.rayTracer == null)
                throw new MissingResourceException("Missing rendering data", "Camera", "rayTracer");

            if (camera.threadsCount < 0)
                throw new MissingResourceException("threads count can't be smaller than 0", "Camera","threadsCount");
            try {

                return (Camera) this.camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(e);
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
        return constructRays(nX, nY, j, i).getFirst();
    }

    /**
     * Renders the image by casting rays for each pixel.
     *
     * @return The current Camera instance (for method chaining).
     */
    public Camera renderImage() {
        if (usingInBVH) rayTracer.scene.geometries.makeBVH();
        else if (usingInCBR) rayTracer.scene.geometries.makeCBR();

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        pixelManager = new PixelManager(nY, nX);

        if (threadsCount == 0)
            for (int i = 0; i < nX; i++) {
                for (int j = 0; j < nY; j++) {
                    castRay(nX, nY, i, j);
                }
            }
        else { // see further... option 2
            var threads = new LinkedList<Thread>(); // list of threads
            while (threadsCount-- > 0) // add appropriate number of threads
                threads.add(new Thread(() -> { // add a thread with its code
                    PixelManager.Pixel pixel; // current pixel(row,col)
                    // allocate pixel(row,col) in loop until there are no more pixels
                    while ((pixel = pixelManager.nextPixel()) != null)
                        // cast ray through pixel (and color it – inside castRay)
                        castRay(nX, nY, pixel.col(), pixel.row());
                }));
            // start all the threads
            for (var thread : threads) thread.start();
            // wait until all the threads have finished
            try {
                for (var thread : threads) thread.join();
            } catch (InterruptedException ignore) {
            }
        }

        return this;
    }

    /**
     * Casts multiple rays for a specific pixel to achieve depth of field and writes the average color to the image.
     *
     * @param nX The number of pixels in the x-direction.
     * @param nY The number of pixels in the y-direction.
     * @param j  The x-coordinate of the pixel.
     * @param i  The y-coordinate of the pixel.
     */
    private void castRay(int nX, int nY, int j, int i) {
        List<Ray> rays = this.constructRays(nX, nY, j, i);
        Color pixelColor = Color.BLACK;
        for (Ray ray : rays) {
            pixelColor = pixelColor.add(rayTracer.traceRay(ray));
        }
        pixelColor = pixelColor.reduce(rays.size());
        imageWriter.writePixel(j, i, pixelColor);
    }

    /**
     * Constructs a list of rays through a given pixel on the view plane for depth of field effect.
     *
     * @param nX number of horizontal pixels
     * @param nY number of vertical pixels
     * @param j  the column index of the pixel
     * @param i  the row index of the pixel
     * @return a list of Ray objects passing through the specified pixel
     */
    public List<Ray> constructRays(int nX, int nY, int j, int i) {
        List<Ray> rays = new LinkedList<>();
        // Calculate distance on x,y axes to the designated point
        Point pIJ = p0.add(vTo.scale(distance));
        double yI = (((nY - 1) / 2.0) - i) * (height / nY);
        double xJ = (((nX - 1) / 2.0) - j) * (width / nX);
        // Avoiding creation of zero vector (which is unnecessary anyway)
        if (!isZero(xJ))
            pIJ = pIJ.add(vRight.scale(xJ));
        if (!isZero(yI))
            pIJ = pIJ.add(vUp.scale(yI));

        // Primary ray from the camera position to the pixel on the view plane
        Ray primaryRay = new Ray(p0, pIJ.subtract(p0));
        rays.add(primaryRay);

        // Generate additional rays for depth of field effect
        for (int k = 0; k < numOfAdditionalRays; k++) {// Number of additional rays
            // Generate random points on the lens aperture
            double randomX = (Math.random() - 0.5) * apertureSize;
            double randomY = (Math.random() - 0.5) * apertureSize;
            Point pointLens = p0.add(vRight.scale(randomX)).add(vUp.scale(randomY));
            // Calculate the direction towards the focal plane
            Vector direction = pIJ.add(vTo.scale(focalDistance)).subtract(pointLens);
            rays.add(new Ray(pointLens, direction));
        }

        return rays;
    }

    /**
     * Prints a grid on the image with the specified interval and color.
     *
     * @param interval The interval between grid lines.
     * @param color    The color of the grid lines.
     * @return The current Camera instance (for method chaining).
     */
    public Camera printGrid(int interval, Color color) {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nX; i += interval) {
            for (int j = 0; j < nY; j += 1) {
                imageWriter.writePixel(i, j, color);
            }
        }

        for (int i = 0; i < nX; i += 1) {
            for (int j = 0; j < nY; j += interval) {
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
