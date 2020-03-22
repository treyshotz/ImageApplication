package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.Metadata;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Tag Repository.
 *
 * Implementation of {@link  GenericRepository} which supports regular Create, Read, Update and Delete operations.
 * @author Stian Mogen, Eirik Steira
 * @version 1.0 22.03.20
 * @see NTNU.IDATT1002.repository.GenericRepository
 */
public class MetadataRepository extends GenericRepository<Metadata, Long> {

    /**
     * Constructor to inject {@link EntityManager} dependency and sets the class type to {@link Metadata}
     *
     * @param entityManager the entity manager to utilize
     */
    public MetadataRepository(EntityManager entityManager) {
        super(entityManager);
        setClassType(Metadata.class);
    }

    /**
     * Retrieves a tag if found and creates it if not.
     *
     * @param metadata the tag to retrieve
     * @return the tag if found, else the newly created one.
     */
    public Optional<Metadata> findOrCreate(Metadata metadata) {
        Optional<Metadata> foundMetadata = findById(metadata.getMetadataId());

        if (foundMetadata.isPresent())
            return foundMetadata;

        return save(metadata);
    }

}