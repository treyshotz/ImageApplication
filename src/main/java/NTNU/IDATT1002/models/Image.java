package NTNU.IDATT1002.models;


import javafx.scene.layout.VBox;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "image")
@NamedQueries({
        @NamedQuery(name="Image.findAllByUsername",
                query = "SELECT ia from Image ia WHERE ia.user.username = :username"),
        @NamedQuery(name="Image.findByTags",
                query = "SELECT im from Image im "
                        + "join im.tags tg "
                        + "where tg.name = :name"),
        @NamedQuery(name="Image.findByQueryString",
                query = "SELECT img " +
                        "FROM Image img " +
                        "WHERE img.user.username = :query " +
                        "OR img.title = :query " +
                        "OR img IN (SELECT i FROM Image i INNER JOIN i.tags tag WHERE tag.name = :query) ")
})
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Album> albums = new ArrayList<>();

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Tag> tags = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @Lob
  @NotNull
  @NotEmpty
  private byte[] rawImage;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Metadata metadata;

  @NotBlank
  private String path;

  @Column(name = "title", length = 50)
  private String title;

  @CreationTimestamp
  private Date uploadedAt;

  public Image() {
  }

  public Image(byte[] rawImage, Album album, User user, Metadata metadata, String path) {
    this.rawImage = rawImage;
    this.addAlbum(album);
    this.user = user;
    this.metadata = metadata;
    this.path = path;
    this.tags = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public byte[] getRawImage() {
    return rawImage;
  }

  public void setRawImage(byte[] rawImage) {
    this.rawImage = rawImage;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setMetadata(Metadata metadata) {
    this.metadata = metadata;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setTitle(String title){
    this.title = title;
  }

  public List<Album> getAlbums() {
    return albums;
  }

  public void addTags(ArrayList<Tag> tags) {
    this.tags.addAll(tags);
  }

  public void addTag(Tag tag){
      tags.add(tag);
  }

    public List<Tag> getTags() {
        return tags;
    }

    public Metadata getMetadata() {
    return metadata;
  }

  public Date getUploadedAt() {
    return uploadedAt;
  }

  public String getTitle(){
    return title;
  }

  public User getUser() {
    return user;
  }

  /**
   * Add this image in the given album.
   *
   * @param album the album to add to
   */
  public void addAlbum(Album album) {
    albums.add(album);
  }

  /**
   * Remove this image from the given image.
   *
   * @param album the album to remove from
   */
  public void removeAlbum(Album album) {
    albums.remove(album);
  }

  /**
   * Get Geo Location related to this image.
   *
   * @return the location the image was taken
   */
  public GeoLocation getGeoLocation() {
    if (metadata == null)
      return new GeoLocation("0","0");

    return metadata.getGeoLocation();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Image)) {
      return false;
    }
    Image that = (Image) o;
    return getId().equals(that.getId());
  }

  @Override
  public String toString() {
    String formattedTags = "";
    if (tags != null)
        formattedTags = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList())
                .toString();

    String formattedAlbums = "";
    if (albums != null)
      formattedAlbums = albums.stream()
              .map(Album::getId)
              .collect(Collectors.toList())
              .toString();

    return "Image{" +
            "id=" + id +
            ", albums=" + formattedAlbums +
            ", tags=" + formattedTags +
            ", user=" + user.getUsername() +
            ", metadata=" + metadata +
            ", path='" + path + '\'' +
            ", uploadedAt=" + uploadedAt +
            '}';
  }
}





