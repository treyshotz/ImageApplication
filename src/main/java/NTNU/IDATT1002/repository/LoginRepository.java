package NTNU.IDATT1002.repository;


import NTNU.IDATT1002.models.Login;

import javax.persistence.EntityManager;

/**
 * Login Repository
 *
 * Implements {@link Repository} whick supports CRUD operations.
 *
 * @author madslun
 * @version 1.0 22.03.20
 * @see NTNU.IDATT1002.repository.Repository
 */

public class LoginRepository extends AbstractRepository<Login, String> {

    /**
     * {@inheritDoc}
     * Set the class type to {@link Login}
     */
    public LoginRepository(EntityManager entityManager) {
        super(entityManager);
        setEntityClass(Login.class);
    }
}