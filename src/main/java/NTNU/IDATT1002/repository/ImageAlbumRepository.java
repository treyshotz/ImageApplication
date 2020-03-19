package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.ImageAlbum;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * Image Album Repository.
 *
 * Implements {@link  Repository} which supports regular Create, Read, Update and Delete operations.
 * @author Eirik Steira
 * @version 1.0 19.03.20
 * @see NTNU.IDATT1002.repository.Repository
 */
public class ImageAlbumRepository implements Repository<ImageAlbum, Long> {

    /**
     * Mapping to @NamedQuery 'find all image albums by title' defined in {@link  ImageAlbum}
     */
    public static final String IMAGE_ALBUM_FIND_BY_TITLE = "ImageAlbum.findAllByTitle";

    private EntityManager entityManager;

    /**
     * Constructor to inject {@link EntityManager} dependency.
     *
     * @param entityManager the entity manager to utilize.
     */
    public ImageAlbumRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Saves a given image album and returns the saved instance.
     *
     * @param imageAlbum the image album to save
     * @return the saved image album
     */
    public Optional<ImageAlbum> save(ImageAlbum imageAlbum) {
        try {
            persist(imageAlbum);
            return Optional.of(imageAlbum);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Persists the given image album.
     *
     * @param imageAlbum  the image album to persist
     */
    private void persist(ImageAlbum imageAlbum) {
        entityManager.getTransaction().begin();
        entityManager.persist(imageAlbum);
        entityManager.getTransaction().commit();
    }

    /**
     * Retrieves all instances of type image album.
     *
     * @return all saved image albums
     */
    public List<?> findAll() {
        return entityManager.createQuery("from ImageAlbum ").getResultList();
    }


    /**
     * Retrieves an image album with the given id.
     *
     * @param id  the if of the image album to find
     * @return the entity with the given id if found, else Optional.empty()
     */
    public Optional<ImageAlbum> findById(Long id) {
        ImageAlbum imageAlbum = entityManager.find(ImageAlbum.class, id);
        return imageAlbum != null ? Optional.of(imageAlbum) : Optional.empty();
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

    /**
     * Deletes an image album with the given id.
     *
     * @param id the id of the image album to delete
     */
    public void deleteById(Long id) {
        Optional<ImageAlbum> imageAlbum = findById(id);
        imageAlbum.ifPresent(this::delete);
    }

    /**
     * Deletes the given image album.
     *
     * @param imageAlbum the image album to delete
     */
    public void delete(ImageAlbum imageAlbum) {
        try {
            remove(imageAlbum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the given image album.
     *
     * @param imageAlbum the image album to remove
     */
    private void remove(ImageAlbum imageAlbum) {
        entityManager.getTransaction().begin();
        entityManager.remove(imageAlbum);
        entityManager.getTransaction().commit();
    }

    /**
     * Return the number of image albums.
     *
     * @return the number of image albums
     */
    public long count() {
        return findAll().size();
    }

    /**
     * Return whether the given image album exists.
     *
     * @param imageAlbum image album to check existence for
     * @return true if the image album exist, else false
     */
    public boolean exists(ImageAlbum imageAlbum) {
        return findById(imageAlbum.getId())
                .isPresent();
    }
}
