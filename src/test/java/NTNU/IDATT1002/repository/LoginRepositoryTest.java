/*
package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.Login;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.utils.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

*/
/**
 * Tests for {@link LoginRepository}
 *
 * @author madslun
 * @version 1.0 17.03.20
 *//*


class LoginRepositoryTest {


    private LoginRepository loginRepository;

    private String username1;
    private String username2;
    private String password;
    private String newPassword;
    private Date date;
    private User user1;
    private User user2;
    private Login login1;
    private Login login2;


    */
/**
     * Sets up some testdata for thorough testing
     * So much information has been added for making sure every part works as intended
     *//*

    @BeforeEach
    public void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplicationTest");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        loginRepository = new LoginRepository(entityManager);

        username1 = "test1";
        username2 = "test2";
        password = "Test123";
        newPassword = "Test321";
        date = new Date(System.currentTimeMillis());
        user1 = new User(username1,"email","firstName", "lastName", "test" , "test", date);
        user2 = new User(username2, "email2" , "firstName2", "lastName2", "test2", "test2", date);
        login1 = new Login(user1, "test", "test");
        login2 = new Login(user2, "test2", "test2");
    }

    */
/**
     * Test that saving an entity returns the saved instance
     *//*

    @Test
    void testSaveReturnsInstance() {
        Optional<Login> optionalLogin = loginRepository.save(login1);
        assertTrue(optionalLogin.isPresent());
    }

    */
/**
     * Test that finding all entities returns all entities
     *//*

    @Test
    void testSaveReturnsAllSavedEntities() {

        loginRepository.save(login1);
        loginRepository.save(login2);

        List<?> foundLogins = loginRepository.findAll();
        assertEquals(2, foundLogins.size());
    }

    */
/**
     * Test that saving invalid entity will fail and return empty optional
     *//*

    @Test
    void testSaveInvalidEntityReturnsEmptyOptional() {
        Optional<Login> savedLogin = loginRepository.save(null);

        assertTrue(savedLogin.isEmpty());
    }

    */
/**
     * Test that finding entity by id returns optional with the correct id
     *//*

    @Test
    void testFindByIdReturnsOptionalWithEntityWithId() {

        loginRepository.save(login1);
        Optional<Login> foundLogins = loginRepository.findById(username1);

        assertTrue(foundLogins.isPresent());
        assertEquals(username1, foundLogins.get().getUser().getUsername());
    }

    */
/**
     * Test that deleting by id removes the given entity and returns empty optional
     *//*

    @Test
    void testDeleteById() {
        loginRepository.save(login1);
        Optional<Login> foundLogins = loginRepository.findById(username1);

        foundLogins.ifPresent(Login -> loginRepository.deleteById(username1));
        Optional<Login> deletedLogin = loginRepository.findById(username1);

        assertTrue(deletedLogin.isEmpty());
    }

    */
/**
     * Test that count returns correct amount of enities
     *//*

    @Test
    void testCountReturnsAmountOfSavedEntities() {
        loginRepository.save(login1);
        loginRepository.save(login2);

        long loginCount = loginRepository.count();

        assertEquals(2, loginCount);
    }

    */
/**
     * Test that a created user can log in
     *//*

    @Test
    void testLogin() {
        ArrayList<String> credentials = Authentication.setPassword(password);
        String salt = credentials.get(0);
        String hash = credentials.get(1);
        Login login3 = new Login(user1, salt, hash);
        loginRepository.save(login3);

        assertTrue(loginRepository.logIn(username1, password));
    }

    */
/**
     * Test that a created user can change the password
     *//*

    @Test
    void testChangePassword() {
        ArrayList<String> credentials = Authentication.setPassword(password);
        String salt = credentials.get(0);
        String hash = credentials.get(1);
        Login login3 = new Login(user1, salt, hash);
        loginRepository.save(login3);

        assertTrue(loginRepository.changePassword(username1, password, newPassword));
    }

    */
/**
     * Test that a user can log in, change password then log in again
     *//*

    @Test
    void testLoginWithNewPassword() {
        ArrayList<String> credentials = Authentication.setPassword(password);
        String salt = credentials.get(0);
        String hash = credentials.get(1);
        Login login3 = new Login(user1, salt, hash);
        loginRepository.save(login3);

        assertTrue(loginRepository.logIn(username1, password));
        assertTrue(loginRepository.changePassword(username1, password, newPassword));
        assertTrue(loginRepository.logIn(username1, newPassword));
    }

    */
/**
     * Test that trying to login with wrong password returns false
     *//*

    @Test
    void testWrongPasswordDoesNotLogIn() {
        ArrayList<String> credentials = Authentication.setPassword(password);
        String salt = credentials.get(0);
        String hash = credentials.get(1);
        Login login3 = new Login(user1, salt, hash);
        loginRepository.save(login3);
        assertFalse(loginRepository.logIn(username1, newPassword));
    }

    */
/**
     * Test that trying to change password with wrong password returns false
     *//*

    @Test
    void testWrongPasswordDoesNotChangePassword() {
        ArrayList<String> credentials = Authentication.setPassword(password);
        String salt = credentials.get(0);
        String hash = credentials.get(1);
        Login login3 = new Login(user1, salt, hash);
        loginRepository.save(login3);
        assertFalse(loginRepository.changePassword(username1, newPassword, password));
        assertTrue(loginRepository.logIn(username1, password));
    }

    */
/**
     * Test that trying to login with null returns false
     *//*

    @Test
    void testLoginWithNullReturnsFalse() {
        ArrayList<String> credentials = Authentication.setPassword(password);
        String salt = credentials.get(0);
        String hash = credentials.get(1);
        Login login3 = new Login(user1, salt, hash);
        loginRepository.save(login3);
        assertFalse(loginRepository.logIn(username1, null));
    }

    */
/**
     * Test that trying to login with null returns false
     *//*

    @Test
    void testChangeWithNullReturnsFalse() {
        ArrayList<String> credentials = Authentication.setPassword(password);
        String salt = credentials.get(0);
        String hash = credentials.get(1);
        Login login3 = new Login(user1, salt, hash);
        loginRepository.save(login3);
        assertFalse(loginRepository.changePassword(username1, null, newPassword));
    }
}*/
