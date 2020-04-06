package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.GeoLocation;

import javax.persistence.EntityManager;

public class GeoLocatioRepository extends AbstractRepository<GeoLocation, Long> {

    /**
     * Constructor to inject {@link EntityManager} dependency.
     *
     * @param entityManager the entity manager to utilize
     */
    public GeoLocatioRepository(EntityManager entityManager) {
        super(entityManager);
        setEntityClass(GeoLocation.class);
    }
}
