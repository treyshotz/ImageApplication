package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.Config;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Image;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * Album Repository.
 *
 * Implementation of {@link  AbstractRepository} which supports regular Create, Read, Update and Delete operations.
 * @author Eirik Steira
 * @version 1.0 19.03.20
 * @see AbstractRepository
 */
public class AlbumRepository extends PagingAndSortingRepository<Album, Long> {

    /**
     * Mapping to @NamedQuery 'find all albums by username, tags, and title' defined in {@link  Album}
     */
    public static final String ALBUM_FIND_BY_USERNAME = "Album.findAllByUsername";
    public static final String ALBUM_FIND_BY_TAGS = "Album.findByTags";
    public static final String ALBUM_FIND_BY_TITLE = "Album.findByTitle";
    public static final String ALBUM_FIND_PREVIEW_IMAGE = "Album.findPreviewImage";

    /**
     * @inheritDoc
     * Set the class type to {@link Album}
     */
    public AlbumRepository(EntityManager entityManager) {
        super(entityManager);
        setEntityClass(Album.class);
    }

    /**
     * Retrieves all albums of the user with the given username.
     *
     * @param username the username to query for
     * @return the list of the users albums.
     */
    public List<Album> findAllByUsername(String username) {
        return entityManager.createNamedQuery(ALBUM_FIND_BY_USERNAME, Album.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Album> findAllByTags(String tag){
        return entityManager.createNamedQuery(ALBUM_FIND_BY_TAGS, Album.class)
                .setParameter("name",tag)
                .getResultList();
    }

    /**
     * Find all albums by given title.
     *
     * @param title the title to query for
     * @return a list of albums found
     */
    public List<Album> findAllByTitle(String title){
        return entityManager.createNamedQuery(ALBUM_FIND_BY_TITLE, Album.class)
                .setParameter("title", title)
                .getResultList();
    }

    /**
     * Find a single preview image from album with given id.
     *
     * Creates a new entity manager for each call
     * to allow concurrent fetching within the maximum
     * connection pool size.
     *
     * @param albumId the id of the album
     * @return Optional of image if found
     */
    public Optional<Image> findPreviewImage(Long albumId) {
        Image image = Config.createEntityManager()
                    .createNamedQuery(ALBUM_FIND_PREVIEW_IMAGE, Image.class)
                    .setParameter("albumId", albumId)
                    .setMaxResults(1)
                    .setFirstResult(0)
                    .getResultList().get(0);

        return Optional.ofNullable(image);
    }
}
