package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

public class ImageWriterTest {
    @Test
    public void writeToImageTest() {
        int height = 500;
        int width = 800;
        ImageWriter imageWriter = new ImageWriter("testImage", width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i,j,new Color(java.awt.Color.ORANGE));
                }
                else{
                    imageWriter.writePixel(i,j,new Color(java.awt.Color.BLUE));
                }
            }
        }

//        for (int i = 0; i < width; i+=50) {
//            for (int j = 0; j < height; j+=1) {
//                imageWriter.writePixel(i,j,new Color(java.awt.Color.YELLOW));
//            }
//        }
//
//        for (int i = 0; i < width; i+=1) {
//            for (int j = 0; j < height; j+=50) {
//                imageWriter.writePixel(i,j,new Color(java.awt.Color.YELLOW));
//            }
//        }



        imageWriter.writeToImage();
    }
}
