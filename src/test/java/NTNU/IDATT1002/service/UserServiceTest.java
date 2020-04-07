package NTNU.IDATT1002.service;

import static org.junit.jupiter.api.Assertions.*;

import NTNU.IDATT1002.models.User;

import java.util.Date;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link UserService}
 *
 * @author madslun
 * @version 1.0 06.04.20
 */
class UserServiceTest {

  private UserService userService;

  private final String EMAIL = "test@test.no";
  private final String USERNAME = "Test_Username";
  private final String FIRST_NAME = "Test_First_Name";
  private final String LAST_NAME = "Test_Last_Name";
  private final String CALLING_CODE = "Test_Calling_Code";
  private final String PHONE_NUMBER = "Test_Phone_Number";
  private final Date DATE = new Date(System.currentTimeMillis());
  private final String PASSWORD = "Test123";


  /**
   * Sets up test data for testing the service. Also uses a local test database instead of production database
   */
  @BeforeEach
  void setUp() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplicationTest");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    userService = new UserService(entityManager);
  }

  /**
   * Test that creates a user and makes sure the user was created correctly
   */
  @Test
  void createUserWithValidInput() {
    Optional<User> createdUser = userService.createUser(EMAIL, USERNAME, FIRST_NAME, LAST_NAME, CALLING_CODE, PHONE_NUMBER, DATE, PASSWORD);
    assertTrue(createdUser.isPresent());
    assertEquals(createdUser.get().getUsername(), USERNAME);
  }

  /**
   * Test that creates a user with null as email than checks that null was saved as email
   */
  @Test
  void createUserWithInvalidInputCreatesUser() {
    Optional<User> createdUser = userService.createUser(null, USERNAME, FIRST_NAME, LAST_NAME, CALLING_CODE, PHONE_NUMBER, null, PASSWORD);
    assertTrue(createdUser.isPresent());
    assertEquals(createdUser.get().getEmail(), null);
  }

  /**
   * Test that creates a user then proceeds to login with wrong password
   */
  @Test
  void loginWithWrongCredentials() {
    String wrongPassword = "Test321";
    Optional<User> createdUser = userService.createUser(EMAIL, USERNAME, FIRST_NAME, LAST_NAME, CALLING_CODE, PHONE_NUMBER, DATE, PASSWORD);
    assertTrue(createdUser.isPresent());
    assertFalse(userService.logIn(USERNAME, wrongPassword));
  }

  /**
   * Test that creates a user then proceeds to login with given user
   */
  @Test
  void loginWithCorrectCredentials() {
    Optional<User> createdUser = userService.createUser(EMAIL, USERNAME, FIRST_NAME, LAST_NAME, CALLING_CODE, PHONE_NUMBER, DATE, PASSWORD);
    assertTrue(createdUser.isPresent());
    assertTrue(userService.logIn(USERNAME, PASSWORD));
  }

  /**
   * Test that creates a user, changes the password on the user, then proceeds to login with the new password
   */
  @Test
  void changePasswordWithCorrectCredentials() {
    String newPassword = "Test321";
    Optional<User> createdUser = userService.createUser(EMAIL, USERNAME, FIRST_NAME, LAST_NAME, CALLING_CODE, PHONE_NUMBER, DATE, PASSWORD);
    assertTrue(createdUser.isPresent());
    assertTrue(userService.changePassword(USERNAME, PASSWORD, newPassword));
    assertFalse(userService.logIn(USERNAME, PASSWORD));
    assertTrue(userService.logIn(USERNAME, newPassword));
  }

  /**
   * Test that creates a user, tries to change password with wrong credentials, the proceeds to get failed login with wrong password
   */
  @Test
  void changePasswordWithWrongCredentialsReturnsFalse() {
    String wrongPassword = "Test321";
    Optional<User> createdUser = userService.createUser(EMAIL, USERNAME, FIRST_NAME, LAST_NAME, CALLING_CODE, PHONE_NUMBER, DATE, PASSWORD);
    assertTrue(createdUser.isPresent());
    assertTrue(createdUser.isPresent());
    assertFalse(userService.changePassword(USERNAME, wrongPassword, wrongPassword));
    assertFalse(userService.logIn(USERNAME, wrongPassword));
  }
}