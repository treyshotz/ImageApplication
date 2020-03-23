package NTNU.IDATT1002.service;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Metadata;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.repository.ImageRepository;
import NTNU.IDATT1002.repository.MetadataRepository;
import NTNU.IDATT1002.repository.TagRepository;
import NTNU.IDATT1002.service.filters.ImageFilter;
import NTNU.IDATT1002.utils.ImageUtil;
import NTNU.IDATT1002.utils.MetaDataExtractor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Image service
 * @author Stian Mogen, Eirik Steira, madslun
 * @version 1.0 22.03.2020
 */

public class ImageService {

    private ImageRepository imageRepository;
    private MetadataRepository metadataRepository;
    private TagRepository tagRepository;

    /**
     * Inject entity manager instance to the repositories.
     */
    public ImageService() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplication");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        this.imageRepository = new ImageRepository(entityManager);
        this.metadataRepository = new MetadataRepository(entityManager);
        this.tagRepository = new TagRepository(entityManager);
    }

    /**
     * Creates a new image from the input File
     *
     * @param user the user of the image
     * @param file the file uploaded
     * @return Optional containing the saved image
     */
    public Optional<Image> createImage(User user, File file, ArrayList<Tag> tags) {
        Image image = new Image();
        byte[] bFile = ImageUtil.convertToBytes(file.getPath());
        Metadata metadata = MetaDataExtractor.assembleMetaData(file, image);
        metadata = metadataRepository.save(metadata).orElse(null);

        //TODO: Unsure what to do with imageAlbum
        image.setRawImage(bFile);
        image.setUser(user);
        image.setUser(null);
        image.setMetadata(metadata);
        image.setPath(file.getPath());
        //image.addTags(tags);
        return imageRepository.save(image);
    }

    /**
     * Finds each picture belonging to a specific user
     * @param user
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
     *  Adds the given tag to the given image album.
     *
     * @param image the image album to add the tag to
     * @param tag the tag to add
     * @return the updated image album
     */

    public Optional<Image> addTagToImage(Image image, Tag tag) {
        Image foundImage = imageRepository.findById(image.getId())
                .orElseThrow(IllegalArgumentException::new);
        Tag foundTag = tagRepository.findOrCreate(tag)
                .orElseThrow(IllegalArgumentException::new);

        foundImage.addTag(foundTag);

        return imageRepository.save(foundImage);
    }

    /**
     * Search all images by tags specified in {@link ImageFilter#filter(String)}.
     *
     * @param query the query to filter by
     * @return list of images matching the query
     */

    //This search method is for futureproofing, when we will search using additional parameters than just tags
    public List<Image> searchImageAlbums(String query) {
        List<Image> allImages = imageRepository.findAll();
        return allImages.stream()
                .filter(ImageFilter.filter(query))
                .collect(Collectors.toList());
    }
}
