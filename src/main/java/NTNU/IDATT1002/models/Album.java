package NTNU.IDATT1002.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Class Album representing an album. Contains {@link Image}s and the creator ({@link User})
 *
 * @author Eirik Steira
 * @version 1.1 19.03.20
 * */
@Entity
@Table(name = "album")
@NamedQueries({
        @NamedQuery(name="Album.findAllByUsername",
                query = "SELECT ia from Album ia WHERE ia.user.username = :username"),
        @NamedQuery(name="Album.findByTags",
                query = "SELECT ia from Album ia "
                        + "join ia.tags tg "
                        + "where tg.name = :name"),
        @NamedQuery(name="Image.findByTitle",
                query = "SELECT ia from Album ia WHERE ia.title = :title")
})
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title may not be blank")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tag> tags = new ArrayList<>();;

    private String description;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public Album() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Add given image to this album.
     *
     * @param image the image to add
     */
    public void addImage(Image image) {
        image.addAlbum(this);
        images.add(image);
    }

    /**
     * Remove given image from the album.
     *
     * @param image the image to add
     */
    public void removeImage(Image image) {
        image.removeAlbum(this);
        images.remove(image);
    }

    /**
     * Add given tag to this album
     *
     * @param tag the tag to add
     */
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    /**
     * Remove given tag to this album
     *
     * @param tag the tag to add
     */
    public void removeTag(Tag tag) {
        tags.remove(tag);
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
        Album that = (Album) o;
        return id.equals(that.id) &&
                title.equals(that.title) &&
                user.equals(that.user) &&
                Objects.equals(images, that.images) &&
                Objects.equals(description, that.description) &&
                createdAt.equals(that.createdAt) &&
                updatedAt.equals(that.updatedAt);
    }

    @Override
    public String toString() {
        String formattedImages = "";
        if (images != null)
            formattedImages = images.stream()
                    .map(Image::getId)
                    .collect(Collectors.toList())
                    .toString();

        String formattedTags = "";
        if (tags != null)
            formattedTags = tags.stream()
                    .map(Tag::getName)
                    .collect(Collectors.toList())
                    .toString();

        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", user=" + user +
                ", images=" + formattedImages +
                ", tags=" + formattedTags +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
