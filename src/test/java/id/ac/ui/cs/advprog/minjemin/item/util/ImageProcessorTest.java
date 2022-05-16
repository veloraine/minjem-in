package id.ac.ui.cs.advprog.minjemin.item.util;

import id.ac.ui.cs.advprog.minjemin.item.util.ImageProcessor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ImageProcessorTest {
    ImageProcessor imageProcessor;

    MockMultipartFile file;

    @BeforeEach
    void setUp() {
        imageProcessor = ImageProcessor.getInstance();
        file = new MockMultipartFile("myImage", "Gallade.jpeg", "image/jpeg", "megagallade".getBytes());

    }

    @Test
    void testGetImageProcessorInstanceSingleton(){
        var imageProcessorOne = ImageProcessor.getInstance();
        var imageProcessorTwo = ImageProcessor.getInstance();
        assertEquals(imageProcessorOne, imageProcessorTwo);
    }

    @Test
    void testConvertToByteMethod() throws IOException {
        byte[] expectedBytes = Base64.encodeBase64(file.getBytes());
        byte[] imageBytes = imageProcessor.convertToByte(file);

        assertEquals(expectedBytes.length, imageBytes.length);
        for (int i = 0; i < expectedBytes.length; i++) {
            assertEquals(expectedBytes[i], imageBytes[i]);
        }
    }

    @Test
    void testGenerateStringImages() {
        byte[] testImageBytes = "magnamalo".getBytes();
        String imageString = new String(testImageBytes);
        String imageStringResult = imageProcessor.generateStringImage(testImageBytes);

        assertEquals(imageString, imageStringResult);
    }
}
