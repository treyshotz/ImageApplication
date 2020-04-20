package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Sort}.
 */
class SortTest {

    private static final String IMAGE_ALBUM_TITLE_A = "ATITLE";
    private static final String IMAGE_ALBUM_TITLE_B = "BTITLE";
    private static final String IMAGE_ALBUM_TITLE_C = "CTITLE";

    private AlbumRepository albumRepository;
    private UserRepository userRepository;

    private User currentUser;
    private Album firstAlbum;
    private Album secondAlbum;
    private Album thirdAlbum;

    /**
     * Setup test data - An {@link EntityManager} and an {@link Sort}.
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

        firstAlbum = new Album();
        firstAlbum.setTitle(IMAGE_ALBUM_TITLE_A);
        firstAlbum.setUser(currentUser);

        secondAlbum = new Album();
        secondAlbum.setTitle(IMAGE_ALBUM_TITLE_C);
        secondAlbum.setUser(currentUser);

        thirdAlbum = new Album();
        thirdAlbum.setTitle(IMAGE_ALBUM_TITLE_B);
        thirdAlbum.setUser(currentUser);

        albumRepository.save(firstAlbum);
        albumRepository.save(secondAlbum);
        albumRepository.save(thirdAlbum);

    }

    /**
     * Test that checks if ascending and descending creates unequal result
     */

    @Test
    void testDescendingNotEqualsAscending() {
        PageRequest pageRequestAsc = PageRequest.of(0, 3, Sort.by("createdAt").ascending());
        PageRequest pageRequestDsc = PageRequest.of(0,3,Sort.by("createdAt").descending());

        Page<Album> pageAsc = albumRepository.findAll(pageRequestAsc);
        Page<Album> pageDsc = albumRepository.findAll(pageRequestDsc);
        assertNotEquals(pageAsc, pageDsc);
    }

    /**
     * Test that checks that Sort.by creates different sorts based on the type you choose
     * Sorted by createdAt and title, and compares pageByDate and pageByTitle
     */

    @Test
    void testCreatedAtAndTitleNotEquals() {
        PageRequest sortByDate = PageRequest.of(0,3,Sort.by("createdAt"));
        PageRequest sortByTitle = PageRequest.of(0,3, Sort.by("title"));

        Page<Album> pageByDate = albumRepository.findAll(sortByDate);
        Page<Album> pageByTitle = albumRepository.findAll(sortByTitle);

        assertNotEquals(pageByDate, pageByTitle);
    }

    /**
     * Test sort by created at
     * Creates test dates, and sorts by those dates
     * Checks if the album dates are as expected based on which dates come first
     * Also compares the album found and the album excpected based on said dates
     */

    @Test
    void testSortByCreatedAt(){
        PageRequest sortByDate = PageRequest.of(0,3, Sort.by("createdAt"));
        Date firstDate = new Date(2010, Calendar.JANUARY, 8);
        Date secondDate = new Date(2011, Calendar.JANUARY, 9);
        Date thirdDate = new Date(2012, Calendar.JANUARY,10);
        firstAlbum.setCreatedAt(secondDate);
        secondAlbum.setCreatedAt(firstDate);
        thirdAlbum.setCreatedAt(thirdDate);
        albumRepository.update(firstAlbum);
        albumRepository.update(secondAlbum);
        albumRepository.update(thirdAlbum);
        Page<Album> pageByDate = albumRepository.findAll(sortByDate);

        assertEquals(firstDate, pageByDate.getContent().get(0).getCreatedAt());
        assertEquals(secondAlbum, pageByDate.getContent().get(0));

        assertEquals(secondDate, pageByDate.getContent().get(1).getCreatedAt());
        assertEquals(firstAlbum, pageByDate.getContent().get(1));

        assertEquals(thirdDate, pageByDate.getContent().get(2).getCreatedAt());
        assertEquals(thirdAlbum, pageByDate.getContent().get(2));
    }

    /**
     * Test sort by title
     * Uses test titles, and sorts by those titles
     * Checks if the albums are in correct order by the titles
     * A should come first, then B, then C
     */

    @Test
    void testSortByTitle(){
        PageRequest sortByTitle = PageRequest.of(0,3, Sort.by("title"));

        Page<Album> pageByTitle = albumRepository.findAll(sortByTitle);

        assertEquals(IMAGE_ALBUM_TITLE_A, pageByTitle.getContent().get(0).getTitle());
        assertEquals(IMAGE_ALBUM_TITLE_B, pageByTitle.getContent().get(1).getTitle());
        assertEquals(IMAGE_ALBUM_TITLE_C, pageByTitle.getContent().get(2).getTitle());
    }

    /**
     * Test that descending creates the opposite result from ascending
     * Sorts by title, and by using descending, the sort should return an opposite alphabetical order
     * C should come first, then B, then C
     */

    @Test
    void testSortDescending(){
        PageRequest sortByTitleDescending = PageRequest.of(0,3, Sort.by("title").descending());

        Page<Album> pageDescending = albumRepository.findAll(sortByTitleDescending);

        assertEquals(IMAGE_ALBUM_TITLE_C, pageDescending.getContent().get(0).getTitle());
        assertEquals(IMAGE_ALBUM_TITLE_B, pageDescending.getContent().get(1).getTitle());
        assertEquals(IMAGE_ALBUM_TITLE_A, pageDescending.getContent().get(2).getTitle());
    }



}