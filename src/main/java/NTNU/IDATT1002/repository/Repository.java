package NTNU.IDATT1002.repository;

import java.util.List;
import java.util.Optional;


/**
 * Entity Repository Interface. Supports regular Create, Read, Update and Delete operations.
 *
 * @param <T> type of entity
 * @param <ID> type of entity id
 * @author Eirik Steira
 * @version 1.1 19.03.20
 */
public interface Repository<T, ID> {

    /**
     * Saves a given entity and returns the saved instance.
     *
     * @param entity not null
     * @return the saved entity
     */
    Optional<T> save(T entity);

    /**
     * Updates a given entity and returns the updated instance.
     *
     * @param entity not null
     * @return the updated entity
     */
    Optional<T> update(T entity);

    /**
     * Retrieves all instances of the type.
     *
     * @return all entities
     */
    List<?> findAll();

    /**
     * Retrieves an entity with the given id.
     *
     * @param id not null
     * @return the entity with the given id if found, else Optional.empty()
     */
    Optional<T> findById(ID id);

    /**
     * Deletes the given entity.
     *
     * @param entity not null
     */
    void delete(T entity);

    /**
     * Deletes an entity with the given id.
     *
     * @param id not null
     */
    void deleteById(ID id);

}
