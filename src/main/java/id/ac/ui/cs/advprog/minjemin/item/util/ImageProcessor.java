package id.ac.ui.cs.advprog.minjemin.item.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImageProcessor {

    private static ImageProcessor imageProcessor = null;
    private ImageProcessor(){
    }
    public static ImageProcessor getInstance() {
        if (imageProcessor == null) {
            imageProcessor = new ImageProcessor();
        }
        return imageProcessor;
    }
    public byte[] convertToByte(MultipartFile file) throws IOException {
        return Base64.encodeBase64(file.getBytes());
    }

    public String generateStringImage(byte[] imageBytes) {
        return new String(imageBytes);
    }
}
