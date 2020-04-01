package NTNU.IDATT1002.filters;

import NTNU.IDATT1002.models.Album;

import java.util.function.Predicate;


/**
 * Album Filter. Class to filter an album by title, description and tag names.
 *
 * @author Eirik Steira
 * @version 1.0 22.03.20
 */
public class AlbumFilter {

    /**
     * Chain the predicates and test the album by the given query.
     *
     * @param query the query to filter by
     * @return predicate chaining the album filter components.
     */
    public static Predicate<Album> filter(String query) {
        return filterByTitle(query)
                .or(filterByDescription(query))
                .or(filterByTags(query));
    }

    /**
     * Filter an album by given title.
     *
     * @param title the title to query by
     * @return predicate to apply.
     */
    private static Predicate<Album> filterByTitle(String title) {
        return album -> album.getTitle().contains(title);
    }

    /**
     * Filter an album by given description.
     *
     * @param description the description to query by
     * @return predicate to apply.
     */
    private static Predicate<Album> filterByDescription(String description) {
        return album -> album.getTitle().contains(description);
    }

    /**
     * Filter an album by given tag name.
     *
     * @param tagName the tag name to query by
     * @return predicate to apply.
     */
    private static Predicate<Album> filterByTags(String tagName) {
        return album -> album.getTags().stream()
                .anyMatch(tag -> tag.getName().equalsIgnoreCase(tagName));
    }

}
