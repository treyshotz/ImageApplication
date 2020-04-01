package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Tests for {@link AlbumRepository}.
 *
 * @author Eirik Steira
 * @version 1.0 17.03.20
 */
class AlbumRepositoryTest {

    private static final String IMAGE_ALBUM_TITLE = "Test";
    private static final Long IMAGE_ALBUM_INITIAL_ID = 1L;

    private AlbumRepository albumRepository;

    private UserRepository userRepository;

    private User currentUser;

    /**
     * Setup test data - An {@link EntityManager} and an {@link AlbumRepository}.
     */
    @BeforeEach
    public void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplicationTest");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        albumRepository = new AlbumRepository(entityManager);
        userRepository = new UserRepository(entityManager);

        currentUser = new User();
        currentUser.setUsername("testUser");
        userRepository.save(currentUser);
    }

    /**
     * Test that saving an entity returns the saved instance.
     */
    @Test
    void testSaveReturnsInstance() {
        Optional<Album> savedAlbum = albumRepository.save(new Album());

        assertTrue(savedAlbum.isPresent());
    }

    /**
     * Test that saving an invalid entity fails and returns an empty optional.
     */
    @Test
    void testSaveInvalidEntityReturnsEmptyOptional() {
        Optional<Album> savedAlbum = albumRepository.save(null);

        assertTrue(savedAlbum.isEmpty());
    }

    /**
     * Test that finding all entities returns all saved entities.
     */
    @Test
    void testFindAllReturnsAllSavedEntities() {
        albumRepository.save(new Album());
        albumRepository.save(new Album());

        List<?> foundAlbums = albumRepository.findAll();
        assertEquals(2, foundAlbums.size());
    }

    /**
     * Test that finding entity by id returns the optional with the given id.
     */
    @Test
    void testFindByIdReturnsOptionalWithEntityWithId() {
        albumRepository.save(new Album());
        Optional<Album> foundAlbum = albumRepository.findById(IMAGE_ALBUM_INITIAL_ID);

        assertTrue(foundAlbum.isPresent());
        assertEquals(IMAGE_ALBUM_INITIAL_ID, foundAlbum.get().getId());
    }


    /**
     * Test that deleting an entity by id removes said entity.
     */
    @Test
    void testDeleteByIdRemovesEntity() {
        albumRepository.save(new Album());
        Optional<Album> foundAlbum = albumRepository.findById(IMAGE_ALBUM_INITIAL_ID);

        foundAlbum.ifPresent(album -> albumRepository.deleteById(IMAGE_ALBUM_INITIAL_ID));
        Optional<Album> deletedAlbum = albumRepository.findById(IMAGE_ALBUM_INITIAL_ID);

        assertTrue(deletedAlbum.isEmpty());
    }

    /**
     * Test that deleting an entity removes said entity.
     */
    @Test
    void testDeleteRemovesEntity() {
        albumRepository.save(new Album());
        Optional<Album> foundAlbum = albumRepository.findById(IMAGE_ALBUM_INITIAL_ID);

        foundAlbum.ifPresent(albumRepository::delete);
        Optional<Album> deletedAlbum = albumRepository.findById(IMAGE_ALBUM_INITIAL_ID);

        assertTrue(deletedAlbum.isEmpty());
    }

    /**
     * Test that getting count returns the correct amount of saved entities.
     */
    @Test
    void testCountReturnsAmountOfSavedEntities() {
        albumRepository.save(new Album());
        albumRepository.save(new Album());

        long albumCount = albumRepository.count();

        assertEquals(2, albumCount);
    }

    /**
     * Test that finding albums by username returns all albums
     * created by the user with the given username.
     */
    @Test
    void testFindByUsernameReturnsAllAlbumsWithGivenUser() {
        Album albumWithUser = new Album();
        albumWithUser.setUser(currentUser);

        albumRepository.save(albumWithUser);
        Optional<Album> expectedAlbum = albumRepository.findById(IMAGE_ALBUM_INITIAL_ID);
        
        List<Album> foundAlbums = albumRepository.
                findAllByUsername(currentUser.getUsername());

        assertEquals(1, foundAlbums.size());
        assertTrue(foundAlbums.contains(expectedAlbum.get()));
    }

}