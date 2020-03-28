package NTNU.IDATT1002.service;

import NTNU.IDATT1002.models.Tag;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Tag Service. Supports common domain specific operations such as converting a list of tags to a string
 * and opposite.
 *
 * @author Eirik Steira
 * @version 1.0 26.03.20
 */
public class TagService {

    /**
     * Retrieves tags from text field and converts them to a list of tag objects.
     *
     * @return the list of tag objects
     */
    public static List<Tag> getTagsFromString(String tagsAsString) {
        String[] tags = tagsAsString
                .trim()
                .split("[, ?.@]+");

        return Stream.of(tags)
                .map(Tag::new)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves tags from list of tags and converts them to a concatenated string.
     *
     * @param tags the list of tags
     * @return the tags as a string
     */
    public static String getTagsAsString(List<Tag> tags) {
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.joining(", "));
    }
}
