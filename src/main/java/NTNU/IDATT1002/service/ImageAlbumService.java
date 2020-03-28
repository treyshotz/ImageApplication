package NTNU.IDATT1002.service;

import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.ImageAlbum;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.repository.ImageAlbumRepository;
import NTNU.IDATT1002.repository.TagRepository;
import NTNU.IDATT1002.filters.ImageAlbumFilter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static NTNU.IDATT1002.service.TagService.getTagsFromString;


/**
 * Image Album Service. Supports common domain specific operations such as creating an image album,
 * adding images and tags as well as searching by an arbitrary query.
 *
 * @author Eirik Steira
 * @version 1.0 22.03.20
 */
public class ImageAlbumService {

    private ImageAlbumRepository imageAlbumRepository;

    private TagRepository tagRepository;


    /**
     * Inject entity manager instance to the repositories.
     */
    public ImageAlbumService() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplication");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        this.imageAlbumRepository = new ImageAlbumRepository(entityManager);
        this.tagRepository = new TagRepository(entityManager);
    }

    public Optional<ImageAlbum> getImageAlbumById(Long imageAlbumId) {
        return imageAlbumRepository.findById(imageAlbumId);
    }

    /**
     * Retrieves all image albums.
     *
     * @return list of all image albums.
     */
    public List<ImageAlbum> getAllImageAlbums() {
        return imageAlbumRepository.findAll();
    }

    /**
     * Create a new image album with all fields populated.
     *
     * @param title the title of the image album
     * @param description the description of the image album
     * @param user the user of the image album
     * @param tags the tags of the image album
     * @param images the images of the image album
     * @return Optional containing the saved image album
     */
    public Optional<ImageAlbum> createImageAlbum(String title,
                                                 String description,
                                                 User user,
                                                 List<Tag> tags,
                                                 List<Image> images) {
        ImageAlbum imageAlbum = new ImageAlbum();
        imageAlbum.setTitle(title);
        imageAlbum.setDescription(description);
        imageAlbum.setUser(user);
        imageAlbum.setTags(getOrCreateTags(tags));
        imageAlbum.setImages(images);

        return imageAlbumRepository.save(imageAlbum);
    }

    /**
     * Gets or creates given tags in given list.
     *
     * @param tags the list of tags
     * @return a list of persisted tags
     */
    private List<Tag> getOrCreateTags(List<Tag> tags) {
        return tags.stream()
                .map(tag -> tagRepository.findOrCreate(tag).orElse(null))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all image albums created by the given user by username.
     *
     * @param title the title of the image album
     * @param description the description of the image album
     * @param user the user of the image album
     * @param tagsAsString the tags of the image album as strings
     */
    public Optional<ImageAlbum> createImageAlbum(String title, String description, User user, String tagsAsString) {
        List<Tag> tags = getTagsFromString(tagsAsString);
        return createImageAlbum(title, description, user, tags, new ArrayList<>());
    }


    /**
     * Retrieves all image albums created by the given user by username.
     *
     * @param user the user to query by
     * @return list of all image albums created by the user
     */
    public List<ImageAlbum> getImageAlbumFromUser(User user) {
        return imageAlbumRepository.findAllByUsername(user.getUsername());
    }


    /**
     *  Adds the given tag to the given image album.
     *
     * @param imageAlbum the image album to add the tag to
     * @param tag the tag to add
     * @return the updated image album
     */
    public Optional<ImageAlbum> addTagToImageAlbum(ImageAlbum imageAlbum, Tag tag) {
        ImageAlbum foundImageAlbum = imageAlbumRepository.findById(imageAlbum.getId())
                .orElseThrow(IllegalArgumentException::new);
        Tag foundTag = tagRepository.findOrCreate(tag)
                .orElseThrow(IllegalArgumentException::new);

        foundImageAlbum.addTag(foundTag);

        return imageAlbumRepository.save(foundImageAlbum);
    }

    /**
     * Add given image to the given image album.
     *
     * @param imageAlbum the image album to add the image to
     * @param image the image to add
     */
    public Optional<ImageAlbum> addImageToImageAlbum(ImageAlbum imageAlbum, Image image) {
        ImageAlbum foundImageAlbum = imageAlbumRepository.findById(imageAlbum.getId())
                .orElseThrow(IllegalArgumentException::new);

        foundImageAlbum.addImage(image);
        
        return imageAlbumRepository.save(foundImageAlbum);
    }

    /**
     * Search all images by title, description and tags specified in {@link ImageAlbumFilter#filter(String)}.
     *
     * @param query the query to filter by
     * @return list of image albums matching the query
     */
    public List<ImageAlbum> searchImageAlbums(String query) {
        List<ImageAlbum> allImageAlbums = imageAlbumRepository.findAll();
        return allImageAlbums.stream()
                .filter(ImageAlbumFilter.filter(query))
                .collect(Collectors.toList());
    }

}
