package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.Histogram;

import javax.persistence.EntityManager;

public class HistorgramRepository extends GenericRepository<Histogram, Long> {

    /**
     * Constructor to inject {@link EntityManager} dependency.
     *
     * @param entityManager the entity manager to utilize
     */
    public HistorgramRepository(EntityManager entityManager) {
        super(entityManager);
        setClassType(Histogram.class);
    }
}
