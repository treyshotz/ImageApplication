package NTNU.IDATT1002.service;

import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.repository.ImageRepository;
import NTNU.IDATT1002.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Tag Service. Supports common domain specific operations such as converting a list of tags to a string
 * and opposite.
 *
 * @version 1.1 01.04.20
 */
public class TagService {

    private TagRepository tagRepository;
    
    private ImageRepository imageRepository;

    private static Logger logger = LoggerFactory.getLogger(TagService.class);
    
    /**
     * Inject entity manager instance to the repositories.
     */
    public TagService(EntityManager entityManager) {
        this.tagRepository = new TagRepository(entityManager);
        this.imageRepository = new ImageRepository(entityManager);
    }

    /**
     * Gets or creates given tags in given list. Ignores null values.
     *
     * @param tags the list of tags
     * @return a list of persisted tags
     */
    public List<Tag> getOrCreateTags(List<Tag> tags) {
        return tags.stream()
                .map(tag -> tagRepository.findOrCreate(tag))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves tags from text field and converts them to a list of tag objects.
     *
     * @return the list of tag objects
     */
    public static List<Tag> getTagsFromString(String tagsAsString) {
        if (tagsAsString.isBlank()) {
            String[] tags = tagsAsString.split(" ");

            return Stream.of(tags)
                .map(Tag::new)
                .collect(Collectors.toList());

        }else {
            String[] tags = tagsAsString
            .trim()
            .split("[, ?.@]+");

            return Stream.of(tags)
                .map(Tag::new)
                .collect(Collectors.toList());

        }
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
                .collect(Collectors.joining(" "));
    }

    /**
     * Retrieve given tag. Either get an existing or create it.
     *
     * @param tag the tag to retrieve
     * @throws IllegalArgumentException if tag could not be found or created
     * @return the persisted tag, else a new persisted tag instance
     */
    public Tag getSingleTag(Tag tag) {
        return tagRepository.findOrCreate(tag);
    }

    /**
     *  Adds the given tag to the given album.
     *
     * @param image the album to add the tag to
     * @param tag the tag to add
     * @return the updated album
     */

    public Optional<Image> addTagToImage(Image image, Tag tag) {
        Image foundImage = imageRepository.findById(image.getId())
            .orElseThrow(IllegalArgumentException::new);
        Tag foundTag = tagRepository.findOrCreate(tag);

        foundImage.addTag(foundTag);

        return imageRepository.save(foundImage);
    }
}
