package NTNU.IDATT1002.repository;


import NTNU.IDATT1002.ApplicationState;
import NTNU.IDATT1002.models.ImageAlbum;
import NTNU.IDATT1002.models.Login;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.utils.Authentication;

import javax.persistence.EntityManager;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Login Repository
 *
 * Implements {@link Repository} whick supports CRUD operations.
 *
 * @author madslun
 * @version 1.0 22.03.20
 * @see NTNU.IDATT1002.repository.Repository
 */

public class LoginRepository extends GenericRepository<Login, String>{

    private EntityManager entityManager;


    /**
     * Constructor to inject {@link EntityManager} dependency and sets the class type to {@link Login}
     *
     * @param entityManager the entity manager to utilize
     */
    public LoginRepository(EntityManager entityManager) {
        super(entityManager);
        setClassType(Login.class);
    }

    /**
     * Logs a user into the sytem
     *
     * @param username of user that should login
     * @param password of the user that should login
     * @return boolean of the operation
     */
    public boolean logIn(String username, String password) {
        try {
            Optional<Login> login = findById(username);
            if(login.isPresent()) {
                String salt = login.get().getPasswordSalt();
                String expectedHash = login.get().getHash();
                return Authentication.isCorrectPassword(salt, password, expectedHash);
            }
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks the old password on the user and changes to the new password if the old is correct
     *
     * @param username of the user that want to change password
     * @param oldPassword that will be checked to the database
     * @param newPassword that will be changed to
     * @return boolean of the operation
     */

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        ArrayList<String> info = new ArrayList<>();
        try {
            Optional<Login> login = findById(username);
            if(login.isPresent()) {
                String salt = login.get().getPasswordSalt();
                String expectedHash = login.get().getHash();
                if(Authentication.isCorrectPassword(salt, oldPassword, expectedHash)) {
                    info = Authentication.setPassword(newPassword);
                    String saltString = info.get(0);
                    String hashString = info.get(1);
                    login.get().setPasswordSalt(saltString);
                    login.get().setHash(hashString);

                    save(login.get());
                    return true;
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method for setting the password on the user for the first time
     *
     * @param username of the user that will have the password set
     * @param password password that will be set
     * @return
     */
    public boolean setPassword(String username, String password) {
        ArrayList<String> info = new ArrayList<>();
        try {
            Optional<Login> login = findById(username);
            if(login.isPresent()) {
                info = Authentication.setPassword(password);
                String saltString = info.get(0);
                String hastString = info.get(1);
                login.get().setPasswordSalt(saltString);
                login.get().setHash(hastString);

                save(login.get());
                return true;
            }
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }
}