package NTNU.IDATT1002.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * Class ImageAlbum representing an image album. Contains {@link Image}s and the creator ({@link User})
 *
 * @author Eirik Steira
 * @version 1.1 19.03.20
 * */
@Entity
@Table(name = "image_album")
@NamedQueries({
        @NamedQuery(name = "ImageAlbum.findAllByTitle",
        query="SELECT ia from ImageAlbum ia WHERE ia.title LIKE :queried_title ")
})
public class ImageAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title may not be blank")
    private String title;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Image> images = new ArrayList<>();;

    private String description;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public ImageAlbum() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Add given image to the album.
     *
     * @param image the image to add
     */
    public void addImage(Image image) {

    }

    /**
     * Remove given image from the album.
     *
     * @param image the image to add
     */
    public void removeImage(Image image) {

    }

    /**
     * Check if this and given entity are equal.
     * The two are defined as equal if all individual fields are equal.
     *
     * @param o object to check for equality against
     * @return true if this is equal to given object, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageAlbum that = (ImageAlbum) o;
        return id.equals(that.id) &&
                title.equals(that.title) &&
                user.equals(that.user) &&
                Objects.equals(images, that.images) &&
                Objects.equals(description, that.description) &&
                createdAt.equals(that.createdAt) &&
                updatedAt.equals(that.updatedAt);
    }

}
