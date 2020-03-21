package NTNU.IDATT1002.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Creates table named tag
 */

@Entity
@Table(name = "Tag")
public class Tag {

    /**
     * Defines the tag-id, this cannot be blank
     */


    @Id @NotBlank(message = "Tag-Id may not be blank")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tagId;


    /**
     * Creates a many to many relations between tag and image
     * on table ImageTags, joining column tagId and imageId
     */

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "ImageTags",
            joinColumns = { @JoinColumn(name = "tagId")},
            inverseJoinColumns = {@JoinColumn(name = "imageId")}
    )
    Set<Image> image = new HashSet<>();

    @NotBlank (message = "Tag title may not be blank")
    private String title;

    /**
     * Constructor with tagId and title as parameters
     * @param tagId
     * @param title
     */

    public Tag(Long tagId, String title){
        this.tagId = tagId;
        this.title = title;
    }

    /**
     * Constructor thar takes in a tag object
     * @param tag
     */

    public Tag(Tag tag){
        this(tag.getTagId(),
                tag.getTitle());
    }

    public Long getTagId() {
        return tagId;
    }

    public String getTitle() {
        return title;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
