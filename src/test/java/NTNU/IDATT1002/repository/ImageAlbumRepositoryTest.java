package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.ImageAlbum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for {@link ImageAlbumRepository}.
 *
 * @author Eirik Steira
 * @version 1.0 17.03.20
 */
class ImageAlbumRepositoryTest {

    private static final String IMAGE_ALBUM_TITLE = "Test";
    private static final Long IMAGE_ALBUM_INITIAL_ID = 1L;

    private ImageAlbumRepository imageAlbumRepository;


    /**
     * Setup test data - An {@link EntityManager} and an {@link ImageAlbumRepository}.
     */
    @BeforeEach
    public void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplicationTest");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        imageAlbumRepository = new ImageAlbumRepository(entityManager);
    }

    /**
     * Test that saving an entity returns the saved instance.
     */
    @Test
    void testSaveReturnsInstance() {
        Optional<ImageAlbum> savedImageAlbum = imageAlbumRepository.save(new ImageAlbum());

        assertTrue(savedImageAlbum.isPresent());
    }

    /**
     * Test that saving an invalid entity fails and returns an empty optional.
     */
    @Test
    void testSaveInvalidEntityReturnsEmptyOptional() {
        Optional<ImageAlbum> savedImageAlbum = imageAlbumRepository.save(null);

        assertTrue(savedImageAlbum.isEmpty());
    }

    /**
     * Test that finding all entities returns all saved entities.
     */
    @Test
    void testFindAllReturnsAllSavedEntities() {
        imageAlbumRepository.save(new ImageAlbum());
        imageAlbumRepository.save(new ImageAlbum());

        List<?> foundImageAlbums = imageAlbumRepository.findAll();
        assertEquals(2, foundImageAlbums.size());
    }

    /**
     * Test that finding entity by id returns the optional with the given id.
     */
    @Test
    void testFindByIdReturnsOptionalWithEntityWithId() {
        imageAlbumRepository.save(new ImageAlbum());
        Optional<ImageAlbum> foundImageAlbum = imageAlbumRepository.findById(IMAGE_ALBUM_INITIAL_ID);

        assertTrue(foundImageAlbum.isPresent());
        assertEquals(IMAGE_ALBUM_INITIAL_ID, foundImageAlbum.get().getId());
    }

    /**
     * Test that finding all entities by title with a single match returns a list with only that instance.
     */
    @Test
    void testFindAllByTitleWithSingleMatchReturnsListWithSingleInstance() {
        ImageAlbum imageAlbum = new ImageAlbum();
        imageAlbum.setTitle(IMAGE_ALBUM_TITLE);

        Optional<ImageAlbum> savedImageAlbum = imageAlbumRepository.save(imageAlbum);
        List<?> foundImageAlbums = imageAlbumRepository.findAllByTitle(IMAGE_ALBUM_TITLE);

        assertEquals(1, foundImageAlbums.size());
        assertEquals(savedImageAlbum.get(), foundImageAlbums.get(0));
    }

    /**
     * Test that finding all entities by title with multiple matches returns a list with all matching instances.
     */
    @Test
    void testFindAllByTitleWithMultipleMatchesReturnsListWithInstances() {
        ImageAlbum imageAlbum = new ImageAlbum();
        imageAlbum.setTitle(IMAGE_ALBUM_TITLE);

        imageAlbumRepository.save(imageAlbum);

        imageAlbum = new ImageAlbum();
        imageAlbum.setTitle(IMAGE_ALBUM_TITLE + "Test");

        imageAlbumRepository.save(imageAlbum);
        List<?> foundImageAlbums = imageAlbumRepository.findAllByTitle(IMAGE_ALBUM_TITLE);

        assertEquals(2, foundImageAlbums.size());
    }

    /**
     * Test that deleting an entity by id removes said entity.
     */
    @Test
    void testDeleteByIdRemovesEntity() {
        imageAlbumRepository.save(new ImageAlbum());
        Optional<ImageAlbum> foundImageAlbum = imageAlbumRepository.findById(IMAGE_ALBUM_INITIAL_ID);

        foundImageAlbum.ifPresent(imageAlbum -> imageAlbumRepository.deleteById(IMAGE_ALBUM_INITIAL_ID));
        Optional<ImageAlbum> deletedImageAlbum = imageAlbumRepository.findById(IMAGE_ALBUM_INITIAL_ID);

        assertTrue(deletedImageAlbum.isEmpty());
    }

    /**
     * Test that deleting an entity removes said entity.
     */
    @Test
    void testDeleteRemovesEntity() {
        imageAlbumRepository.save(new ImageAlbum());
        Optional<ImageAlbum> foundImageAlbum = imageAlbumRepository.findById(IMAGE_ALBUM_INITIAL_ID);

        foundImageAlbum.ifPresent(imageAlbumRepository::delete);
        Optional<ImageAlbum> deletedImageAlbum = imageAlbumRepository.findById(IMAGE_ALBUM_INITIAL_ID);

        assertTrue(deletedImageAlbum.isEmpty());
    }

    /**
     * Test that getting count returns the correct amount of saved entities.
     */
    @Test
    void testCountReturnsAmountOfSavedEntities() {
        imageAlbumRepository.save(new ImageAlbum());
        imageAlbumRepository.save(new ImageAlbum());

        long imageAlbumCount = imageAlbumRepository.count();

        assertEquals(2, imageAlbumCount);
    }

}