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
    @OneToOne(mappedBy = "metadata")
    @MapsId
    private Image image;

    @OneToOne(cascade = CascadeType.ALL)
    private GeoLocation geolocation;

    @OneToOne(cascade = CascadeType.ALL)
    private Histogram histogram;

    public Metadata() {
    }

    public Metadata(Image image, GeoLocation geoLocation, Histogram histogram) {
        this.image = image;
        this.geolocation = geolocation;
        this.histogram = histogram;
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

    public Histogram getHistogram() {
        return histogram;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setGeoLocation(GeoLocation geolocation) {
        this.geolocation = geolocation;
    }

    public void setHistogram(Histogram histogram) {
        this.histogram = histogram;
    }
}
