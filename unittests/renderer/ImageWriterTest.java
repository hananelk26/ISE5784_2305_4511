package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static java.awt.Color.*;

/**
 * Test class for ImageWriter.
 * This test writes a test image with a grid pattern where the grid lines are red
 * and the background is blue. The grid lines are drawn every 50 pixels.
 */
public class ImageWriterTest {
    /**
     * Test method for writing an image with a grid pattern.
     * The image will be 500 pixels in height and 800 pixels in width.
     * The grid lines will be red and drawn every 50 pixels, and the rest of the pixels will be blue.
     */
    @Test
    public void writeToImageTest() {
        final int height = 501;
        final int width = 801;
        final ImageWriter imageWriter = new ImageWriter("testImage", width, height);
        final int interval = 50;
        final Color color1 = new Color(RED);
        final Color color2 = new Color(BLUE);

        // Loop through all pixels and set their colors based on their position.
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Draw red lines every 50 pixels
                // Fill the rest with blue
                imageWriter.writePixel(i, j,
                        i % interval == 0 || j % interval == 0 ? color1 : color2);
            }
        }

        // Write the image to file
        imageWriter.writeToImage();
    }
}
