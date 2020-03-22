package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.Image;


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

    /**
     * Mapping to @NamedQuery 'find all image albums by users username' defined in {@link  Image}
     */
    public static final String IMAGE_FIND_BY_USERNAME = "ImageAlbum.findAllByUsername";

    /**
     * Constructor to inject {@link EntityManager} dependency.
     *
     * @param entityManager the entity manager to utilize
     */
    public ImageRepository(EntityManager entityManager) {
      super(entityManager);
      setClassType(Image.class);
    }

    /**
     * Retrieves all image albums of the user with the given username.
     *
     * @param username the username to query for
     * @return the list of the users image albums.
     */
    public List<Image> findAllByUsername(String username) {
        return entityManager.createNamedQuery(IMAGE_FIND_BY_USERNAME, Image.class)
                .setParameter("username", username)
                .getResultList();
    }
}




