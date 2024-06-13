package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    final private Color intensity;

    AmbientLight(Color intensity, Double3 kA) {
        this.intensity = intensity.scale(kA);
    }

    AmbientLight(Color intensity, double kA) {
        this.intensity = intensity.scale(kA);
    }

    final public static AmbientLight NONE = new AmbientLight(Color.BLACK,0);

    public Color getIntensity(){
        return this.intensity;
    }
}
