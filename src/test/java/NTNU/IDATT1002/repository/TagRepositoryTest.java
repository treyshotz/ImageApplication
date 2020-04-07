package NTNU.IDATT1002.repository;

import static org.junit.jupiter.api.Assertions.*;

import NTNU.IDATT1002.models.Tag;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link TagRepository}
 *
 * @author madslun
 * @version 1.0 06.04.20
 */
class TagRepositoryTest {

  private TagRepository tagRepository;
  private static final Long TAG_INITIAL_ID = 1L;


  /**
   * Sets up testdata used through testing the repository
   */
  @BeforeEach
  public void setUp() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplicationTest");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    tagRepository = new TagRepository(entityManager);
  }

  /**
   * Test that tries to save a new tag
   */
  @Test
  void testSaveReturnsInstance() {
    Optional<Tag> optionalTag = tagRepository.save(new Tag());
    assertTrue(optionalTag.isPresent());
  }

  /**
   * Test that tries to save two tags and makes sure both are returned
   */
  @Test
  void testSaveReturnsAllSavedEntities() {
    tagRepository.save(new Tag());
    tagRepository.save(new Tag());

    List<?> foundTags = tagRepository.findAll();
    assertEquals(2, foundTags.size());
  }

  /**
   * Test that tries to save a tag with invalid entity and makes sure an empty optional is returned
   */
  @Test
  void testInvalidEntityReturnsEmptyOptional() {
    Optional<Tag> optionalTag = tagRepository.save(null);

    assertTrue(optionalTag.isEmpty());
  }

  /**
   * Test that saves a tag and finds the same tag by tag id
   */
  @Test
  void testFindByIdReturnsOptionalWithCorrectId() {
    tagRepository.save(new Tag());
    Optional<Tag> optionalTag = tagRepository.findById(TAG_INITIAL_ID);

    assertTrue(optionalTag.isPresent());
    assertEquals(TAG_INITIAL_ID, optionalTag.get().getTagId());
  }

  /**
   * Test that save a new tag, then proceeds to delete the tag, finally checks that the tag does not exist anymore
   */
  @Test
  void testDeleteById() {
    tagRepository.save(new Tag());
    Optional<Tag> optionalTag = tagRepository.findById(TAG_INITIAL_ID);

    optionalTag.ifPresent(Tag -> tagRepository.deleteById(TAG_INITIAL_ID));
    Optional<Tag> deletedTag = tagRepository.findById(TAG_INITIAL_ID);

    assertTrue(deletedTag.isEmpty());
  }

  /**
   * Test that tries to create a new tag that already has the same name, then makes sure the last tag was not saved
   */
  @Test
  void testFindOrCreateTagFindsCreatedTag() {
    String takenName = "Test";
    Tag firstTag = new Tag(takenName);
    tagRepository.save(firstTag);
    tagRepository.findOrCreate(new Tag(takenName));
    List<?> foundTags = tagRepository.findAll();
    assertEquals(1, foundTags.size());
  }

  /**
   * Test that tries to create a new tag that does not exist
   */
  @Test
  void testFindOrCreateTagCreatesNewTag() {
    String availableName = "Test123";
    Tag firstTag = new Tag("Test321");
    tagRepository.save(firstTag);
    tagRepository.findOrCreate(new Tag(availableName));
    List<?> foundTags = tagRepository.findAll();
    assertEquals(2, foundTags.size());
  }

  /**
   * Test that tries to create several tags with the same name
   */
  @Test
  void testFindOrCreateTagWhenSeveralExists() {
    String availableName = "Test123";
    Tag firstTag = new Tag(availableName);
    tagRepository.save(firstTag);
    tagRepository.findOrCreate(new Tag(availableName));
    tagRepository.findOrCreate(new Tag(availableName));
    tagRepository.findOrCreate(new Tag(availableName));
    List<?> foundTags = tagRepository.findAll();
    assertEquals(1, foundTags.size());
  }

  /**
   * Test that tries to create a tag whit an empty string and makes sure null is returned
   */
  @Test
  void testCreateOrFindTagWithEmptyStringReturnsNull() {
    String emptyName = "";
    Tag testTag = new Tag(emptyName);
    assertEquals(null, tagRepository.findOrCreate(testTag));
  }

  /**
   * Test that tries to create a tag with a whitespace as name and makes sure null is returned
   */
  @Test
  void testCreateOrFindTagWithWhitespaceReturnsNull() {
    String whiteSpaceName = " ";
    Tag testTag = new Tag(whiteSpaceName);
    assertEquals(null, tagRepository.findOrCreate(testTag));
  }
}