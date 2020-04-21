package NTNU.IDATT1002.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.Optional;

/**
 * Represents a part of a sorted {@link PageRequest}.
 *
 * Composes an {@link Order} wrapped in an {@link Optional}
 * which can be used when building an ordered {@link javax.persistence.criteria.CriteriaQuery}.
 * If no sorting is specified or required, {@link Sort#empty()} should be used.
 * This returns a {@link Optional#empty()} when building queries.
 *
 * The default order is ascending.
 */
public class Sort {

    private String byField;

    private boolean ascending;

    /**
     * @see Sort#by(String byField)
     */
    private Sort(String byField) {

        this.byField = byField;
        this.ascending = true;
    }

    /**
     * Return a {@link Sort} by given field.
     */
    public static Sort by(String field) {
        return new Sort(field);
    }

    /**
     * Return a {@link Sort} in descending order.
     */
    public Sort descending() {
        this.ascending = false;
        return this;
    }

    /**
     * Return a {@link Sort} in ascending order.
     */
    public Sort ascending() {
        this.ascending = true;
        return this;
    }

    /**
     * Return a {@link Sort} which does not sort by any field.
     */
    public static Sort empty() {
        return new Sort(null);
    }

    /**
     * Compose and return this {@link Sort} as an {@link Order} wrapped in an {@link Optional}
     * which can be used when building queries with {@link javax.persistence.criteria.CriteriaQuery}.
     *
     * Return {@link Optional#empty()} if this {@link Sort} is empty,
     *
     * @param criteriaBuilder the {@link CriteriaBuilder} to build the {@link Order} from
     * @param from the {@link Root} specifying which table the query acts on.
     */
    public <T> Optional<Order> getOrder(CriteriaBuilder criteriaBuilder, Root<T> from) {
        if (this.isEmpty())
            return Optional.empty();

        Expression<String> expression = from.get(byField);

        if (ascending)
            return Optional.of(criteriaBuilder.asc(expression));

        return Optional.of(criteriaBuilder.desc(expression));
    }

    /**
     * Return whether this {@link Sort} is empty.
     */
    private boolean isEmpty() {
        return byField == null;
    }

}
