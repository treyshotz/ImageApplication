package NTNU.IDATT1002.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for {@link Page}.
 */
class PageTest {

    private Page<Object> pageWithNext;
    private Page<Object> pageWithoutNext;
    private Page<Object> pageWithPrevious;
    private Page<Object> pageWithoutPrevious;
    private Page<Object> pageWithLeftoversOnLastPage;

    /**
     * Setup pages mimicking real situations.
     */
    @BeforeEach
    void setUp() {
        pageWithNext = new Page<>(
                new ArrayList<>(),
                PageRequest.of(0, 1),
                2
        );
        pageWithoutNext = new Page<>(
                new ArrayList<>(),
                PageRequest.of(0, 1),
                1
        );
        pageWithPrevious = new Page<>(
                new ArrayList<>(),
                PageRequest.of(1, 1),
                2
        );
        pageWithoutPrevious = new Page<>(
                new ArrayList<>(),
                PageRequest.of(0, 1),
                1
        );
        pageWithLeftoversOnLastPage = new Page<>(
                new ArrayList<>(),
                PageRequest.of(0, 2),
                3
        );
    }

    /**
     * Test that page returns next {@link PageRequest} when there is a next page.
     */
    @Test
    void testNextPageableWithNextPage() {
        PageRequest currentPageRequest = pageWithNext.getPageRequest();
        PageRequest nextPageRequest = pageWithNext.nextPageRequest();

        assertEquals(currentPageRequest.getPageNumber() + 1, nextPageRequest.getPageNumber());
    }

    /**
     * Test that page returns current {@link PageRequest} when there is no next page.
     */
    @Test
    void testNextPageableWithoutNextPage() {
        PageRequest currentPageRequest = pageWithoutNext.getPageRequest();
        PageRequest nextPageRequest = pageWithoutNext.nextPageRequest();

        assertEquals(currentPageRequest, nextPageRequest);
    }

    /**
     * Test that page returns previous {@link PageRequest} when there is a previous page.
     */
    @Test
    void testPreviousPageableWithPreviousPage() {
        PageRequest currentPageRequest = pageWithPrevious.getPageRequest();
        PageRequest previousPageRequest = pageWithPrevious.previousPageRequest();

        assertEquals(currentPageRequest.getPageNumber() - 1, previousPageRequest.getPageNumber());
    }

    /**
     * Test that page returns current {@link PageRequest} when there is no previous page.
     */
    @Test
    void testPreviousPageableWithoutPreviousPage() {
        PageRequest currentPageRequest = pageWithoutPrevious.getPageRequest();
        PageRequest previousPageRequest = pageWithoutPrevious.previousPageRequest();

        assertEquals(currentPageRequest, previousPageRequest);
    }

    /**
     * Test that last page number is calculated correctly.
     * Should return 2 pages when page size is 2 and total is 3.
     * Pagination starts at 0.
     */
    @Test
    void getLastPageNumber() {
        assertEquals(1, pageWithLeftoversOnLastPage.getLastPageNumber());
    }

    /**
     * Test that attempting to create a page with a negative total fails.
     */
    @Test
    public void testCreatePageWithNegativeTotal() {
        try {
            Page<Object> pageWithNegativeTotal = new Page<>(
                    new ArrayList<>(),
                    PageRequest.of(1, 1),
                    -1L
            );
            fail("Should not be able to create page with negative total");
        } catch (IllegalArgumentException ignored) {

        }
    }
}