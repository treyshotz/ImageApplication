package NTNU.IDATT1002.models;


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
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToOne
    private GeoLocation geoLocation;

    @OneToOne
    private Histogram histogram;

    public Metadata() {
    }

    public Metadata(Image image, GeoLocation geoLocation, Histogram histogram) {
        this.image = image;
        this.geoLocation = geoLocation;
        this.histogram = histogram;
    }


    public Long getMetadataId() {
        return metadataId;
    }

    public Image getImage() {
        return image;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public Histogram getHistogram() {
        return histogram;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public void setHistogram(Histogram histogram) {
        this.histogram = histogram;
    }
}
