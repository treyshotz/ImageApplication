package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.Image;
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

public class ImageRepository extends GenericRepository<Image, Long> {

  private EntityManager entityManager;

  /**
   * Constructor to inject {@link EntityManager} dependency.
   *
   * @param entityManager the entity manager to utilize
   */
  public ImageRepository(EntityManager entityManager) {
    super(entityManager);
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
   * Return whether the given image exists.
   *
   * @param image image album to check existence for
   * @return true if the image album exist, else false
   */


  public boolean exists(Image image) {
    return findById(image.getImageID()).isPresent();
  }
}
