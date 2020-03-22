package NTNU.IDATT1002.utils;

import java.io.File;
import java.io.FileInputStream;

public class ImageUtil {

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
}
