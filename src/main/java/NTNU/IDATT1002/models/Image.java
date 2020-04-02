package NTNU.IDATT1002.models;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "image")
@NamedQueries({
        @NamedQuery(name="Image.findAllByUsername",
                query = "SELECT ia from Image ia WHERE ia.user.username = :username")
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

  public List<Album> getAlbums() {
    return albums;
  }

  public void addTags(ArrayList<Tag> tags) {
    tags.addAll(tags);
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

  public String getPath() {
    return path;
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
    return "Image{" +
            "id=" + id +
            ", albums=" + albums +
            ", tags=" + tags +
            ", user=" + user +
            ", metadata=" + metadata +
            ", path='" + path + '\'' +
            ", uploadedAt=" + uploadedAt +
            '}';
  }
}





