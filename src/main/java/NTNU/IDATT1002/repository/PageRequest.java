package NTNU.IDATT1002.repository;

/**
 * Represents requests for requesting a {@link Page} from a paginated query.
 *
 * A page request contains information about the requested page number,
 * the page size, ie number of elements per page
 * and a {@link Sort} representing an ordering.
 */
public class PageRequest {

    private int pageSize;

    private int pageNumber;

    private Sort sort;

    /**
     * @see PageRequest#of(int pageNumber, int pageSize)
     */
    protected PageRequest(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = Sort.empty();
    }

    /**
     * @see PageRequest#of(int pageNumber, int pageSize, Sort sort)
     */
    protected PageRequest(int pageNumber, int pageSize, Sort sort) {
        this(pageNumber, pageSize);
        this.sort = sort;
    }

    /**
     * Create a {@link PageRequest} defined by given page number and page size.
     *
     * Includes a {@link Sort#empty()}.
     *
     * @param pageNumber the page this request represents, defined by its number
     * @param pageSize the number of elements per page
     */
    public static PageRequest of(int pageNumber, int pageSize) {
        return new PageRequest(pageNumber, pageSize);
    }

    /**
     * Create a {@link PageRequest} defined by
     * given page number and page size, with {@link Sort}.
     *
     * @param pageNumber the page this request represents, defined by its number
     * @param pageSize the number of elements per page
     * @param sort the sorting of the elements requested by this request.
     */
    public static PageRequest of(int pageNumber, int pageSize, Sort sort) {
        return new PageRequest(pageNumber, pageSize, sort);
    }

    /**
     * Return the succeeding {@link PageRequest}.
     */
    public PageRequest next() {
        return PageRequest.of(pageNumber + 1, pageSize, sort);
    }

    /**
     * Return the preceding {@link PageRequest}.
     */
    public PageRequest previous() {
        return PageRequest.of(pageNumber - 1, pageSize, sort);
    }

    /**
     * Return the page size for this request.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Return the page number for this request.
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Return the {@link Sort} .
     */
    public Sort getSort() {
        return sort;
    }

}
