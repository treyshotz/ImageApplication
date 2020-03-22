package NTNU.IDATT1002.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


/**
 * Generic Entity Repository Abstract Class.
 * Implements regular Create, Read, Update and Delete operations defined in {@link Repository}.
 *
 * This class can be easily extended to support type specific operations through concrete implementations.
 * @param <T> type of entity
 * @param <ID> type of entity id
 * @author Eirik Steira
 * @version 1.0 19.03.20
 */
abstract class GenericRepository<T, ID> implements Repository<T, ID> {

    /**
     * The type of class which implementations of this class is to operate on.
     */
    private Class<T> classType;

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Constructor to inject {@link EntityManager} dependency.
     *
     * @param entityManager the entity manager to utilize
     */
    public GenericRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Set the type of class which this class is to operate on.
     *
     * @param classTypeToSet the type of class
     */
    public void setClassType(Class<T> classTypeToSet) {
        classType = classTypeToSet;
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
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Persists the given image album.
     *
     * @param entity  the image album to persist
     */
    private void persist(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    /**
     * Retrieves all instances of the class type.
     *
     * @return all entities
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("from " + classType.getName())
                .getResultList();
    }

    /**
     * Retrieves an entity with the given id.
     *
     * @param id not null
     * @return the entity with the given id if found, else Optional.empty()
     */
    public Optional<T> findById(ID id) {
        T entity = entityManager.find(classType, id);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the given image album.
     *
     * @param entity the image album to remove
     */
    private void remove(T entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    /**
     * Return the number of entities.
     *
     * @return the number of entities.
     */
    public long count() {
        return findAll().size();
    }

}
