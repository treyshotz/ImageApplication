package NTNU.IDATT1002.service.filters;

import NTNU.IDATT1002.models.ImageAlbum;

import java.util.function.Predicate;


/**
 * Image Album Filter. Class to filter an image album by title, description and tag names.
 *
 * @author Eirik Steira
 * @version 1.0 22.03.20
 */
public class ImageAlbumFilter {

    /**
     * Chain the predicates and test the image album by the given query.
     *
     * @param query the query to filter by
     * @return predicate chaining the image album filter components.
     */
    public static Predicate<ImageAlbum> filter(String query) {
        return filterByTitle(query)
                .or(filterByDescription(query))
                .or(filterByTags(query));
    }

    /**
     * Filter an image album by given title.
     *
     * @param title the title to query by
     * @return predicate to apply.
     */
    private static Predicate<ImageAlbum> filterByTitle(String title) {
        return imageAlbum -> imageAlbum.getTitle().contains(title);
    }

    /**
     * Filter an image album by given description.
     *
     * @param description the description to query by
     * @return predicate to apply.
     */
    private static Predicate<ImageAlbum> filterByDescription(String description) {
        return imageAlbum -> imageAlbum.getTitle().contains(description);
    }

    /**
     * Filter an image album by given tag name.
     *
     * @param tagName the tag name to query by
     * @return predicate to apply.
     */
    private static Predicate<ImageAlbum> filterByTags(String tagName) {
        return imageAlbum -> imageAlbum.getTags().stream()
                .anyMatch(tag -> tag.getName().equalsIgnoreCase(tagName));
    }

}
