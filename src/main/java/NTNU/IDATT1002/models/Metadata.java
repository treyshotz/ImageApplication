package NTNU.IDATT1002.models;


import java.util.ArrayList;
import javax.persistence.*;

/**
 * Creates Metadata table
 */

@Entity
@Table(name = "metadata")
public class Metadata {

    /**
     * Defines metadataId, may not be blank
     */
    @Id
    @GeneratedValue
    private Long metadataId;

    /**
     * One to one relation joining imageId
     * on image_id column in image
     */
    @OneToOne(mappedBy = "metadata", fetch = FetchType.LAZY)
    private Image image;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private GeoLocation geolocation;

    @Column(name = "camera")
    private String camera;

    @Column(name = "lens")
    private String lens;

    @Column(name = "aperture")
    private String aperture;

    @Column(name = "shutterspeed")
    private String shutterSpeed;

    @Column(name = "iso")
    private String ISO;

    @Column(name = "focallength")
    private String focalLength;

    @Column(name = "filetype")
    private String fileType;

    @Column(name = "photodate")
    private String photoDate;

    @Column(name = "filesize")
    private String fileSize;

    @Column(name = "filedimension")
    private String fileDimension;


    @Column(name = "allMetadata")
    private String allMetadata;

    public Metadata() {
    }

    public Metadata(Metadata metadata) {
        this.metadataId = metadata.getMetadataId();
        this.image = metadata.getImage();
        this.geolocation = metadata.getGeoLocation();
        this.camera = metadata.getCamera();
        this.lens = metadata.getLens();
        this.aperture = metadata.getAperture();
        this.shutterSpeed = metadata.getShutterSpeed();
        this.ISO = metadata.getISO();
        this.focalLength = metadata.getFocalLength();
        this.fileType = metadata.getFileType();
        this.photoDate = metadata.getPhotoDate();
        this.fileSize = metadata.getFileSize();
        this.fileDimension = metadata.getFileDimension();
    }


    public Long getMetadataId() {
        return metadataId;
    }

    public Image getImage() {
        return image;
    }

    public GeoLocation getGeoLocation() {
        return geolocation;
    }

    public String getCamera() {
        return camera;
    }

    public String getAllMetadata(){
        return allMetadata;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getLens() {
        return lens;
    }

    public void setLens(String lens) {
        this.lens = lens;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setGeoLocation(GeoLocation geolocation) {
        this.geolocation = geolocation;
    }

    public String getAperture() {
        return aperture;
    }

    public String getShutterSpeed() {
        return shutterSpeed;
    }

    public String getISO() {
        return ISO;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public String getFileType() {
        return fileType;
    }

    public String getPhotoDate() {
        return photoDate;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getFileDimension() {
        return fileDimension;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public void setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public void setISO(String ISO) {
        this.ISO = ISO;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setPhotoDate(String photoDate) {
        this.photoDate = photoDate;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setFileDimension(String fileDimension) {
        this.fileDimension = fileDimension;
    }

    public void setAllMetadata(String allMetadata){
        this.allMetadata = allMetadata;
    }

    @Override
    public String toString() {
      /*  String allData = " ";
        for (int i = 0; i < allMetadataList.size(); i++){
            allData += allMetadataList.get(i);
        }*/

        return "Metadata{" +
            "\nmetadataId=" + metadataId +
            "\ncamera='" + camera + '\'' +
            "\nlens='" + lens + '\'' +
            "\naperture='" + aperture + '\'' +
            "\nshutterSpeed='" + shutterSpeed + '\'' +
            "\nISO='" + ISO + '\'' +
            "\nfocalLength='" + focalLength + '\'' +
            "\nfileType='" + fileType + '\'' +
            "\nphotoDate='" + photoDate + '\'' +
            "\nfileSize='" + fileSize + '\'' +
            "\nfileDimension='" + fileDimension + '\'' +
            '}'; /* +
            " \n\n All the extra stuff that I dont know what to do with yet" + allData;*/

    }
}
