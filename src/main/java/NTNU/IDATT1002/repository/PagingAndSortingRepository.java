package NTNU.IDATT1002.repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


/**
 * Paging and Sorting Repository.
 *
 * Supports operations for requesting paginated and sorted results.
 *
 * @param <T> type of entity
 * @param <ID> type of entity id
 */
public abstract class PagingAndSortingRepository<T, ID> extends AbstractRepository<T, ID> {

    /**
     * {@inheritDoc}
     */
    protected PagingAndSortingRepository(EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * Find all entities specified by given {@link PageRequest}.
     * Queries for the total amount of results to support proper pagination.
     *
     * @param pageRequest the {@link PageRequest} specifying which page and how many entities to query for,
     *                    with an optional {@link Sort}
     * @return {@link Page} consisting of the results of this operation
     */
    public Page<T> findAll(PageRequest pageRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> selectQuery = getCriteriaQuery(criteriaBuilder, pageRequest.getSort());

        List<T> paginatedResults = getPaginatedResults(pageRequest, selectQuery);
        long total = getTotal(criteriaBuilder);

        return new Page<>(paginatedResults, pageRequest, total);
    }

    /**
     * Return a {@link CriteriaQuery} SELECT FROM query.
     *
     * If given {@link Sort}s order is not empty, include the
     * ordering in the {@link CriteriaQuery}, resulting in an ORDER BY clause.
     *
     * @param criteriaBuilder the {@link CriteriaBuilder} to build the query from
     */
    private CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder criteriaBuilder, Sort sort) {
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(super.entityClass);
        Root<T> from = criteriaQuery.from(super.entityClass);
        CriteriaQuery<T> selectQuery = criteriaQuery.select(from);

        sort.getOrder(criteriaBuilder, from)
                .ifPresent(selectQuery::orderBy);

        return selectQuery;
    }

    /**
     * Return the paginated results in a {@link List}
     * specified by given {@link PageRequest} and {@link CriteriaQuery}.
     *
     * Calculate the position of the first row to
     * fetch by multiplying the page number with the amount of
     * rows per page.
     *
     * @param pageRequest the {@link PageRequest} specifying which page and how many entities to query for
     * @param selectQuery the {@link CriteriaQuery} query to execute.
     */
    private List<T> getPaginatedResults(PageRequest pageRequest, CriteriaQuery<T> selectQuery) {
        int startPosition = pageRequest.getPageNumber() * pageRequest.getPageSize();
        return entityManager.createQuery(selectQuery)
                .setFirstResult(startPosition)
                .setMaxResults(pageRequest.getPageSize())
                .getResultList();
    }

    /**
     * Return the total amount of entities of current type.
     *
     * @param criteriaBuilder the {@link CriteriaBuilder} to build the query from
     */
    private long getTotal(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(
            criteriaBuilder.count(
                countQuery.from(
                        super.entityClass
                )
            )
        );

        return entityManager.createQuery(countQuery)
                .getSingleResult();
    }

}
