package NTNU.IDATT1002.repository;

import static org.junit.jupiter.api.Assertions.*;

import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.service.UserService;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link UserRepository}
 */
class UserRepositoryTest {

  private UserRepository userRepository;
  private String testUsername;
  private User testUser;


  /**
   * Sets up necessary testdata for testin
   */
  @BeforeEach
  void setUp() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplicationTest");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    userRepository = new UserRepository(entityManager);

    testUser = new User();
    testUsername = "Test123";
    testUser.setUsername(testUsername);
  }

  /**
   * Test that save a user and makes sure the user was saved
   */
  @Test
  void testSaveReturnsInstance() {
    Optional<User> savedUser = userRepository.save(testUser);

    assertTrue(savedUser.isPresent());
    assertEquals(testUsername, savedUser.get().getUsername());
  }

  /**
   * Test that tries to save user with invalid entity and make sure an empty optional is returned
   */
  @Test
  void testSaveInvalidEntityRetrunsEmptyOptional() {
    Optional<User> savedUser = userRepository.save(null);

    assertTrue(savedUser.isEmpty());
  }

  /**
   * Test that saves two users and makes sure both are saved
   */
  @Test
  void testFindAllReturnsAllSavedEntities() {
    User testUser2 = new User();
    String testUsername2 = "Test321";
    testUser2.setUsername(testUsername2);

    userRepository.save(testUser);
    userRepository.save(testUser2);

    List<?> foundUsers = userRepository.findAll();

    assertEquals(2, foundUsers.size());
  }

  /**
   * Test that saves a user and makes sure correct user is returned when searching by id
   */
  @Test
  void testFindByIdReturnsOptionalWithCorrectEntity() {
    userRepository.save(testUser);
    Optional<User> foundUSer = userRepository.findById(testUsername);

    assertTrue(foundUSer.isPresent());
    assertEquals(testUsername, foundUSer.get().getUsername());
  }

  /**
   * Test that deletes a saved user by id and makes sure it is deleted
   */
  @Test
  void testDeleteByIdRemovesEntitiy() {
    userRepository.save(testUser);
    Optional<User> foundUser = userRepository.findById(testUsername);

    foundUser.ifPresent(user -> userRepository.deleteById(testUsername));
    Optional<User> deletedUser = userRepository.findById(testUsername);

    assertTrue(deletedUser.isEmpty());
  }

  /**
   * Test that deletes a saved user object and makes sure it is deleted
   */
  @Test
  void testDeleteRemovesEntity() {
    userRepository.save(testUser);
    Optional<User> foundUser = userRepository.findById(testUsername);

    foundUser.ifPresent(userRepository::delete);
    Optional<User> deletedUser = userRepository.findById(testUsername);

    assertTrue(deletedUser.isEmpty());
  }
}