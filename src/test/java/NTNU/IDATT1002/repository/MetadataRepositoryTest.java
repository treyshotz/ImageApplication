package NTNU.IDATT1002.repository;

import static org.junit.jupiter.api.Assertions.*;

import NTNU.IDATT1002.models.Metadata;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test for {@link MetadataRepository}
 *
 * @author madslun
 * @version 1.0 13.04.20
 */
class MetadataRepositoryTest {

  private static final Long INITIAL_ID = 1L;
  private MetadataRepository metadataRepository;

  /**
   * Sets up necessary test data for testing
   */
  @BeforeEach
  void setUp() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplicationTest");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    metadataRepository = new MetadataRepository(entityManager);
  }

  /**
   * Test that tries to save metadata and makes sure the image was saved
   */
  @Test
  void testSaveReturnsInstance() {
    Optional<Metadata> savedMetadata = metadataRepository.save(new Metadata());
    assertTrue(savedMetadata.isPresent());
    assertEquals(INITIAL_ID, savedMetadata.get().getMetadataId());
  }

  /**
   * Test that tries to save a metadata with invalid entity and makes sure the Image was not saved
   */
  @Test
  void testSaveInvalidEntityReturnsEmptyOptional() {
    Optional<Metadata> savedMetadata = metadataRepository.save(null);
    assertTrue(savedMetadata.isEmpty());
  }

  /**
   * Test that tries to save two metadata and make sure both are returned
   */
  @Test
  void testFindAllReturnsAllSavedEntities() {
    metadataRepository.save(new Metadata());
    metadataRepository.save(new Metadata());

    List<Metadata> foundMetadata = metadataRepository.findAll();
    assertEquals(2, foundMetadata.size());
  }

  /**
   * Test that save a metadata and finds the metadata by id
   */
  @Test
  void testFindByIdReturnsOptionalWithEntityWithId() {
    metadataRepository.save(new Metadata());
    Optional<Metadata> foundMetadata = metadataRepository.findById(INITIAL_ID);
    assertTrue(foundMetadata.isPresent());
    assertEquals(INITIAL_ID, foundMetadata.get().getMetadataId());
  }

  /**
   * Test that deletes metadata by id and makes sure metadata was deleted
   */
  @Test
  void testDeleteByIdRemovesEntity() {
    metadataRepository.save(new Metadata());
    Optional<Metadata> foundMetadata = metadataRepository.findById(INITIAL_ID);

    foundMetadata.ifPresent(metadata -> metadataRepository.deleteById(INITIAL_ID));
    Optional<Metadata> deletedMetadata = metadataRepository.findById(INITIAL_ID);

    assertTrue(deletedMetadata.isEmpty());
  }

  /**
   * Test that deletes metadata object and makes sure the metadata was deleted
   */
  @Test
  void testDeleteRemovesEntitiy() {
    metadataRepository.save(new Metadata());
    Optional<Metadata> foundMetadata = metadataRepository.findById(INITIAL_ID);

    foundMetadata.ifPresent(metadataRepository::delete);
    Optional<Metadata> deletedMetada = metadataRepository.findById(INITIAL_ID);

    assertTrue(deletedMetada.isEmpty());
  }
}