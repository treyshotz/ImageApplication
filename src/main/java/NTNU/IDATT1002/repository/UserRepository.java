package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.User;

import javax.persistence.EntityManager;

/**
 * User Repository.
 * Implementation of {@link  GenericRepository} which supports regular Create, Read, Update and Delete operations.
 *
 * @version 1.0 22.03.20
 */
public class UserRepository extends GenericRepository<User, String> {

    /**
     * Constructor to inject {@link EntityManager} dependency and sets the class type to {@link User}
     *
     * @param entityManager the entity manager to utilize
     */
    public UserRepository(EntityManager entityManager) {
        super(entityManager);
        setClassType(User.class);
    }
}
