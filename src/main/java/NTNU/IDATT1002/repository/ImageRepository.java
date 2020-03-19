package NTNU.IDATT1002.repository;

import java.awt.Image;
import java.util.Optional;
import javax.persistence.EntityManager;
import java.util.List;


/**
 * Image Repository.
 * <p>
 * Implements {@link  Repository} which supports regular Create, Read, Update and Delete operations.
 *
 * @author Lars Østby
 * @version 1.0 19.03.20
 * @see NTNU.IDATT1002.repository.Repository
 */

public class ImageRepository implements Repository<Image, Long> {

  private EntityManager entityManager;

  /**
   * Constructor to inject {@link EntityManager} dependency.
   *
   * @param entityManager the entity manager to utilize.
   */

  public ImageRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * Saves a given image and returns the saved instance.
   *
   * @param image the image album to save
   * @return the saved image
   */

  public Optional<Image> save(Image image) {
    try {
      persist(image);
      return Optional.of(image);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return Optional.empty();
  }

  /**
   * Persists the given image
   *
   * @param image the image to persist
   */

  private void persist(Image image) {
    entityManager.getTransaction().begin();
    entityManager.persist(image);
    entityManager.getTransaction().commit();
  }

  /**
   * Retrieves all instances of the type image.
   *
   * @return all entities
   */
  public Optional<Image> update(Image image) {
    return Optional.empty();
  }

  /**
   * Retrieves all instances of type image.
   *
   * @return all saved image
   */

  public List<Image> findAll() {
    return entityManager.createQuery("from Image").getResultList();
  }


  /**
   * Retrieves an image with the given id.
   *
   * @param id the if of the image to find
   * @return the entity with the given id if found, else Optional.empty()
   */

  public Optional<Image> findById(Long id) {
    Image image = entityManager.find(Image.class, id);
    return image != null ? Optional.of(image) : Optional.empty();
  }

  /**
   * Deletes the given image
   *
   * @param image the image to delete
   */

  public void delete(Image image) {
    try {
      entityManager.getTransaction().begin();
      entityManager.remove(image);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Deletes an image with the given id.
   *
   * @param id the id of the image to delete
   */

  public void deleteById(Long id) {
    Optional<Image> image = findById(id);
    image.ifPresent(this::delete);

  }

  /**
   * Return the number of images
   *
   * @return the number of images
   */

  public long count() {
    return findAll().size();
  }

  /**
   * Return whether the given image exists.
   *
   * @param image image album to check existence for
   * @return true if the image album exist, else false
   */

  @Override
  public boolean exists(Image image) {
    return false;
  }
}
