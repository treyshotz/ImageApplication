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
public class Image {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToMany
  private List<ImageAlbum> imageAlbums = new ArrayList<>();;

  @ManyToOne
  private User user;

  @Lob
  @NotNull
  @NotEmpty
  private byte[] rawImage;

  @OneToOne
  private Metadata metadata;

  @NotBlank
  private String path;

  @CreationTimestamp
  private Date uploadedAt;

  public Image() {
  }

  public Image(byte[] rawImage, ImageAlbum imageAlbum, User user, Metadata metadata, String path) {
    this.rawImage = rawImage;
    this.addImageAlbum(imageAlbum);
    this.user = user;
    this.metadata = metadata;
    this.path = path;
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

  public List<ImageAlbum> getImageAlbums() {
    return imageAlbums;
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
   * Add this image in the given image album.
   *
   * @param imageAlbum the image album to add to
   */
  public void addImageAlbum(ImageAlbum imageAlbum) {
    imageAlbums.add(imageAlbum);
  }

  /**
   * Remove this image from the given image.
   *
   * @param imageAlbum the image album to remove from
   */
  public void removeImageAlbum(ImageAlbum imageAlbum) {
    imageAlbums.remove(imageAlbum);
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
} 





