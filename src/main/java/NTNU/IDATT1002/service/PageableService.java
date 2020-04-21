package NTNU.IDATT1002.service;

import NTNU.IDATT1002.repository.Page;
import NTNU.IDATT1002.repository.PageRequest;

/**
 * Interface for services that are pageable, ie offer paginated content.
 *
 * @param <T> the type of entity provided
 */
public interface PageableService<T> {

    /**
     * Find all entities on page matching given {@link PageRequest}.
     *
     * @param pageRequest the requested page
     * @return the {@link Page} containing the results
     */
    Page<T> findAll(PageRequest pageRequest);

}
