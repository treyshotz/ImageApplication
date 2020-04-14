package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Image;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

public class ImageRepository extends AbstractRepository<Image, Long> {

    /**
     * Mapping to @NamedQuery 'find all albums by username and tags' defined in {@link  Image}
     */
    public static final String IMAGE_FIND_BY_USERNAME = "Image.findAllByUsername";
    public static final String IMAGE_FIND_BY_TAG = "Image.findByTags";

    /**
     * Constructor to inject {@link EntityManager} dependency.
     *
     * @param entityManager the entity manager to utilize
     */
    public ImageRepository(EntityManager entityManager) {
      super(entityManager);
      setEntityClass(Image.class);
    }


    /**
     * Retrieves all albums of the user with the given username.
     *
     * @param username the username to query for
     * @return the list of the users albums.
     */
    public List<Image> findAllByUsername(String username) {
        return entityManager.createNamedQuery(IMAGE_FIND_BY_USERNAME, Image.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Image> findAllByTags(String tag){
        return entityManager.createNamedQuery(IMAGE_FIND_BY_TAG, Image.class)
                .setParameter("name",tag)
                .getResultList();
    }

}




