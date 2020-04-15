package NTNU.IDATT1002.utils;

import NTNU.IDATT1002.models.Metadata;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetadataStringFormatterTest {

    private File metadataImage;
    private Metadata metadata = new Metadata();
    private final String formattedMetadata = "Geolocation: No geolocation found.  Camera: Make: NIKON CORPORATION, Model: NIKON D610  Lens: Lens Specification: 24-70mm f/2,8, Lens Model: 24.0-70.0 mm f/2.8  Aperture: Aperture Value: f/2,8, Max Aperture Value: f/2,8  Shutter Speed: Shutter Speed Value: 1/124 sec  Iso: ISO Speed Ratings: 640  Focal Length: Focal Length: 70 mm  File Type: Detected  Name: JPEG  Photo Date: Date Created: 2020:02:15, Time Created: 12:07:01  File Size:  Size: 821839 bytes  File Dimension: Image Height: 930 pixels, Image Width: 1394 pixels";

    @BeforeEach
    void setUp() {
        metadataImage = new File("src/test/resources/Images/pb.jpg");
        MetaDataExtractor.setMetadata(metadata, metadataImage);
    }

    /**
     * Test that metadata string is properly formatted.
     */
    @Ignore
    @Test
    void testFormatReturnsFormattedString() {
        String metadataString = MetadataStringFormatter.format(metadata, ' ');

        assertEquals(metadataString.trim(), formattedMetadata.trim());
    }
}