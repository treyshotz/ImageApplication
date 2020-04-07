package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.User;

import javax.persistence.EntityManager;

/**
 * User Repository.
 * Implementation of {@link  AbstractRepository} which supports regular Create, Read, Update and Delete operations.
 *
 * @author madslun
 * @version 1.0 22.03.20
 */
public class UserRepository extends AbstractRepository<User, String> {

    /**
     * Constructor to inject {@link EntityManager} dependency and sets the class type to {@link User}
     *
     * @param entityManager the entity manager to utilize
     */
    public UserRepository(EntityManager entityManager) {
        super(entityManager);
        setEntityClass(User.class);
    }
}
