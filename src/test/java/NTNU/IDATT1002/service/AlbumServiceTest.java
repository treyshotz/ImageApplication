package NTNU.IDATT1002.service;

import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.repository.AlbumRepository;
import NTNU.IDATT1002.repository.ImageRepository;
import NTNU.IDATT1002.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for {@link AlbumService}.
 */
class AlbumServiceTest {

    private final String ALBUM_TITLE = "album";
    private final Long INITIAL_ID = 1L;

    private AlbumService albumService;
    private AlbumRepository albumRepository;
    private UserRepository userRepository;
    private ImageRepository imageRepository;

    private User user;
    private Image image;

    /**
     * Setup test data consisting of an album with a single user and image.
     */
    @BeforeEach
    void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplicationTest");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        userRepository = new UserRepository(entityManager);
        imageRepository = new ImageRepository(entityManager);
        albumRepository = new AlbumRepository(entityManager);

        User userToSave = new User();
        userToSave.setUsername("test");
        user = userRepository.save(userToSave).get();

        image = imageRepository.save(new Image()).get();

        Album album = new Album();
        album.setTitle(ALBUM_TITLE);
        albumRepository.save(album);

        albumService = new AlbumService(entityManager);
    }

    /**
     * Test that getting an album by id returns said album.
     */
    @Test
    public void testThatGetAlbumByIdReturnsAlbum() {
        Optional<Album> foundAlbum = albumService.getAlbumById(INITIAL_ID);

        assertTrue(foundAlbum.isPresent());
        assertEquals(ALBUM_TITLE, foundAlbum.get().getTitle());
    }

    /**
     * Test that getting all albums returns all saved albums.
     */
    @Test
    public void testThatGetAllAlbumsReturnsAllAlbums() {
        albumRepository.save(new Album());
        List<Album> foundAlbums = albumService.getAllAlbums();

        assertEquals(2, foundAlbums.size());
    }

    /**
     * Test that create album with all fields creates and returns said album.
     */
    @Test
    public void testThatCreateAlbumCreatesAlbum() {
        String description = "test";
        List<Tag> tags = Arrays.asList(new Tag("test"));
        List<Image> images = Arrays.asList(image);

        Optional<Album> createdAlbum = albumService.createAlbum(ALBUM_TITLE,
                description,
                user,
                tags,
                images);

        assertTrue(createdAlbum.isPresent());

        Album album = createdAlbum.get();
        assertEquals(description, album.getDescription());
        assertEquals(user.getUsername(), album.getUser().getUsername());
        assertEquals(INITIAL_ID, album.getTags().get(0).getTagId());
        assertEquals(INITIAL_ID, album.getImages().get(0).getId());
    }


    /**
     * Test that creating an empty album creates and returns an album with no images.
     */
    @Test
    public void testThatCreateEmptyAlbumCreatesEmptyAlbum() {
        String description = "test";
        List<Tag> tags = Arrays.asList(new Tag("test"));

        Optional<Album> createdAlbum = albumService.createEmptyAlbum(ALBUM_TITLE,
                description,
                user,
                tags);

        assertTrue(createdAlbum.isPresent());

        Album album = createdAlbum.get();
        assertEquals(description, album.getDescription());
        assertEquals(user.getUsername(), album.getUser().getUsername());
        assertEquals(INITIAL_ID, album.getTags().get(0).getTagId());
        assertTrue(album.getImages().isEmpty());
    }

}