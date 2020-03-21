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
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToMany
  private List<ImageAlbum> imageAlbums = new ArrayList<>();;

  @Lob
  @NotNull
  @NotEmpty
  private byte[] image;

  @NotBlank
  private Long albumId;

  @NotBlank
  private Long metaDataId;

  @NotBlank
  @CreationTimestamp
  private Date uploadAt;

  @NotBlank
  private String path;

  public Image() {
  }

  public Image(byte[] image, Long albumId, Long metaDataId, Date uploadAt, String path) {
    this.image = image;
    this.albumId = albumId;
    this.metaDataId = metaDataId;
    this.uploadAt = uploadAt;
    this.path = path;
  }

  public Image(Image image) {
    this(image.getImage(), image.getAlbumId(), image.getMetaDataId(), image.getUploadAt(), image.getPath());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public void setAlbumId(Long albumId) {
    this.albumId = albumId;
  }

  public void setMetaDataId(Long metaDataId) {
    this.metaDataId = metaDataId;
  }

  public void setUploadAt(Date uploadAt) {
    this.uploadAt = uploadAt;
  }

  public void setPath(String path) {
    this.path = path;
  }


  public Long getAlbumId() {
    return albumId;
  }

  public Long getMetaDataId() {
    return metaDataId;
  }

  public Date getUploadAt() {
    return uploadAt;
  }

  public String getPath() {
    return path;
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





