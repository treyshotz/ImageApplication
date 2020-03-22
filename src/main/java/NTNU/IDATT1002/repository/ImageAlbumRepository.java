package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.ImageAlbum;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Image Album Repository.
 *
 * Implementation of {@link  GenericRepository} which supports regular Create, Read, Update and Delete operations.
 * @author Eirik Steira
 * @version 1.0 19.03.20
 * @see NTNU.IDATT1002.repository.GenericRepository
 */
public class ImageAlbumRepository extends GenericRepository<ImageAlbum, Long> {

    /**
     * Mapping to @NamedQuery 'find all image albums by users username' defined in {@link  ImageAlbum}
     */
    public static final String IMAGE_ALBUM_FIND_BY_USERNAME = "ImageAlbum.findAllByUsername";

    /**
     * Constructor to inject {@link EntityManager} dependency and sets the class type to {@link ImageAlbum}
     *
     * @param entityManager the entity manager to utilize
     */
    public ImageAlbumRepository(EntityManager entityManager) {
        super(entityManager);
        setClassType(ImageAlbum.class);
    }

    /**
     * Retrieves all image albums of the user with the given username.
     *
     * @param username the username to query for
     * @return the list of the users image albums.
     */
    public List<ImageAlbum> findAllByUsername(String username) {
        return entityManager.createNamedQuery(IMAGE_ALBUM_FIND_BY_USERNAME, ImageAlbum.class)
                .setParameter("username", username)
                .getResultList();
    }

}
