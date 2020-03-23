/*
package NTNU.IDATT1002.service;

import NTNU.IDATT1002.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.AssertTrue;
import java.lang.management.OperatingSystemMXBean;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

  private UserService userService;
  private String email;
  private String username;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String callingCode;
  private String password;
  private Date date;

  @BeforeEach
  void setUp() {
     userService = new UserService();
     email = "email";
     username = "test";
     firstName = "test";
     lastName = "testesen";
     phoneNumber = "12345678";
     callingCode = "+47";
     password = "Test123";
     date = new Date(System.currentTimeMillis());
  }

  @Test
  void testCreateuser() {
  Optional<User> user = userService.createUser(email, username, firstName, lastName, callingCode, phoneNumber, date, password);
  assertTrue(user.isPresent());
  assertEquals(username, user.get().getUsername());
  }

  @Test
  void testChangePassword() {
    String newPassword = "Test321";
    Optional<User> user = userService.createUser(email, username, firstName, lastName, callingCode, phoneNumber, date, password);
    assertTrue(user.isPresent());
    assertTrue(userService.changePassword(username, password, newPassword));
  }

  @Test
  void testLogIn() {
    Optional<User> user = userService.createUser(email, username, firstName, lastName, callingCode, phoneNumber, date, password);
    assertTrue(user.isPresent());
    assertTrue(userService.logIn(username, password));
  }
}*/
