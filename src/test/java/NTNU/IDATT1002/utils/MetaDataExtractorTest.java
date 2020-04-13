package NTNU.IDATT1002.utils;

import static org.junit.jupiter.api.Assertions.*;

import NTNU.IDATT1002.models.GeoLocation;
import NTNU.IDATT1002.models.Metadata;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link MetaDataExtractor}
 * These test are not the best and are best used as a proof of concept
 * For testing we have used two predefined pictures so we know what metadata is supposed to receive
 *
 * @author madslun
 * @version 1.0 13.04.20
 */
class MetaDataExtractorTest {

  private MetaDataExtractor metaDataExtractor;
  private File geolocationImage;
  private File metadataImage;

  /**
   * Sets up necessary test data for the tests
   */
  @BeforeEach
  void setUp() {
    geolocationImage = new File("src/test/resources/Images/plsWork.jpg");
    metadataImage = new File("src/test/resources/Images/pb.jpg");
    metaDataExtractor = new MetaDataExtractor();
  }

  /**
   * Test extracts geolocation from a chosen image and compares with a predefined string containing expected information
   */
  @Test
  void testGetGeoLocation() {
    String expectedLatitude = "51.504105555555554";
    String expectedLongitude = "-0.074575";
    GeoLocation returnedGeoLocation = metaDataExtractor.getGeoLocation(geolocationImage);
    assertEquals(expectedLatitude, returnedGeoLocation.getLatitude());
    assertEquals(expectedLongitude, returnedGeoLocation.getLongitude());
  }

  /**
   * Test extracts camerainformation from a chosen image and compares with a predefined string containing expected information
   */
  @Test
  void testGetCamera() {
    String expectedCamera = "Make: NIKON CORPORATION, Model: NIKON D610";
    String extractedCamera = metaDataExtractor.getCamera(metadataImage);
    assertEquals(expectedCamera, extractedCamera);
  }

  /**
   * Test extracts lens information from a chosen image and compares with a predefined string containing expected information
   */

  @Test
  void testGetLens() {
    String expectedLens = "Lens Specification: 24-70mm f/2.8, Lens Model: 24.0-70.0 mm f/2.8";
    String extractedLens = metaDataExtractor.getLens(metadataImage);
    assertEquals(expectedLens, extractedLens);
  }

  /**
   * Test extracts aperture information from a chosen image and compares with a predefined string containing expected information
   */
  @Test
  void testGetAperture() {
    String expectedAperture = "Aperture Value: f/2.8, Max Aperture Value: f/2.8";
    String extractedAperture = metaDataExtractor.getAperture(metadataImage);
    assertEquals(expectedAperture, extractedAperture);
  }

  /**
   * Test extracts shutterspeed information from a chosen image and compares with a predefined string containing expected information
   */
  @Test
  void testGetShutterSpeed() {
    String expectedShutterSpeed = "Shutter Speed Value: 1/124 sec";
    String extractedAperture = metaDataExtractor.getShutterSpeed(metadataImage);
    assertEquals(expectedShutterSpeed, extractedAperture);
  }

  /**
   * Test extracts iso information from a chosen image and compares with a predefined string containing expected information
   */
  @Test
  void testGetIso() {
    String expectedIso = "ISO Speed Ratings: 640";
    String extractedIso = metaDataExtractor.getIso(metadataImage);
    assertEquals(expectedIso, extractedIso);
  }

  /**
   * Test extracts focal length from a chosen image and compares with a predefined string containing expected information
   */
  @Test
  void testGetFocalLength() {
    String expectedFocalLength = "Focal Length: 70 mm";
    String extractedFocalLength = metaDataExtractor.getFocalLength(metadataImage);
    assertEquals(expectedFocalLength, extractedFocalLength);
  }

  /**
   * Test extracts file type from a chosen image and compares with a predefined string containing expected information
   */
  @Test
  void testGetFileType() {
    String expectedFileType = "Detected  Name: JPEG";
    String extractedFileType = metaDataExtractor.getFileType(metadataImage);
    System.out.println(extractedFileType);
    assertEquals(expectedFileType, extractedFileType);
  }

  /**
   * Test extracts photo date from a chosen image and compares with a predefined string containing expected information
   */
  @Test
  void testGetPhotoDate() {
    String expectedPhotoDate = "Date Created: 2020:02:15, Time Created: 12:07:01";
    String extractedPhotoDate = metaDataExtractor.getPhotoDate(metadataImage);
    assertEquals(expectedPhotoDate, extractedPhotoDate);
  }

  /**
   * Test extracts file size from a chosen image and compares with a predefined string containing expected information
   */
  @Test
  void testGetFileSize() {
    String expectedFileSize = " Size: 821839 bytes";
    String extractedFileSize = metaDataExtractor.getFileSize(metadataImage);
    assertEquals(expectedFileSize, extractedFileSize);
  }

  /**
   * Test extracts file dimension from a chosen image and compares with a predefined string containing expected information
   */
  @Test
  void testGetFileDimension() {
    String expectedFileDimension = "Image Height: 930 pixels, Image Width: 1394 pixels";
    String extractedFileDimension = metaDataExtractor.getFileDimension(metadataImage);
    assertEquals(expectedFileDimension, extractedFileDimension);
  }
}