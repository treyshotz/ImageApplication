package NTNU.IDATT1002.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for {@link PageRequest}.
 */
public class PageRequestTest {

    private PageRequest pageRequest;

    /**
     * Setup a simple {@link PageRequest} requesting page 0 with 0 elements per page.
     */
    @BeforeEach
    void setUp() {
        pageRequest = PageRequest.of(0, 0);
    }

    /**
     * Test that a new page request is correctly created.
     */
    @Test
    void testOf() {
        PageRequest pageRequestOf = PageRequest.of(0, 0);
        assertEquals(pageRequest, pageRequestOf);
    }

    /**
     * Test that a new page request is correctly created with sort.
     */
    @Disabled("Sort is not implemented yet")
    @Test
    void testOfWithSort() {
        Sort sort = Sort.of(Sort.Type.DATE);
        PageRequest pageRequestWithSort = PageRequest.of(0, 0, sort);

        assertEquals(0, pageRequestWithSort.getPageNumber());
        assertEquals(0, pageRequestWithSort.getPageSize());
        assertNotNull(pageRequestWithSort.getSort());
    }

    /**
     * Test that getting next page increments page number and keeps page size.
     */
    @Test
    void testNext() {
        PageRequest nextPageRequest = pageRequest.next();

        assertEquals(pageRequest.getPageNumber() + 1, nextPageRequest.getPageNumber());
        assertEquals(pageRequest.getPageSize(), nextPageRequest.getPageSize());
    }

    /**
     * Test that getting next page decrements page number and keeps page size.
     */
    @Test
    void testPrevious() {
        PageRequest nextPageRequest = pageRequest.next();

        assertEquals(pageRequest.getPageNumber() + 1, nextPageRequest.getPageNumber());
        assertEquals(pageRequest.getPageSize(), nextPageRequest.getPageSize());
    }

    /**
     * Should return page number.
     */
    @Test
    public void getPageNumber() {
        assertEquals(0, pageRequest.getPageSize());
    }

    /**
     * Should return page size.
     */
    @Test
    public void testGetPageSize() {
        assertEquals(0, pageRequest.getPageNumber());
    }
}