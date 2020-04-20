package NTNU.IDATT1002.utils;


import NTNU.IDATT1002.models.Image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Image Util class for performing {@link Image} related utility operations,
 * such as converting a file to a byte array and {@link Image} to {@link javafx.scene.image.Image}.
 */
public class ImageUtil {

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

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
            logger.error("[x] Something went wrong while trying to convert to bytes" ,e);
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

    /**
     * Resizes and image to be suitable as markers on map.
     * @param image that will be resized
     * @return string with resized image in HTML img format
     */
    public static String createSmallerMarkers(Image image) {
        int markerHeight = 30;
        int markerWidth = 30;
        byte[] imageBytes = image.getRawImage();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            java.awt.Image scaledImage = bufferedImage.getScaledInstance(markerWidth, markerHeight, java.awt.Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(markerWidth, markerHeight, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, "jpg", buffer);

            imageBytes = buffer.toByteArray();
        } catch (IOException e) {
            logger.error("[x] Something went wrong during creating a smaller marker", e);
        }

        String imageMarkerPath = "data:image/png;base64," + DatatypeConverter.printBase64Binary(imageBytes);
        return imageMarkerPath;
    }
}
