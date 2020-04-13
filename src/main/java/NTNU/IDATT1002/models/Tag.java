package NTNU.IDATT1002.models;



import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Tag model
 * @author Stian Mogen
 */

/**
 * Creates table named tag
 */

@Entity
@Table(name = "tag")
@NamedQuery(name="Tag.findByName", query = "SELECT tag from Tag tag WHERE tag.name = :name")
public class Tag {

    /**
     * Defines the tag-id, this cannot be blank
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;


    /**
     * Creates a many to many relations between tag and image
     * on table ImageTags, joining column tagId and imageId
     */
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "image_tag",
            joinColumns = {@JoinColumn(name = "tags_tagId",
                    referencedColumnName = "tagId"
            )},
            inverseJoinColumns = {@JoinColumn(name = "image_id",
                    referencedColumnName = "id"
            )}
    )
    Set<Image> image = new HashSet<>();

    /**
     * Creates a many to many relations between tag and album
     */
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "album_tags",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "album_id")}
    )
    Set<Image> albums = new HashSet<>();

    @NotBlank(message = "Tag name may not be blank")
    private String name;

    public Tag() {
    }

    /**
     * Contructor to set initial tag name.
     *
     * @param name the name of the tag
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * Constructor with tagId and name as parameters
     *
     * @param tagId
     * @param name
     */
    public Tag(Long tagId, String name) {
        this.tagId = tagId;
        this.name = name;
    }

    /**
     * Constructor thar takes in a tag object
     *
     * @param tag
     */
    public Tag(Tag tag) {
        this(tag.getTagId(),
                tag.getName());
    }

    public Long getTagId() {
        return tagId;
    }

    public String getName() {
        return name;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String trim() {
        this.name = this.name.trim();
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(tagId, tag.tagId) &&
                Objects.equals(name, tag.name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", name='" + name + '\'' +
                '}';
    }
}
