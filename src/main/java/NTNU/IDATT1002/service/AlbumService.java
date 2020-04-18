package NTNU.IDATT1002.service;

import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.repository.AlbumRepository;
import NTNU.IDATT1002.repository.Page;
import NTNU.IDATT1002.repository.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Album Service. Supports common domain specific operations such as creating and retrieving albums.
 *
 * @author Eirik Steira
 * @version 1.1 01.04.20
 */
public class AlbumService {

    private AlbumRepository albumRepository;

    private TagService tagService;

    private static Logger logger = LoggerFactory.getLogger(AlbumService.class);

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
     * Retrieve paginated results specified by given {@link PageRequest}.
     *
     * @param pageRequest the {@link PageRequest} defining page number and size
     * @return the page containing results found based on the {@link PageRequest}
     */
    public Page<Album> findAll(PageRequest pageRequest) {
        return albumRepository.findAll(pageRequest);
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
     * Add given image to given album.
     *
     * @param album the album to add the image to
     * @param image the image to add
     * @return the updated album
     */
    public Optional<Album> addImage(Album album, Image image){
        album.addImage(image);
        return albumRepository.update(album);
    }

    /**
     * Find a single image as an album preview.
     *
     * @param album the album to get a preview from
     * @return An optional image if found
     */
    public Optional<Image> findPreviewImage(Album album) {
        return albumRepository.findPreviewImage(album.getId());
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

        String destinationFile = String.format("%s/Downloads/%s.pdf",
                System.getProperty("user.home"),
                album.getTitle());

        AlbumDocument document = new PdfDocument(album, destinationFile);
        document.create();
        logger.info("[x] Saved PDF document to {}", destinationFile);

        return document;
    }

    /**
     * Takes in a string and searched through all album by tags, username and title to find results.
     *
     * @param query the query string
     * @return list of results without duplocates
     */
    public List<Album> searchResult(String query){
        List<Album> allFound = new ArrayList<>();
        List<Album> byTags = albumRepository.findAllByTags(query);
        List<Album> byUsername = albumRepository.findAllByUsername(query);
        List<Album> byTitle = albumRepository.findAllByTitle(query);
        allFound.addAll(byTags);
        allFound.addAll(byUsername);
        allFound.addAll(byTitle);
        return removeDuplicates(allFound);
    }

    /**
     * Removes all duplicate elements in given list.
     *
     * @param albums the list of albums to remove duplicates
     * @return list without duplicates
     */
    public List<Album> removeDuplicates(List<Album> albums){
        return albums.stream().distinct().collect(Collectors.toList());
    }

}
