package NTNU.IDATT1002.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = "image")
public class Image {  


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToMany
  private List<ImageAlbum> imageAlbums = new ArrayList<>();;


  @NotBlank
  private long imageID;

  @NotBlank
  private long albumID;

  @NotBlank
  private long metaDataID;

  @NotBlank
  @CreationTimestamp
  private Date uploadAt;

  @NotBlank
  private String path;

  public Image() {
  }

  public Image(long imageID, long albumID, long metaDataId, Date uploadAt, String path) {
    this.imageID = imageID;
    this.albumID = albumID;
    this.metaDataID = metaDataId;
    this.uploadAt = uploadAt;
    this.path = path;
  }

  public Image(Image image) {
    this(image.getImageID(), image.getAlbumID(), image.getMetaDataID(), image.getUploadAt(), image.getPath());
  }

  public void setImageID(int imageID) {
    this.imageID = imageID;
  }

  public void setAlbumID(int albumID) {
    this.albumID = albumID;
  }

  public void setMetaDataID(int metaDataID) {
    this.metaDataID = metaDataID;
  }

  public void setUploadAt(Date uploadAt) {
    this.uploadAt = uploadAt;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public long getImageID() {
    return imageID;
  }

  public long getAlbumID() {
    return albumID;
  }

  public long getMetaDataID() {
    return metaDataID;
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
    return getImageID() == that.getImageID() &&
        getAlbumID() == that.getAlbumID();
  }
}





