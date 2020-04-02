package NTNU.IDATT1002.utils;

import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.service.PdfDocument;
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

    private static final String PATH_TO_PDF = "src/test/java/tmp/generatedAlbumPdf.pdf";
    private Album album;
    private Image image;
    private User user;

    /**
     * Setup an album with a user and two images.
     */
    @BeforeEach
    public void setup() {
        byte[] byteFile = ImageUtil.convertToBytes("src/test/java/tmp/test_image_1.jpg");
        image = new Image();
        image.setRawImage(byteFile);

        user = new User();
        user.setUsername("Alex Johnson");

        album = new Album();
        album.setTitle("Test Title");
        album.setUser(user);
        album.setDescription("This is a test.");
        album.setCreatedAt(new Date());
        album.addImage(image);

        byteFile = ImageUtil.convertToBytes("src/test/java/tmp/test_image_2.jpg");
        image = new Image();
        image.setRawImage(byteFile);
        album.addImage(image);
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
    void testCreateDocumentCreatesPdfDocument() {
        PdfDocument document = new PdfDocument(album, PATH_TO_PDF);
        File pdfFile = document.getDocument();

        assertNotNull(document);
        assertNotNull(pdfFile);
    }
    

}