package NTNU.IDATT1002.service;
import NTNU.IDATT1002.filters.ImageFilter;
import NTNU.IDATT1002.models.*;
import NTNU.IDATT1002.repository.*;
import NTNU.IDATT1002.utils.ImageUtil;
import NTNU.IDATT1002.utils.MetaDataExtractor;

import javax.persistence.EntityManager;
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
    private HistorgramRepository historgramRepository;
    private GeoLocatioRepository geoLocatioRepository;
    private MetaDataExtractor metaDataExtractor;

    /**
     * Inject entity manager instance to the repositories.
     */
    public ImageService(EntityManager entityManager) {
        this.imageRepository = new ImageRepository(entityManager);
        this.metadataRepository = new MetadataRepository(entityManager);
        this.tagRepository = new TagRepository(entityManager);
        this.historgramRepository = new HistorgramRepository(entityManager);
        this.geoLocatioRepository = new GeoLocatioRepository(entityManager);
        this.metaDataExtractor = new MetaDataExtractor();
    }

    /**
     * Creates a new image from the input File
     *
     * @param user the user of the image
     * @param file the file uploaded
     * @return Optional containing the saved image
     */
    public Optional<Image> createImage(User user, File file, ArrayList<Tag> tags) {

        GeoLocation geoLocation = metaDataExtractor.getGeoLocation(file);
        Histogram histogram = metaDataExtractor.getHistogram(file);

        Image image = new Image();
        Metadata metadata = new Metadata();
        metadata.setImage(image);
        image.setMetadata(metadata);

        metadata.setGeoLocation(geoLocation);
        geoLocation.setMetadata(metadata);

        metadata.setHistogram(histogram);
        histogram.setMetadata(metadata);


        byte[] bFile = ImageUtil.convertToBytes(file.getPath());

        //TODO: Add image tags and add image to imageAlbum
        image.setRawImage(bFile);
        image.setUser(user);
        image.setPath(file.getPath());
        image.addTags(tags);
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
    public List<Image> searchImages(String query) {
        List<Image> allImages = imageRepository.findAll();
        return allImages.stream()
                .filter(ImageFilter.filter(query))
                .collect(Collectors.toList());
    }
}
