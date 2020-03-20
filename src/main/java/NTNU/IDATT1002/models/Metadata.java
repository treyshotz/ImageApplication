package NTNU.IDATT1002.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Creates Metadata table
 */

@Entity
@Table(name = "Metadata")
public class Metadata {

    /**
     * Defines metadataId, may not be blank
     */

    @Id @NotBlank(message = "Metadata-Id may not be blank")
    private int metadataId;

    /**
     * One to one relation joining imageId
     * on image_id column in image
     */

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @NotBlank (message = "GeolocationId may not be blank")
    private int geoLocationId;

    @NotBlank (message = "HistogramId may not be blank")
    private int histogramId;

    public Metadata(int metadataId, int geoLocationId, int histogramId){
        this.metadataId = metadataId;
        this.geoLocationId = geoLocationId;
        this.histogramId = histogramId;
    }

    public Metadata(Metadata metadata){
        this(metadata.getMetadataId(),
                metadata.getGeoLocationId(),
                metadata.getHistogramId());
    }

    public int getMetadataId() {
        return metadataId;
    }

    public int getGeoLocationId() {
        return geoLocationId;
    }

    public int getHistogramId() {
        return histogramId;
    }

    public void setGeoLocationId(int geoLocationId) {
        this.geoLocationId = geoLocationId;
    }

    public void setMetadataId(int metadataId) {
        this.metadataId = metadataId;
    }

    public void setHistogramId(int histogramId) {
        this.histogramId = histogramId;
    }
}
