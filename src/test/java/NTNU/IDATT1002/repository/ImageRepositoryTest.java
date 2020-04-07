package NTNU.IDATT1002.repository;

import static org.junit.jupiter.api.Assertions.*;

import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

/**
 * Test for {@link ImageRepository}
 */
class ImageRepositoryTest {

  private ImageRepository imageRepository;
  private UserRepository userRepository;
  private static final Long IMAGE_INITIAL_ID = 1L;
  private User user;
  private String username;

  /**
   * Sets up necessary test data for testing
   * For testing a method in the repository it is necessary to include userRepository
   * This test then unfortunately relies on userRepostiory working
   */
  @BeforeEach
  void setUp() {
    username = "test123";
    user = new User();
    user.setUsername(username);
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplicationTest");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    imageRepository = new ImageRepository(entityManager);
    userRepository = new UserRepository(entityManager);
    userRepository.save(user);
  }


  /**
   * Test that tries to save a image and makes sure the image was saved
   */
  @Test
  void testSaveReturnsInstance() {
    Optional<Image> savedImage = imageRepository.save(new Image());

    assertTrue(savedImage.isPresent());
    assertEquals(IMAGE_INITIAL_ID, savedImage.get().getId());
  }

  /**
   * Test that tries to save a Image with invalid entity and makes sure the Image was not saved
   */
  @Test
  void testSaveInvalidEntityReturnsEmptyOptional() {
    Optional<Image> savedImage = imageRepository.save(null);

    assertTrue(savedImage.isEmpty());
  }

  /**
   * Test that tries to save two images and make sure both are returned
   */
  @Test
  void testFindAllReturnsAllSavedEntities() {
    imageRepository.save(new Image());
    imageRepository.save(new Image());

    List<?> foundImages = imageRepository.findAll();
    assertEquals(2, foundImages.size());
  }

  /**
   * Test that tries to save two images to a user and makes sure both are returned
   */
  @Test
  void testFindAllByUserReturnsCorrectAmount() { ;
    Image testImage = new Image();
    testImage.setUser(user);
    Image testImage2 = new Image();
    testImage2.setUser(user);
    imageRepository.save(testImage);
    imageRepository.save(testImage2);

    List<?> foundImages = imageRepository.findAllByUsername(username);
    assertEquals(2, foundImages.size());
  }

  /**
   * Test that save a image and finds the image by id
   */
  @Test
  void testFindByIdReturnsOptionalWithCorrectId() {
    imageRepository.save(new Image());
    Optional<Image> foundImage = imageRepository.findById(IMAGE_INITIAL_ID);

    assertTrue(foundImage.isPresent());
    assertEquals(IMAGE_INITIAL_ID, foundImage.get().getId());
  }

  /**
   * Test that deletes image by id and makes sure image was deleted
   */
  @Test
  void testDeleteByIdRemovesEntity() {
    imageRepository.save(new Image());

    Optional<Image> foundImage = imageRepository.findById(IMAGE_INITIAL_ID);

    foundImage.ifPresent(image -> imageRepository.deleteById(IMAGE_INITIAL_ID));
    Optional<Image> deletedImage = imageRepository.findById(IMAGE_INITIAL_ID);

    assertTrue(deletedImage.isEmpty());
  }

  /**
   * Test that deletes Image object and makes sure the image was deleted
   */
  @Test
  void testDeleteRemovesEntity() {
    imageRepository.save(new Image());

    Optional<Image> foundImage = imageRepository.findById(IMAGE_INITIAL_ID);

    foundImage.ifPresent(imageRepository::delete);
    Optional<Image> deletedImage = imageRepository.findById(IMAGE_INITIAL_ID);

    assertTrue(deletedImage.isEmpty());
  }
}