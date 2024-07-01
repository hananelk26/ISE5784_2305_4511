package primitives;

/**
 * The Material class represents the optical properties of a geometric object.
 * It includes parameters such as diffuse reflection coefficient (kD), specular reflection coefficient (kS),
 * and shininess (Shininess) which affects the size of the specular highlight.
 */
public class Material {

    /**
     * Diffuse reflection coefficient (kD) and specular reflection coefficient (kS) of the material.
     * Both coefficients are initialized to zero (represented as Double3.ZERO).
     */
    public Double3 kD = Double3.ZERO, kS = Double3.ZERO;

    /**
     * Shininess parameter affecting the size of the specular highlight.
     */
    public int Shininess = 0;

    /**
     * Sets the shininess parameter of the material.
     *
     * @param shininess The shininess value to set.
     * @return This Material object with the updated shininess value.
     */
    public Material setShininess(int shininess) {
        Shininess = shininess;
        return this;
    }

    /**
     * Sets the specular reflection coefficient (kS) of the material.
     *
     * @param kS The specular reflection coefficient as a Double3 object.
     * @return This Material object with the updated specular reflection coefficient.
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the specular reflection coefficient (kS) of the material.
     *
     * @param kS The specular reflection coefficient as a double.
     * @return This Material object with the updated specular reflection coefficient.
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient (kD) of the material.
     *
     * @param kD The diffuse reflection coefficient as a Double3 object.
     * @return This Material object with the updated diffuse reflection coefficient.
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient (kD) of the material.
     *
     * @param kD The diffuse reflection coefficient as a double.
     * @return This Material object with the updated diffuse reflection coefficient.
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }
}
