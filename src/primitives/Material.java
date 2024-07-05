package primitives;

/**
 * The Material class represents the optical properties of a geometric object.
 * It includes parameters such as diffuse reflection coefficient (kD), specular reflection coefficient (kS),
 * and shininess (Shininess) which affects the size of the specular highlight.
 */
public class Material {

    /**
     * The diffuse coefficient of the material.
     * Represents how much light is scattered when it hits the surface.
     * Initialized to {@code Double3.ZERO}.
     */
    public Double3 kD = Double3.ZERO;

    /**
     * The specular coefficient of the material.
     * Represents the reflection of light in a specific direction.
     * Initialized to {@code Double3.ZERO}.
     */
    public Double3 kS = Double3.ZERO;

    /**
     * Shininess parameter affecting the size of the specular highlight.
     */
    public int Shininess = 0;

    /**
     * The transmission coefficient of the material.
     */
    public Double3 kT = Double3.ZERO;

    /**
     * The reflection coefficient of the material.
     */
    public Double3 kR = Double3.ZERO;

    /**
     * Sets the transmission coefficient of the material.
     *
     * @param kT The new transmission coefficient.
     * @return The current Material object.
     */
    public Material setKT(Double3 kT){
        this.kT = kT;
        return this;
    }

    /**
     * Sets the reflection coefficient of the material.
     *
     * @param kR The new reflection coefficient.
     * @return The current Material object.
     */
    public Material setKR(Double3 kR){
        this.kR = kR;
        return this;
    }

    /**
     * Sets the transmission coefficient of the material.
     *
     * @param kT The new transmission coefficient as a double.
     * @return The current Material object.
     */
    public Material setKT(double kT){
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Sets the reflection coefficient of the material.
     *
     * @param kR The new reflection coefficient as a double.
     * @return The current Material object.
     */
    public Material setKR(double kR){
        this.kR = new Double3(kR);
        return this;
    }


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
