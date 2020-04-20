package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.GeoLocation;

import javax.persistence.EntityManager;

/**
 * GeoLocation Repository.
 * <p>
 * Implements {@link  Repository} which supports regular Create, Read, Update and Delete operations.
 *
 * @author madslun
 * @version 1.0 96.04.20
 * @see NTNU.IDATT1002.repository.Repository
 */
public class GeoLocatioRepository extends AbstractRepository<GeoLocation, Long> {

    /**
     * {@inheritDoc}
     * Set the class type to {@link GeoLocation}
     */
    public GeoLocatioRepository(EntityManager entityManager) {
        super(entityManager);
        setEntityClass(GeoLocation.class);
    }
}
