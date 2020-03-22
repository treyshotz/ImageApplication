package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.Tag;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Tag Repository.
 *
 * Implementation of {@link  GenericRepository} which supports regular Create, Read, Update and Delete operations.
 * @author Eirik Steira
 * @version 1.0 21.03.20
 * @see NTNU.IDATT1002.repository.GenericRepository
 */
public class TagRepository extends GenericRepository<Tag, Long> {

    /**
     * Constructor to inject {@link EntityManager} dependency and sets the class type to {@link Tag}
     *
     * @param entityManager the entity manager to utilize
     */
    public TagRepository(EntityManager entityManager) {
        super(entityManager);
        setClassType(Tag.class);
    }

    /**
     * Retrieves a tag if found and creates it if not.
     *
     * @param tag the tag to retrieve
     * @return the tag if found, else the newly created one.
     */
    public Optional<Tag> findOrCreate(Tag tag) {
        Optional<Tag> foundTag = findById(tag.getTagId());

        if (foundTag.isPresent())
            return foundTag;

        return save(tag);
    }

}