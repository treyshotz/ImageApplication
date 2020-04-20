package NTNU.IDATT1002.service;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Login;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.repository.LoginRepository;
import NTNU.IDATT1002.repository.UserRepository;
import NTNU.IDATT1002.utils.Authentication;

import NTNU.IDATT1002.utils.MetaDataExtractor;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Works together with loginrepository and userrepository
 * Combines authentication and connections to database through repositories
 *
 * @author madslun
 * @version 1.0 22.03.20
 */

public class UserService {

    private LoginRepository loginRepository;
    private UserRepository userRepository;
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Inject entity manager instance to the repositories
     */
    public UserService(EntityManager entityManager) {
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
        User user = new User(username, email, firstName, lastName, callingCode, phoneNumber, birthDate);
        Login login = new Login(user);
        setPassword(login, password);
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
                if(Authentication.isCorrectPassword(salt, password, hash)) {
                    ApplicationState.setCurrentUser(login.get().getUser());
                    return true;
                }
            }
        }
        catch (IllegalArgumentException e) {
            logger.error("[x] Something went wrong trying to log in", e);
        }
        return false;
    }

    public static void logOut() {
        ApplicationState.setCurrentUser(null);
        App.ex.setChosenAlbumId(null);
        App.ex.setChosenImg(null);
        App.ex.setUploadedFiles(null);
        App.ex.setHostServices(null);
    }

    /**
     * Searches for a user by username, checks if the old password matches, sets new password if old is right
     *
     * @param username that will be searched for
     * @param oldPassword that will be compared to database
     * @param newPassword that will be set
     * @return boolean is the action was a success
     */
    boolean changePassword(String username, String oldPassword, String newPassword) {
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
            logger.error("[x] Something went wrong when trying to change password", e);
         }
        return false;
    }

    /**
     * Sets password on a user when initially creating a user
     * @param login object that password will be set to
     * @param password that will be hashed and salted
     * @return boolean whether to action was a success or not
     */
    private boolean setPassword(Login login, String password) {
        ArrayList<String> info = new ArrayList<>();
        try {
             info = Authentication.setPassword(password);
             String saltString = info.get(0);
             String hashString = info.get(1);
             login.setPasswordSalt(saltString);
             login.setHash(hashString);
             if(loginRepository.save(login).isPresent()) {
                 return true;
             }
        }
        catch (IllegalArgumentException e) {
            logger.error("[x] Something went wrong trying to set pasword on a user for the first time");
        }
        return false;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
