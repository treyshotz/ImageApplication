package NTNU.IDATT1002.service;

import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.repository.AlbumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Album Service. Supports common domain specific operations such as creating and retrieving albums.
 *
 * @author Eirik Steira
 * @version 1.1 01.04.20
 */
public class AlbumService {

    private AlbumRepository albumRepository;

    private TagService tagService;

    private static Logger logger = LoggerFactory.getLogger(AlbumService.class);;

    /**
     * Inject entity manager instance to the repositories.
     */
    public AlbumService(EntityManager entityManager) {
        this.albumRepository = new AlbumRepository(entityManager);
        this.tagService = new TagService(entityManager);
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
        album.setTags(tagService.getOrCreateTags(tags));
        album.setImages(images);

        return albumRepository.save(album);
    }

    /**
     * Retrieves all albums created by the given user by username.
     *
     * @param title the title of the album
     * @param description the description of the album
     * @param user the user of the album
     * @param tags the tags of the album
     */
    public Optional<Album> createEmptyAlbum(String title, String description, User user, List<Tag> tags) {
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
        document.create();
        logger.info("[x] Saved PDF document to " + destinationFile);

        return document;
    }
}
