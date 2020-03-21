package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.Image;
  import javax.persistence.EntityManager;


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

  /**
   * Constructor to inject {@link EntityManager} dependency.
   *
   * @param entityManager the entity manager to utilize
   */
  public ImageRepository(EntityManager entityManager) {
    super(entityManager);
    setClassType(Image.class);
  }
}




