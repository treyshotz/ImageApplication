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
    @OneToOne
    @JoinColumn(name = "metadata_id")
    private Metadata metadata;

    @NotBlank (message = "Altitude may not be blank")
    private String altitude;

    @NotBlank (message = "Longitude may not be blank")
    private String longitude;

    /**
     * Creates constructor for geolocation that takes in each parameter
    *
     * @param altitude
     * @param longitude
     */
    public GeoLocation(String altitude, String longitude){
        this.altitude = altitude;
        this.longitude = longitude;
    }

    public Long getGeoLocationId() {
        return geoLocationId;
    }

    public String getAltitude() {
        return altitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setGeoLocationId(Long geoLocationId) {
        this.geoLocationId = geoLocationId;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
