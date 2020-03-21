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

    @NotBlank (message = "GeolocationId may not be blank")
    private Long geoLocationId;

    @OneToOne
    private Histogram histogram;

    @NotBlank (message = "HistogramId may not be blank")
    private Long histogramId;

    public Metadata(Long metadataId, Long geoLocationId, Long histogramId){
        this.metadataId = metadataId;
        this.geoLocationId = geoLocationId;
        this.histogramId = histogramId;
    }

    public Metadata(Metadata metadata){
        this(metadata.getMetadataId(),
                metadata.getGeoLocationId(),
                metadata.getHistogramId());
    }

    public Long getMetadataId() {
        return metadataId;
    }

    public Long getGeoLocationId() {
        return geoLocationId;
    }

    public Long getHistogramId() {
        return histogramId;
    }

    public void setGeoLocationId(Long geoLocationId) {
        this.geoLocationId = geoLocationId;
    }

    public void setMetadataId(Long metadataId) {
        this.metadataId = metadataId;
    }

    public void setHistogramId(Long histogramId) {
        this.histogramId = histogramId;
    }
}
