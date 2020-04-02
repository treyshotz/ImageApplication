package NTNU.IDATT1002.repository;


import NTNU.IDATT1002.ApplicationState;
import NTNU.IDATT1002.models.Album;
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
}