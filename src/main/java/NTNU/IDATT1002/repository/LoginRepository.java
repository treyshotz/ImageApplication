package NTNU.IDATT1002.repository;


import NTNU.IDATT1002.models.Login;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.utils.Authentication;

import javax.persistence.EntityManager;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class LoginRepository extends GenericRepository<Login, String>{

    private EntityManager entityManager;

    public LoginRepository(EntityManager entityManager) {
        super(entityManager);
        setClassType(Login.class);
    }

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
}