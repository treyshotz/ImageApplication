package NTNU.IDATT1002.utils;


import NTNU.IDATT1002.models.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;


/**
 * Image Util class for performing {@link Image} related utility operations,
 * such as converting a file to a byte array and {@link Image} to {@link javafx.scene.image.Image}.
 */
public class ImageUtil {

    /**
     * Convert the file on the given path to a byte array.
     *
     * @param path the path to the file to be converted
     * @return the file, represented as a byte array
     */
    public static byte[] convertToBytes(String path) {
        File file = new File(path);
        byte[] byteFile = new byte[(int) file.length()];

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(byteFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return byteFile;
    }

    /**
     * Convert the given domain image to a javafx image which can be displayed by a controller.
     *
     * @param image the image to convert
     * @return the converted image.
     */
    public static javafx.scene.image.Image convertToFXImage(Image image) {
        ByteArrayInputStream inputStream = ImageUtil.getInputStream(image);
        return new javafx.scene.image.Image(inputStream);
    }

    private static ByteArrayInputStream getInputStream(Image image) {
        return new ByteArrayInputStream(image.getRawImage());
    }
}
