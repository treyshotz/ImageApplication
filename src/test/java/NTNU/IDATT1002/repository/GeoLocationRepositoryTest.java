package NTNU.IDATT1002.repository;

import static org.junit.jupiter.api.Assertions.*;

import NTNU.IDATT1002.models.GeoLocation;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link GeoLocatioRepository}
 *
 * @author madslun
 * @version 1.0 07.04.20
 */
class GeoLocationRepositoryTest {

  private static final Long GEOLOCATION_INITIAL_ID = 1L;
  private GeoLocatioRepository geoLocatioRepository;


  /**
   * Sets up necessary test data for testing
   */
  @BeforeEach
  void setUp() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplicationTest");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    geoLocatioRepository = new GeoLocatioRepository(entityManager);
  }

  /**
   * Test that saves a geolocation then checks that it was saved
   */
  @Test
  void testSaveReturnsInstance() {
    Optional<GeoLocation> savedGeolocation = geoLocatioRepository.save(new GeoLocation());

    assertTrue(savedGeolocation.isPresent());
    assertEquals(GEOLOCATION_INITIAL_ID, savedGeolocation.get().getGeoLocationId());
  }

  /**
   * Test that tries to save a geolocation with invalid input
   */
  @Test
  void testSavedInvalidEntityReturnsEmptyOptional() {
    Optional<GeoLocation> savedGeolocation = geoLocatioRepository.save(null);
    assertTrue(savedGeolocation.isEmpty());
  }

  /**
   * Test that saves two geolocations then checks that both are saved
   */
  @Test
  void testFindAllReturnsAllSavedEntities() {
    geoLocatioRepository.save(new GeoLocation());
    geoLocatioRepository.save(new GeoLocation());

    List<GeoLocation> foundGeolocations = geoLocatioRepository.findAll();
    assertEquals(2, foundGeolocations.size());
  }

  /**
   * Test that saves a new geolocation then find the geolocation by id
   * When geolocation is created it uses the first number as id, we therefore search for the geolocation with this number
   * We then check that the geolocation we got is the correct one
   */
  @Test
  void testFindByIdReturnsOptionalWithCorrectId() {
    geoLocatioRepository.save(new GeoLocation());
    Optional<GeoLocation> foundGeolocation = geoLocatioRepository.findById(GEOLOCATION_INITIAL_ID);

    assertTrue(foundGeolocation.isPresent());
    assertEquals(GEOLOCATION_INITIAL_ID, foundGeolocation.get().getGeoLocationId());
  }

  /**
   * Test that deletes geolocation by id then checks that the geolocation was deleted
   */
  @Test
  void testDeleteByIdRemovesEntity() {
    geoLocatioRepository.save(new GeoLocation());
    Optional<GeoLocation> foundGeolocation = geoLocatioRepository.findById(GEOLOCATION_INITIAL_ID);

    foundGeolocation.ifPresent(geoLocation -> geoLocatioRepository.deleteById(GEOLOCATION_INITIAL_ID));
    Optional<GeoLocation> deletedGeolocation = geoLocatioRepository.findById(GEOLOCATION_INITIAL_ID);

    assertTrue(deletedGeolocation.isEmpty());
  }

  /**
   * Test that deletes a geolocation then checks that the geolocation was deleted
   * When geolocation is created it uses the first number as id, we therefor try to delete a geolocation with this number
   * We afterwards try to list all the geolocations and checks that there is no one left
   */
  @Test
  void testDeleteEntityRemovesEntity() {
    GeoLocation testGeolocation = new GeoLocation();
    geoLocatioRepository.save(testGeolocation);
    Optional<GeoLocation> foundGeolocation = geoLocatioRepository.findById(GEOLOCATION_INITIAL_ID);

    foundGeolocation.ifPresent(geoLocation -> geoLocatioRepository.delete(testGeolocation));
    List<GeoLocation> foudGeolocations = geoLocatioRepository.findAll();
    assertEquals(0, foudGeolocations.size());
  }

}