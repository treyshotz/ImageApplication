package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Tag;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 * Tag Repository.
 *
 * Implementation of {@link  AbstractRepository} which supports regular Create, Read, Update and Delete operations.
 * @author Eirik Steira
 * @version 1.0 21.03.20
 * @see AbstractRepository
 */
public class TagRepository extends AbstractRepository<Tag, Long> {

    /**
     * Mapping to @NamedQuery 'find all albums by users username' defined in {@link  Image}
     */
    public static final String FIND_TAG_BY_NAME = "Tag.findByName";

    /**
     * Constructor to inject {@link EntityManager} dependency and sets the class type to {@link Tag}
     *
     * @param entityManager the entity manager to utilize
     */
    public TagRepository(EntityManager entityManager) {
        super(entityManager);
        setEntityClass(Tag.class);
    }

    /**
     * Retrieves a tag if found and creates it if not.
     * Does not allow null value or blank names.
     *
     * @param tag the tag to retrieve
     * @return the tag if found, else the newly created one.
     */
    public Tag findOrCreate(Tag tag) {
        if (tag.getName() == null || tag.getName().isBlank())
            return null;

        Tag foundTag;
        try {
            foundTag = entityManager.createNamedQuery(FIND_TAG_BY_NAME, Tag.class)
                    .setParameter("name", tag.getName())
                    .getSingleResult();
        } catch (NonUniqueResultException e) {
            super.logger.error("[x] Query for tag {} returned multiple results {}", tag.getName(), e);
            return null;
        } catch (NoResultException e) {
            Tag tagToSave = new Tag();
            tagToSave.setName(tag.trim());
            super.logger.info("[x] Could not find tag - Creating a new one: {}", tag);
            foundTag = save(tagToSave).orElseThrow(() -> new IllegalArgumentException("Could not create tag" + tag));
        }

        return foundTag;
    }

}