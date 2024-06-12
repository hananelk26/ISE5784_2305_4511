package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

public class ImageWriterTest {
    @Test
    public void testImageWriter() {
        int height = 500;
        int width = 800;
        ImageWriter image = new ImageWriter("testImage", width, height);
        Color colorBeckRound = new Color(255, 255, 255);
        Color colorGrid = new Color(0, 0, 0);



    }
}
