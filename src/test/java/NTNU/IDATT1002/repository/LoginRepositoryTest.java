package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.Login;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.utils.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoginRepositoryTest {


    private LoginRepository loginRepository;

    private String id1;
    private String id2;
    private String password;
    private String newPassword;
    private Date date;
    private User user1;
    private User user2;
    private Login login1;
    private Login login2;



    @BeforeEach
    public void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplicationTest");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        id1 = "test1";
        id2 = "test2";
        password = "Test123";
        newPassword = "Test321";
        date = new Date(System.currentTimeMillis());
        user1 = new User("epost", id1, "fornavn", "etternavn", "test" , "test", date);
        user2 = new User("epost2" , id2, "fornavn2", "etternavn2", "test2", "test2", date);
        login1 = new Login(user1, "test", "test");
        login2 = new Login(user2, "test2", "test2");
        loginRepository = new LoginRepository(entityManager);
    }

    @Test
    void testSaveReturnsInstance() {
        Optional<Login> optionalLogin = loginRepository.save(login1);
        assertTrue(optionalLogin.isPresent());
    }

    @Test
    void testSaveReturnsAllSavedEntities() {

        loginRepository.save(login1);
        loginRepository.save(login2);

        List<?> foundLogins = loginRepository.findAll();
        assertEquals(2, foundLogins.size());
    }

    @Test
    void testSaveInvalidEntityReturnsEmptyOptional() {
        Optional<Login> savedLogin = loginRepository.save(null);

        assertTrue(savedLogin.isEmpty());
    }

    @Test
    void testFindByIdReturnsOptionalWithEntityWithId() {

        loginRepository.save(login1);
        Optional<Login> foundLogins = loginRepository.findById(id1);

        assertTrue(foundLogins.isPresent());
        assertEquals(id1, foundLogins.get().getUser().getUsername());
    }

    @Test
    void testDeleteById() {
        loginRepository.save(login1);
        Optional<Login> foundLogins = loginRepository.findById(id1);

        foundLogins.ifPresent(Login -> loginRepository.deleteById(id1));
        Optional<Login> deletedLogin = loginRepository.findById(id1);

        assertTrue(deletedLogin.isEmpty());
    }

    @Test
    void testCountReturnsAmountOfSavedEntities() {
        loginRepository.save(login1);
        loginRepository.save(login2);

        long loginCount = loginRepository.count();

        assertEquals(2, loginCount);
    }

    @Test
    void testLogin() {
        ArrayList<String> credentials = Authentication.setPassword(password);
        String salt = credentials.get(0);
        String hash = credentials.get(1);
        Login login3 = new Login(user1, salt, hash);
        loginRepository.save(login3);

        assertTrue(loginRepository.logIn(id1, password));
    }

    @Test
    void testChangePassword() {
        ArrayList<String> credentials = Authentication.setPassword(password);
        String salt = credentials.get(0);
        String hash = credentials.get(1);
        Login login3 = new Login(user1, salt, hash);
        loginRepository.save(login3);

        assertTrue(loginRepository.changePassword(id1, password, newPassword));
    }

    @Test
    void testLoginWithNewPassword() {
        ArrayList<String> credentials = Authentication.setPassword(password);
        String salt = credentials.get(0);
        String hash = credentials.get(1);
        Login login3 = new Login(user1, salt, hash);
        loginRepository.save(login3);

        assertTrue(loginRepository.logIn(id1, password));
        assertTrue(loginRepository.changePassword(id1, password, newPassword));
        assertTrue(loginRepository.logIn(id1, newPassword));
    }

    @Test
    void testWrongPasswordDoesNotLogIn() {
        ArrayList<String> credentials = Authentication.setPassword(password);
        String salt = credentials.get(0);
        String hash = credentials.get(1);
        Login login3 = new Login(user1, salt, hash);
        loginRepository.save(login3);
        assertFalse(loginRepository.logIn(id1, newPassword));
    }

    @Test
    void testWrongPasswordDoesNotChangePassword() {
        ArrayList<String> credentials = Authentication.setPassword(password);
        String salt = credentials.get(0);
        String hash = credentials.get(1);
        Login login3 = new Login(user1, salt, hash);
        loginRepository.save(login3);
        assertFalse(loginRepository.changePassword(id1, newPassword, password));
        assertTrue(loginRepository.logIn(id1, password));
    }

    @Test
    void testLoginWithNullReturnsFalse() {
        ArrayList<String> credentials = Authentication.setPassword(password);
        String salt = credentials.get(0);
        String hash = credentials.get(1);
        Login login3 = new Login(user1, salt, hash);
        loginRepository.save(login3);
        assertFalse(loginRepository.logIn(id1, null));
    }

    @Test
    void testChangeWithNullReturnsFalse() {
        ArrayList<String> credentials = Authentication.setPassword(password);
        String salt = credentials.get(0);
        String hash = credentials.get(1);
        Login login3 = new Login(user1, salt, hash);
        loginRepository.save(login3);
        assertFalse(loginRepository.changePassword(id1, null, newPassword));
    }
}