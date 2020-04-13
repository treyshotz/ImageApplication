package NTNU.IDATT1002.service;

import NTNU.IDATT1002.filters.ImageFilter;
import NTNU.IDATT1002.models.*;
import NTNU.IDATT1002.repository.ImageRepository;
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
    private TagService tagService;

    /**
     * Inject entity manager instance to the repositories.
     */
    public ImageService(EntityManager entityManager) {
        this.imageRepository = new ImageRepository(entityManager);
        this.tagService = new TagService(entityManager);
    }

    /**
     * Creates a new image from the input File
     *
     * @param user the user of the image
     * @param file the file uploaded
     * @return Optional containing the saved image
     */
    public Optional<Image> createImage(User user, File file, List<Tag> tags) {

        GeoLocation geoLocation = MetaDataExtractor.getGeoLocation(file);

        if(file == null)
            return Optional.empty();

        Image image = new Image();
        Metadata metadata = new Metadata();
        metadata.setImage(image);
        image.setMetadata(metadata);

        metadata.setGeoLocation(geoLocation);
        geoLocation.setMetadata(metadata);

        MetaDataExtractor.setMetadata(metadata, file);
        byte[] bFile = ImageUtil.convertToBytes(file.getPath());

        //TODO: Add image tags and add image to album
        image.setRawImage(bFile);
        image.setUser(user);
        image.setPath(file.getPath());
        image.addTags((ArrayList<Tag>) tagService.getOrCreateTags(tags));
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
        List<Image> allFound = new ArrayList<>();
        List<Image> byTags = imageRepository.findAllByTags(query);
        List<Image> byUsername = imageRepository.findAllByUsername(query);
        allFound.addAll(byTags);
        allFound.addAll(byUsername);
        return removeDuplicates(allFound);
    }


    /**
     * takes a list and removes all duplicate elements
     * @param images
     * @return list without duplicates
     */

    public List<Image> removeDuplicates(List<Image> images){
        return images.stream().distinct().collect(Collectors.toList());
    }
    


}
