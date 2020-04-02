package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.Album;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Album Repository.
 *
 * Implementation of {@link  GenericRepository} which supports regular Create, Read, Update and Delete operations.
 * @author Eirik Steira
 * @version 1.0 19.03.20
 * @see NTNU.IDATT1002.repository.GenericRepository
 */
public class AlbumRepository extends GenericRepository<Album, Long> {

    /**
     * Mapping to @NamedQuery 'find all albums by users username' defined in {@link  Album}
     */
    public static final String ALBUM_FIND_BY_USERNAME = "Album.findAllByUsername";

    /**
     * Constructor to inject {@link EntityManager} dependency and sets the class type to {@link Album}
     *
     * @param entityManager the entity manager to utilize
     */
    public AlbumRepository(EntityManager entityManager) {
        super(entityManager);
        setClassType(Album.class);
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

}
