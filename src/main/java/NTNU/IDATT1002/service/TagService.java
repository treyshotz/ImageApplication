package NTNU.IDATT1002.service;

import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Tag;

import NTNU.IDATT1002.repository.ImageRepository;
import NTNU.IDATT1002.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;


/**
 * Tag Service. Supports common domain specific operations such as converting a list of tags to a string
 * and opposite.
 *
 * @author Eirik Steira
 * @version 1.0 26.03.20
 */
public class TagService {

TagRepository tagRepository;
ImageRepository imageRepository;

public TagService(EntityManager entityManager){
    this.tagRepository = new TagRepository(entityManager);
    this.imageRepository = new ImageRepository(entityManager);

}

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

    /**
     * Gets or creates given tags in given ArrayList.
     *
     * @@author Lars Østby
     * @param tags the list of tags
     * @return an ArrayList of persisted tags
     */
    public  List<Tag> getOrCreateTags(List<Tag> tags) {
        return tags.stream().map(tag -> tagRepository.findOrCreate(tag).orElse(null)).collect(Collectors.toList());
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
        Tag foundTag = tagRepository.findOrCreate(tag)
            .orElseThrow(IllegalArgumentException::new);

        foundImage.addTag(foundTag);

        return imageRepository.save(foundImage);
    }
}
