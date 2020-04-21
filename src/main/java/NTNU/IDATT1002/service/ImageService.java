package NTNU.IDATT1002.service;

import NTNU.IDATT1002.models.*;
import NTNU.IDATT1002.repository.ImageRepository;
import NTNU.IDATT1002.repository.Page;
import NTNU.IDATT1002.repository.PageRequest;
import NTNU.IDATT1002.utils.ImageUtil;
import NTNU.IDATT1002.utils.MetaDataExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Image service
 * @author Stian Mogen, Eirik Steira, madslun
 * @version 1.0 22.03.2020
 */

public class ImageService implements PageableService<Image> {

    private ImageRepository imageRepository;
    private TagService tagService;
    private static Logger logger = LoggerFactory.getLogger(ImageService.class);

    /**
     * Inject entity manager instance to the repositories.
     */
    public ImageService(EntityManager entityManager) {
        this.imageRepository = new ImageRepository(entityManager);
        this.tagService = new TagService(entityManager);
    }

    /**
     * Retrieve paginated results specified by given {@link PageRequest}.
     *
     * @param pageRequest the {@link PageRequest} defining page number and size
     * @return the page containing results found based on the {@link PageRequest}
     */
    @Override
    public Page<Image> findAll(PageRequest pageRequest) {
        return imageRepository.findAll(pageRequest);
    }

    /**
     * Creates a new image from the input File
     * Sets metadata to image by using {@link MetaDataExtractor}
     * Sets a blob of image by using {@link ImageUtil}
     * Finally sets user, path, title and tags to image
     *
     * @param user the user of the image
     * @param file the file uploaded
     * @return Optional containing the saved image
     */
    public Optional<Image> createImage(User user, File file, List<Tag> tags, String title) {
        if(file == null) {
            logger.error("[x] An error occurred when trying to create a image; the file was null and the image could not be created");
            return Optional.empty();
        }
        GeoLocation geoLocation = MetaDataExtractor.getGeoLocation(file);


        Image image = new Image();
        Metadata metadata = new Metadata();
        metadata.setImage(image);
        metadata.setMiscMetadata(MetaDataExtractor.getMiscMetadata(file));
        image.setMetadata(metadata);

        metadata.setGeoLocation(geoLocation);
        geoLocation.setMetadata(metadata);

        MetaDataExtractor.setMetadata(metadata, file);
        byte[] bFile = ImageUtil.convertToBytes(file.getPath());

        image.setTitle(title);
        image.setRawImage(bFile);
        image.setUser(user);
        image.setPath(file.getPath());
        image.addTags((ArrayList<Tag>) tagService.getOrCreateTags(tags));
        return imageRepository.save(image);
    }

    /**
     * Finds each picture belonging to a specific user.
     *
     * @param user the user who uploaded the images
     * @return a list with all pictures from a user
     */
    public List<Image> getImageFromUser(User user) {
        return imageRepository.findAllByUsername(user.getUsername());
    }

    /**
     * Retrieves all images.
     *
     * @return list of all images.
     */
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    /**
     * Retrieves a image with given id
     * @param id that will searched for
     * @return Optional with image if found, empty else wise
     */
    public Optional<Image> findById(Long id){
        return imageRepository.findById(id);
    }

    /**
     * Searches images by tags and username, and merges the two list into one with all images
     * uses removeDuplicates list to return a list with no duplicate images
     * @param query
     * @return a list with no duplicate images
     */

    public List<Image> searchResult(String query){
        return imageRepository.findAllByQueryString(query);
    }

}
