package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * The Scene class represents a scene in a 3D space, containing geometries, ambient light, and background color.
 */
public class Scene {
    /**
     * The name of the scene.
     */
    public final String name;

    /**
     * The background color of the scene.
     */
    public Color background = Color.BLACK;

    /**
     * The ambient light in the scene.
     */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /**
     * The geometries present in the scene.
     */
    public Geometries geometries = new Geometries();

    /**
     * List of light sources present in the scene.
     */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Sets the list of light sources for this scene.
     * Replaces any existing lights with the new ones provided.
     *
     * @param lights the new list of light sources to set
     * @return the current instance of {@code Scene} with the updated lights
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * Constructor for the Scene class.
     *
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param background The background color to set.
     * @return The current Scene instance (for method chaining).
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight The ambient light to set.
     * @return The current Scene instance (for method chaining).
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries in the scene.
     *
     * @param geometries The geometries to set.
     * @return The current Scene instance (for method chaining).
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
