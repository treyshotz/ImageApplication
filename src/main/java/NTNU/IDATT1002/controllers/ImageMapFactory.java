package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.models.GeoLocation;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.service.TagService;
import NTNU.IDATT1002.utils.MetadataStringFormatter;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import javax.xml.bind.DatatypeConverter;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Class ImageMapFactory. Factory for map creation with markers for given images and default options.
 * Default center location is Copenhagen in order to center the full scale map onto a page.
 *
 * @author Eirik Steira
 */
public class ImageMapFactory {

    private GoogleMap map;
    private Logger logger = LoggerFactory.getLogger(ImageMapFactory.class);
    private Map<LatLong, Image> latLongImageMapping = new HashMap<>();
    
    public ImageMapFactory() {
    }

    /**
     * Create a map from given {@link GoogleMapView} with default options.
     *
     * @param mapView the map view to add the map to
     * @return the {@link GoogleMap} created to enable further customization
     */
    public GoogleMap createMap(GoogleMapView mapView) {
        MapOptions mapOptions = getMapOptions();
        map = mapView.createMap(mapOptions);
        logger.info("[x] Map created");
        return map;
    }

    /**
     * Create default {@link MapOptions} to be applied to a map. Extend this for further marker customizations.
     * The default center location is Copenhagen to get a look of the entire map when the zoom is set to fit the window.
     *
     * @return the default map options
     */
    private MapOptions getMapOptions() {
        LatLong copenhagen = new LatLong(55.676098, 12.568337);
        return new MapOptions()
                .center(copenhagen)
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(3);
    }

    /**
     * Create markers from given images.
     *
     * @param images the list of images
     * @return a list of markers created from the images
     */
    public List<Marker> createMarkers(List<Image> images) {
        List<LatLong> locations = getLatLongs(images);
        List<Marker> markers = getMarkers(locations);
        logger.info("[x] {} markers created", markers.size());
        return markers;
    }

    /**
     * Get latitude and longitude ({@link LatLong}) values for given images.
     *
     * @param images the list of images
     * @return a list of {@link LatLong}
     */
    private List<LatLong> getLatLongs(List<Image> images) {
        return images.stream()
            .filter(image -> image.getGeoLocation().hasLatLong())
            .map(image -> {
                LatLong latLong = getLatLong(image);
                latLongImageMapping.put(latLong, image);
                return latLong;
            }).collect(Collectors.toList());
    }

    /**
     * Get a {@link LatLong} from a single image.
     *
     * @param image the image holding the {@link GeoLocation}
     * @return the {@link LatLong} created
     */
    private LatLong getLatLong(Image image) {
        GeoLocation geoLocation = image.getGeoLocation();
        double latitude = Double.parseDouble(geoLocation.getLatitude());
        double longitude = Double.parseDouble(geoLocation.getLongitude());
        return new LatLong(latitude, longitude);
    }

    /**
     * Create {@link Marker}s for each location in given list of locations.
     *
     * @param locations the list containing the locations
     * @return the list of markers created
     */
    private List<Marker> getMarkers(List<LatLong> locations) {
        return locations.stream()
                .map(this::getMarker)
                .collect(Collectors.toList());
    }

    /**
     * Create {@link Marker} for given location with map zoom and center on click event.
     *
     * @param location the location of the marker
     * @return marker created
     */
    private Marker getMarker(LatLong location) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(location)
                .animation(Animation.DROP);

        logger.info("[x] Marker created for location: {}", location);
        Marker marker = new Marker(markerOptions);

        InfoWindow infoWindow = getInfoWindow(location);
        map.addUIEventHandler(marker, UIEventType.click, (JSObject obj) -> {
           map.setZoom(10);
           map.setCenter(location);
           infoWindow.open(map, marker);
        });

        return marker;
    }

    /**
     * Get {@link InfoWindow} with default options to display the
     * corresponding image data.
     *
     * @param location the location corresponding to an image
     * @return the {@link InfoWindow} created
     */
    private InfoWindow getInfoWindow(LatLong location) {
        Image image = latLongImageMapping.get(location);

        String username = image.getUser().getUsername();
        String tags = TagService.getTagsAsString(image.getTags());
        Date uploadedAt = image.getUploadedAt();
        String metadata = MetadataStringFormatter.format(image.getMetadata(), "<br/>");

        //Convert image bytes to base64 for displaying image as HTML
        byte[] imageBytes = image.getRawImage();
        String path = "data:image/png;base64," + DatatypeConverter.printBase64Binary(imageBytes);

        InfoWindowOptions infoWindowOptions = new InfoWindowOptions()
                .content("<h3>Id: " + image.getId() + "</h3>" +
                                 "<p><img src=" + path + " width=\"200\" height=\"200\"> </p>" +
                                 "<p><b>User:</b> " + username + "</p>" +
                                 "<p><b>Tags:</b> " + tags + "</p>" +
                                 "<p><b>Uploaded at:</b> " + uploadedAt + "</p>" +
                                 "<p><b>Metadata:</b> <br/>" + metadata + "</p>");

        return new InfoWindow(infoWindowOptions);
    }
}
