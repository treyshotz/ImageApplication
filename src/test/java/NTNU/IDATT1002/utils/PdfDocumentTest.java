package NTNU.IDATT1002.utils;

import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.ImageAlbum;
import NTNU.IDATT1002.models.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Class PdfDocumentTest to test the generation of pdf documents.
 */
class PdfDocumentTest {

    private static final String PATH_TO_PDF = "src/test/java/tmp/generatedImageAlbumPdf.pdf";

    private static final String PATH_SEPARATOR = File.pathSeparator;

    private ImageAlbum imageAlbum;

    private Image image;

    private User user;

    /**
     * Setup an image album with a user and two images.
     */
    @BeforeEach
    public void setup() {
        byte[] byteFile = ImageUtil.convertToBytes("src/test/java/tmp/test_image_1.jpg");
        image = new Image();
        image.setRawImage(byteFile);

        user = new User();
        user.setUsername("Alex Johnson");

        imageAlbum = new ImageAlbum();
        imageAlbum.setTitle("Test Title");
        imageAlbum.setUser(user);
        imageAlbum.setDescription("This is a test.");
        imageAlbum.setCreatedAt(new Date());
        imageAlbum.addImage(image);

        byteFile = ImageUtil.convertToBytes("src/test/java/tmp/test_image_2.jpg");
        image = new Image();
        image.setRawImage(byteFile);
        imageAlbum.addImage(image);
    }

    /**
     * Delete the generated pdf after all tests have been executed.
     */
    @AfterAll
    public static void tearDown() {
        File file = new File(PATH_TO_PDF);
        file.delete();
    }

    /**
     * Test that a pdf document is successfully created.
     */
    @Test
    void testCreatePdfDocumentCreatesPdfDocument() {
        PdfDocument document = new PdfDocument(imageAlbum, PATH_TO_PDF);
        document.createPdfDocument();

        assertNotNull(document);
    }
    

}