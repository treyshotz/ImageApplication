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
     * Mapping to @NamedQuery 'find all image albums by title' defined in {@link  ImageAlbum}
     */
    public static final String IMAGE_ALBUM_FIND_BY_TITLE = "ImageAlbum.findAllByTitle";

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
     * Retrieves all image albums with the given title.
     *
     * @param title the image album title
     * @return all image albums with the given title
     */
    public List<?> findAllByTitle(String title) {
        return entityManager.createNamedQuery(IMAGE_ALBUM_FIND_BY_TITLE)
                .setParameter("queried_title", "%" + title + "%")
                .getResultList();
    }

}
