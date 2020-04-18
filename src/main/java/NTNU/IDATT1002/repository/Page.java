package NTNU.IDATT1002.repository;

import java.util.List;

/**
 * Provides operations for navigating between pages. A page contains a list of data which type and size
 * are determined by the previous {@link PageRequest} before this page was created.
 *
 * The page does not care what type of data it is holding.
 *
 * @param <T> The type of data, preferably an entity.
 */
public class Page<T> {

    private final long total;

    private PageRequest pageRequest;

    private List<T> content;

    /**
     * Create a {@link Page} holding given content.
     *
     * @param content the list of content
     * @param pageRequest the {@link PageRequest} involved in creating this {@link Page}
     * @param total the current total size of data available. The last page number is calculated with this number.
     * @throws IllegalArgumentException if total is negative
     */
    public Page(List<T> content, PageRequest pageRequest, long total) {
        if (total < 0)
            throw new IllegalArgumentException("Total must be a positive long. Was: " + total);

        this.content = content;
        this.pageRequest = pageRequest;
        this.total = total;
    }

    /**
     * Return the next {@link PageRequest}.
     *
     * If the current {@link PageRequest} is the last page, this {@link PageRequest} is returned.
     */
    public PageRequest nextPageRequest() {
        int nextPageNumber = pageRequest.next().getPageNumber();

        if (nextPageNumber > getLastPageNumber())
            return pageRequest;

        return pageRequest.next();
    }

    /**
     * Return the previous {@link PageRequest}.
     *
     * If the current {@link PageRequest} is the first page, this {@link PageRequest} is returned.
     */
    public PageRequest previousPageRequest() {
        int previousPageNumber = pageRequest.previous().getPageNumber();

        if (previousPageNumber < 0)
            return pageRequest;

        return pageRequest.previous();
    }

    /**
     * Return the current total size of data available.
     */
    public long getTotal() {
        return total;
    }

    /**
     * Calculate and return the last page number.
     *
     * This number is rounded up to include the results left over in the last {@link PageRequest}
     * and 1 is subtracted because counting starts at 0.
     */
    public int getLastPageNumber() {
        return (int) (Math.ceil( (double) total / pageRequest.getPageSize() ) - 1);
    }

    /**
     * Return the {@link PageRequest} for this {@link Page}.
     */
    public PageRequest getPageRequest() {
        return pageRequest;
    }

    /**
     * Return the content for this {@link Page}.
     */
    public List<T> getContent() {
        return content;
    }
}
