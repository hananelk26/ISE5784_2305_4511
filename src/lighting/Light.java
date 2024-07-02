package lighting;

import primitives.Color;

/**
 * Abstract class representing a light source.
 * This class provides the basic properties and methods for any type of light source.
 */
abstract class Light {
    /**
     * The intensity of the light.
     */
    protected final Color intensity;

    /**
     * Constructs a light source with the specified intensity.
     *
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Gets the intensity of the light (the IO).
     *
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }

}
