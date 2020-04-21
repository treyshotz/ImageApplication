package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.Image;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * Image Repository.
 *
 * Implements {@link  Repository} which supports regular Create, Read, Update and Delete operations.
 *
 * @author Lars Østby
 * @version 1.0 19.03.20
 * @see NTNU.IDATT1002.repository.Repository
 */
public class ImageRepository extends PagingAndSortingRepository<Image, Long> {

    /**
     * Mapping to {@link javax.persistence.NamedQuery} defined in {@link Image}
     */
    public static final String IMAGE_FIND_BY_USERNAME = "Image.findAllByUsername";
    public static final String IMAGE_FIND_BY_TAG = "Image.findByTags";
    public static final String IMAGE_FIND_BY_QUERY_STRING = "Image.findByQueryString";

    /**
     * {@inheritDoc}
     * Set the class type to {@link Image}
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

    /**
     * Find all images based on a string. Combined result of title, tag and author search
     *
     * @param query
     * @return
     */
    public List<Image> findAllByQueryString(String query){
        return entityManager.createNamedQuery(IMAGE_FIND_BY_QUERY_STRING, Image.class)
                .setParameter("query", query)
                .getResultList();
    }

}




