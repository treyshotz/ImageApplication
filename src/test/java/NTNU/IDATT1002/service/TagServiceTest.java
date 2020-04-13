package NTNU.IDATT1002.service;

import NTNU.IDATT1002.models.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link TagService}
 *
 * @author madslun
 * @version 1.0 08.04.20
 */
class TagServiceTest {

  private TagService tagService;

  private final Long INITIAL_ID = 1L;
  private List<Tag> testTags;

  /**
   * Sets up necessary test data for testing
   */
  @BeforeEach
  void setUp() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplicationTest");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    tagService = new TagService(entityManager);
    testTags = new ArrayList<>();
  }

  /**
   * Test that tries to create a new tag
   * Since there exist no other tag we expect it create a new tag
   * A list with a single tag should be returned
   */
  @Test
  void testGetOrCreateTagsWithSingleTag() {
    Tag tag = new Tag("Test");
    testTags.add(tag);
    List<Tag> savedTags = tagService.getOrCreateTags(testTags);
    assertEquals(1, savedTags.size());
    assertEquals(INITIAL_ID, savedTags.get(0).getTagId());
  }

  /**
   * Test that get or create a tag with tag name
   * equal to null does not return tag.
   */
  @Test
  void testGetOrCreateTagsWithNullTagName() {
    Tag tagNullName = new Tag();
    List<Tag> savedTags = tagService.getOrCreateTags(Arrays.asList(tagNullName));

    assertEquals(0, savedTags.size());
  }

  /**
   * Test that get or create a tag with empty tag name does not return tag.
   */
  @Test
  void testGetOrCreateTagsWithBlankTagName() {
    Tag tagBlankName = new Tag("");
    List<Tag> savedTags = tagService.getOrCreateTags(Arrays.asList(tagBlankName));

    assertEquals(0, savedTags.size());
  }

  /**
   * Test that get or create a tag with whitespace tag name does not return tag.
   */
  @Test
  void testGetOrCreateTagsWithWhitespaceTagName() {
    Tag tagWhitespaceName = new Tag(" ");
    List<Tag> savedTags = tagService.getOrCreateTags(Arrays.asList(tagWhitespaceName));

    assertEquals(0, savedTags.size());
  }

  /**
   * Test that tries to create multiple tags with different name
   * Since every tag has a different name we expect to get a list of all the tags returned
   */
  @Test
  void testGetOrCreateTagsWithMultipleValidTags() {
    for(int i = 0; i < 10; i++) {
      Tag tag = new Tag(("Test" +i) );
      testTags.add(tag);
    }
    List<Tag> savedTags = tagService.getOrCreateTags(testTags);
    assertEquals(10, testTags.size());
  }

  /**
   * Test that tries to create a tag from a string
   * The string only contains one tagname so we expect it to only create one tag
   * We finally checks that the string and the tag name is the same
   */
  @Test
  void testGetTagsFromStringWithSingleTag() {
    String testString = "#Summer";
    List<Tag> returnedTags = tagService.getTagsFromString(testString);
    assertEquals("#Summer", returnedTags.get(0).getName());
  }

  /**
   * Test that tries to create multiple tags from a string
   * The string consist of three tag names, seperated by whitespace and comma
   * We expect to get three tags in return as the string contains three tagnames
   */
  @Test
  void testGetTagsFromStringWithMultipleTags() {
    String testString = "#Summer #Weather,#Java?Coding.Team14@Tester";
    List<Tag> returnedTags = tagService.getTagsFromString(testString);
    assertEquals(6, returnedTags.size());
  }

  /**
   * Test that creates a tag from a string
   * Proceeds to send the tag and get a string of the name of the tag
   * We finally compare that the string we started with is the same as we got in return
   */
  @Test
  void testGetTagsAsStringWithSingleTag() {
    String testString = "Test123";
    Tag tag = new Tag(testString);
    testTags.add(tag);
    String returnedString = tagService.getTagsAsString(testTags);
    assertEquals(testString, returnedString);
  }

  /**
   * Test that creates two tags from two seperate string
   * Proceeds to send both tags in and get a string in return
   * Checks that the string returned is the same as the two string we started with but with a whitespace between
   */
  @Test
  void testGetTagsAsStringWithMultipleTags() {
    String testString1 = "#Test";
    String testString2 = "#Summer";
    Tag tag1 = new Tag(testString1);
    Tag tag2 = new Tag(testString2);
    testTags.add(tag1);
    testTags.add(tag2);
    String returnedString = tagService.getTagsAsString(testTags);
    assertEquals(testString1+" " + testString2, returnedString);
  }

  /**
   * Test that creates a tag
   * Proceeds to send in the tag and get a tag in return
   * Finally checks that the id on the returned tag is equal to the excepted
   */
  @Test
  void testGetSingleTag() {
    Tag tag = new Tag("Test");
    Tag returnedTag = tagService.getSingleTag(tag);
    assertEquals(INITIAL_ID, returnedTag.getTagId());
  }
}