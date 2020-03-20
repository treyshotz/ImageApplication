package NTNU.IDATT1002.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Creates table Geolocation
 */

@Entity
@Table(name = "Geolocation")
public class GeoLocation {

    /**
     * Defines geolocationId, this may not be blank
     */

    @Id @NotBlank(message = "Geolocation-Id may not be blank")
    private int geoLocationId;

    /**
     * One to one relation between geolocationId in table Geolocation
     * Joins column geolocation_id in metadata
     */

    @OneToOne
    @JoinColumn(name = "metadata_id")
    private Metadata metadata;

    @NotBlank (message = "Altitude may not be blank")
    private String altitude;

    @NotBlank (message = "Longditude may not be blank")
    private String longditude;

    /**
     * Creates constructor for geolocation that takes in each parameter
     * @param geoLocationId
     * @param altitude
     * @param longditude
     */

    public GeoLocation(int geoLocationId, String altitude, String longditude){
        this.geoLocationId = geoLocationId;
        this.altitude = altitude;
        this.longditude = longditude;
    }

    /**
     *  Another constructor that takes in one geolocation, and uses get-methods to define parameters
     * @param geoLocation
     */

    public GeoLocation(GeoLocation geoLocation){
        this(geoLocation.getGeoLocationId(),
               geoLocation.getAltitude(),
                geoLocation.getLongditude());
    }

    public int getGeoLocationId() {
        return geoLocationId;
    }

    public String getAltitude() {
        return altitude;
    }

    public String getLongditude() {
        return longditude;
    }

    public void setGeoLocationId(int geoLocationId) {
        this.geoLocationId = geoLocationId;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public void setLongditude(String longditude) {
        this.longditude = longditude;
    }
}
