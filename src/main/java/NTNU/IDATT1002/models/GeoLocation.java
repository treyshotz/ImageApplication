package NTNU.IDATT1002.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Creates table Geolocation
 */

@Entity
@Table(name = "geolocation")
public class GeoLocation {

    /**
     * Defines geolocationId, this may not be blank
     */
    @Id
    @GeneratedValue
    private Long geoLocationId;

    /**
     * One to one relation between geolocationId in table Geolocation
     * Joins column geolocation_id in metadata
     */
    @OneToOne(mappedBy = "geolocation", fetch = FetchType.LAZY)
    private Metadata metadata;

    @NotBlank (message = "Altitude may not be blank")
    private String latitude;

    @NotBlank (message = "Longitude may not be blank")
    private String longitude;

    public GeoLocation() {

    }
    /**
     * Creates constructor for geolocation that takes in each parameter
     *
     * @param latitude
     * @param longitude
     */
    public GeoLocation(String latitude, String longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getGeoLocationId() {
        return geoLocationId;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setGeoLocationId(Long geoLocationId) {
        this.geoLocationId = geoLocationId;
    }

    public void setLatitude(String altitude) {
        this.latitude = altitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
