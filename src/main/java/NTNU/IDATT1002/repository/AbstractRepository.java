package NTNU.IDATT1002.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


/**
 * Abstract Entity Repository Abstract Class.
 * Implements regular Create, Read, Update and Delete operations defined in {@link Repository}.
 *
 * This class can be easily extended to support type specific operations through concrete implementations.
 * @param <T> type of entity
 * @param <ID> type of entity id
 * @author Eirik Steira
 * @version 1.1 03.04.20
 */
abstract class AbstractRepository<T, ID> implements Repository<T, ID> {

    /**
     * The type of class which implementations of this class is to operate on.
     */
    private Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());;

    /**
     * Constructor to inject {@link EntityManager} dependency.
     *
     * @param entityManager the entity manager to utilize
     */
    public AbstractRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Set the type of class which this class is to operate on.
     *
     * @param classTypeToSet the type of class
     */
    public void setEntityClass(Class<T> classTypeToSet) {
        entityClass = classTypeToSet;
    }

    /**
     * Saves a given entity and returns the saved instance.
     *
     * @param entity not null
     * @return the saved entity
     */
    public Optional<T> save(T entity) {
        try {
            persist(entity);
            logger.info("[x] Saved entity {}", entity);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Updates a given entity and returns the updated instance.
     *
     * @param entity not null
     * @return the updates entity
     */
    public Optional<T> update(T entity) {
        try {
            merge(entity);
            logger.info("[x] Updated entity {}", entity);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Persists the given album.
     *
     * @param entity  the album to persist
     */
    private void persist(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    /**
     * Merge the given album.
     *
     * @param entity  the album to merge
     */
    private void merge(T entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    /**
     * Retrieves all instances of the class type.
     *
     * @return all entities
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("from " + entityClass.getName())
                .getResultList();
    }

    /**
     * Retrieves an entity with the given id.
     *
     * @param id not null
     * @return the entity with the given id if found, else Optional.empty()
     */
    public Optional<T> findById(ID id) {
        T entity = entityManager.find(entityClass, id);
        return entity != null ? Optional.of(entity) : Optional.empty();
    }

    /**
     * Deletes an entity with the given id.
     *
     * @param id not null
     */
    public void deleteById(ID id) {
        Optional<T> entity = findById(id);
        entity.ifPresent(this::delete);
    }

    /**
     * Deletes the given entity.
     *
     * @param entity not null
     */
    public void delete(T entity) {
        try {
            remove(entity);
            logger.info("[x] Deleted entity {}", entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the given album.
     *
     * @param entity the album to remove
     */
    private void remove(T entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

}
