package NTNU.IDATT1002.filters;


import NTNU.IDATT1002.models.Image;

import java.util.function.Predicate;


/**
 * Image Album Filter. Class to filter an image album by title, description and tag names.
 *
 * @author Stian Mogen
 * @version 1.0 22.03.20
 */
public class ImageFilter {

    /**
     * Image filter can filter an album by tags
     * Uses method filterByTags
     * @param query the query to filter by
     * @return predicate chaining the image album filter components.
     */

    public static Predicate<Image> filter(String query) {
        return filterByTags(query);
    }

    /**
     * Filters images based on tag
     * @param tagName
     * @return  predicate to apply
     */


    private static Predicate<Image> filterByTags(String tagName){
        return image -> image.getTags().stream()
                .anyMatch(tag -> tag.getName().equalsIgnoreCase(tagName));
    }

}
