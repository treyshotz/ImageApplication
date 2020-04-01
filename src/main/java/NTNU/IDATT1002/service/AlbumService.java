package NTNU.IDATT1002.service;

import NTNU.IDATT1002.filters.AlbumFilter;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.repository.AlbumRepository;
import NTNU.IDATT1002.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static NTNU.IDATT1002.service.TagService.getTagsFromString;


/**
 * Album Service. Supports common domain specific operations such as creating an album,
 * adding images and tags as well as searching by an arbitrary query.
 *
 * @author Eirik Steira
 * @version 1.0 22.03.20
 */
public class AlbumService {

    private AlbumRepository albumRepository;

    private TagRepository tagRepository;

    private Logger logger;

    /**
     * Inject entity manager instance to the repositories.
     */
    public AlbumService(EntityManager entityManager) {
        this.albumRepository = new AlbumRepository(entityManager);
        this.tagRepository = new TagRepository(entityManager);
        logger = LoggerFactory.getLogger("ImageApplicationLogger");
    }

    public Optional<Album> getAlbumById(Long albumId) {
        return albumRepository.findById(albumId);
    }

    /**
     * Retrieves all albums.
     *
     * @return list of all albums.
     */
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    /**
     * Create a new album with all fields populated.
     *
     * @param title the title of the album
     * @param description the description of the album
     * @param user the user of the album
     * @param tags the tags of the album
     * @param images the images of the album
     * @return Optional containing the saved album
     */
    public Optional<Album> createAlbum(String title,
                                                 String description,
                                                 User user,
                                                 List<Tag> tags,
                                                 List<Image> images) {
        Album album = new Album();
        album.setTitle(title);
        album.setDescription(description);
        album.setUser(user);
        album.setTags(getOrCreateTags(tags));
        album.setImages(images);

        return albumRepository.save(album);
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
     * Retrieves all albums created by the given user by username.
     *
     * @param title the title of the album
     * @param description the description of the album
     * @param user the user of the album
     * @param tagsAsString the tags of the album as strings
     */
    public Optional<Album> createAlbum(String title, String description, User user, String tagsAsString) {
        List<Tag> tags = getTagsFromString(tagsAsString);
        return createAlbum(title, description, user, tags, new ArrayList<>());
    }

    /**
     * Create and return a new document for the album with the given id.
     * The document is saved to the users dowloads folder.
     *
     * @param albumId the album id to get a document for
     * @return the document created
     */
    public AlbumDocument getDocument(Long albumId) {
        Album album = getAlbumById(albumId)
                .orElseThrow(IllegalArgumentException::new);

        String destinationFile = String.format("%s/downloads/%s.pdf",
                System.getProperty("user.home"),
                album.getTitle());

        AlbumDocument document = new PdfDocument(album, destinationFile);
        document.createDocument();
        logger.info("[x] Saved PDF document to " + destinationFile);

        return document;
    }

    /**
     * Retrieves all albums created by the given user by username.
     *
     * @param user the user to query by
     * @return list of all albums created by the user
     */
    public List<Album> getAlbumFromUser(User user) {
        return albumRepository.findAllByUsername(user.getUsername());
    }


    /**
     *  Adds the given tag to the given album.
     *
     * @param album the album to add the tag to
     * @param tag the tag to add
     * @return the updated album
     */
    public Optional<Album> addTagToAlbum(Album album, Tag tag) {
        Album foundAlbum = albumRepository.findById(album.getId())
                .orElseThrow(IllegalArgumentException::new);
        Tag foundTag = tagRepository.findOrCreate(tag)
                .orElseThrow(IllegalArgumentException::new);

        foundAlbum.addTag(foundTag);

        return albumRepository.save(foundAlbum);
    }

    /**
     * Add given image to the given album.
     *
     * @param album the album to add the image to
     * @param image the image to add
     */
    public Optional<Album> addImageToAlbum(Album album, Image image) {
        Album foundAlbum = albumRepository.findById(album.getId())
                .orElseThrow(IllegalArgumentException::new);

        foundAlbum.addImage(image);
        
        return albumRepository.save(foundAlbum);
    }

    /**
     * Search all images by title, description and tags specified in {@link AlbumFilter#filter(String)}.
     *
     * @param query the query to filter by
     * @return list of albums matching the query
     */
    public List<Album> searchAlbums(String query) {
        List<Album> allAlbums = albumRepository.findAll();
        return allAlbums.stream()
                .filter(AlbumFilter.filter(query))
                .collect(Collectors.toList());
    }

}
