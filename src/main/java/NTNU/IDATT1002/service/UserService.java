package NTNU.IDATT1002.service;

import NTNU.IDATT1002.ApplicationState;
import NTNU.IDATT1002.models.Login;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.repository.LoginRepository;
import NTNU.IDATT1002.repository.UserRepository;
import NTNU.IDATT1002.utils.Authentication;
import org.apache.log4j.helpers.AbsoluteTimeDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * User Service
 * @author madslun
 * @version 1.0 22.03.20
 */

public class UserService {

    private LoginRepository loginRepository;
    private UserRepository userRepository;

    /**
     * Inject entity manager instance to the repositories
     */
    public UserService() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplication");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        this.loginRepository = new LoginRepository(entityManager);
        this.userRepository = new UserRepository(entityManager);
    }

    /**
     * Creates a new user and sets password on the new user
     *
     * @param email that will added to the user
     * @param username that will be registered to the user
     * @param firstName of the user
     * @param lastName of the user
     * @param callingCode of the phone number
     * @param phoneNumber of the user
     * @param birthDate of the user
     * @param password that will be set to login
     * @return Optional with the user
     */
    public Optional<User> createUser(String email, String username, String firstName, String lastName, String callingCode, String phoneNumber, Date birthDate, String password) {
        User user = new User(email, username, firstName, lastName, callingCode, phoneNumber, birthDate);
        Login login = new Login();
        login.setUser(user);
        userRepository.save(user);
        setPassword(username, password);

        return userRepository.save(user);
    }

    /**
     * Logs a user in if correct credentials is given
     *
     * @param username that will be checked against
     * @param password that will be compared to database
     * @return
     */
    public boolean logIn(String username, String password) {
        try {
            Optional<Login> login = loginRepository.findById(username);
            if (login.isPresent()) {
                String salt = login.get().getPasswordSalt();
                String hash = login.get().getHash();
                return Authentication.isCorrectPassword(salt, password, hash);
            }
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Searches for a user by username, checks if the old password matches, sets new password if old is right
     *
     * @param username that will be searched for
     * @param oldPassword that will be compared to database
     * @param newPassword that will be set
     * @return
     */
    private boolean changePassword(String username, String oldPassword, String newPassword) {
        ArrayList<String> info = new ArrayList<>();
        try {
            Optional<Login> login = loginRepository.findById(username);
            if(login.isPresent()) {
                String salt = login.get().getPasswordSalt();
                String expectedHash = login.get().getHash();
                if(Authentication.isCorrectPassword(salt, oldPassword,expectedHash)) {
                    info = Authentication.setPassword(newPassword);
                    String saltString = info.get(0);
                    String hashString = info.get(1);
                    login.get().setPasswordSalt(saltString);
                    login.get().setHash(hashString);

                    loginRepository.save(login.get());
                    return true;
                }
            }
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
         }
        return false;
    }

    private boolean setPassword(String username, String password) {
        ArrayList<String> info = new ArrayList<>();
        try {
             Optional<Login> login = loginRepository.findById(username);
             if(login.isPresent()) {
                 info = Authentication.setPassword(password);
                 String saltString = info.get(0);
                 String hashString = info.get(1);
                 login.get().setPasswordSalt(saltString);
                 login.get().setHash(hashString);
                 loginRepository.save(login.get());
                 return true;
             }
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }
}
