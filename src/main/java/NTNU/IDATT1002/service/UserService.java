package NTNU.IDATT1002.service;

import NTNU.IDATT1002.ApplicationState;
import NTNU.IDATT1002.models.Login;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.repository.LoginRepository;
import NTNU.IDATT1002.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        Login login = new Login(user, "", "");
        userRepository.save(user);
        loginRepository.save(login);
        loginRepository.setPassword(username, password);

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
        if(loginRepository.logIn(username, password)) {
            User user = loginRepository.findById(username).get().getUser();
            ApplicationState.setCurrentUser(user);
            return true;
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
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        return loginRepository.changePassword(username, oldPassword, newPassword);
    }
}
