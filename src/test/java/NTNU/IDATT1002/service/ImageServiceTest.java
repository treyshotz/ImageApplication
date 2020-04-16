package NTNU.IDATT1002.service;

import static org.junit.jupiter.api.Assertions.*;

import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.models.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link ImageService}
 *
 * @author madslun
 * @version 1.0 06.04.20
 */

class ImageServiceTest {

  private UserService userService;

  private final String EMAIL = "test@test.no";
  private final String USERNAME = "Test_Username";
  private final String USERNAME2 = "Test_Username2";
  private final String FIRST_NAME = "Test_First_Name";
  private final String LAST_NAME = "Test_Last_Name";
  private final String CALLING_CODE = "Test_Calling_Code";
  private final String PHONE_NUMBER = "Test_Phone_Number";
  private final Date DATE = new Date(System.currentTimeMillis());
  private final String PASSWORD = "Test123";


  private File testImage1;
  private File testImage2;
  private User user;
  private User user2;
  private List<Tag> tags;
  private Tag tag;
  private static final Long IMAGE_INITIAL_ID = 1L;
  private ImageService imageService;

  /**
   * Sets up necessary data for testing
   * To be able to save a image a user has to be saved first, therefore i also include UserService
   * This test unfortunately relies on UserService because of this
   */
  @BeforeEach
  void setUp() {
    testImage1 = new File("src/test/resources/Images/plsWork.jpg");
    testImage2 = new File("src/test/java/tmp/test_image_2.jpg");
    tags = new ArrayList<>();
    tag = new Tag("Test");
    tags.add(tag);
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplicationTest");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    imageService = new ImageService(entityManager);
    userService = new UserService(entityManager);
    user = userService.createUser(EMAIL, USERNAME, FIRST_NAME, LAST_NAME, CALLING_CODE, PHONE_NUMBER, DATE, PASSWORD).get();
    user2 = userService.createUser(EMAIL, USERNAME2, FIRST_NAME, LAST_NAME, CALLING_CODE, PHONE_NUMBER, DATE, PASSWORD).get();

  }

  /**
   * Tests that you can create a image with valid input
   */
  @Test
  void testCreateImageReturnsOptionalWithImage() {
    Optional<Image> createdImage = imageService.createImage(user, testImage1, tags);
    assertTrue(createdImage.isPresent());
    assertEquals(IMAGE_INITIAL_ID, createdImage.get().getId());
  }

  /**
   * Test that you cannot create a image when the file is null
   */
  @Test
  void testCreateImageReturnsEmptyOptionalWithInvalidEntity() {
    Optional<Image> createdImage;
    createdImage = imageService.createImage(user, null, tags);
    assertTrue(createdImage.isEmpty());
  }

  /**
   * Test that all images from a user it returned as a list
   */
  @Test
  void testGetImageFromUserReturnsCorrectImages() {
    imageService.createImage(user, testImage1, tags);
    imageService.createImage(user,testImage1, tags);
    List<Image> foundImages = imageService.getImageFromUser(user);
    assertEquals(2, foundImages.size());
    assertEquals(IMAGE_INITIAL_ID , foundImages.get(0).getId());
  }

  /**
   * Test that makes sure only one image is returned when two different users saves an image
   */
  @Test
  void testGetImageFromUserOnlyReturnsOneInstance() {
    imageService.createImage(user, testImage1, tags);
    imageService.createImage(user2, testImage2, tags);
    List<Image> foundImages = imageService.getImageFromUser(user);
    assertEquals(1, foundImages.size());
    assertEquals(IMAGE_INITIAL_ID, foundImages.get(0).getId());
  }

  /**
   * Test that all images ever created is returned
   */
  @Test
  void testGetAllImagesReturnsAllImages() {
    imageService.createImage(user, testImage1, tags);
    imageService.createImage(user2, testImage2, tags);
    List<Image> foundImages = imageService.getAllImages();
    assertEquals(2, foundImages.size());
  }
}
