package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.models.Image;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Class ImageMapFactory. Factory for map creation with markers for given images and default options.
 * Default center location is Berlin in order to center the full scale map onto a page.
 *
 * @author Eirik Steira
 */
public class ImageMapFactory {

    private static Logger logger = LoggerFactory.getLogger(ImageMapFactory.class);

    private ImageMapFactory() {}

    /**
     * Create a map from given {@link GoogleMapView} with default options and markers for images in given list.
     *
     * @param googleMapView the map view to add the map to
     * @param images the list of images to place on the map
     * @return the {@link GoogleMap} created to enable further customization
     */
    public static GoogleMap createMap(GoogleMapView googleMapView, List<Image> images) {
        List<LatLong> locations = getLatLongs(images);

        MapOptions mapOptions = getMapOptions();
        GoogleMap googleMap = googleMapView.createMap(mapOptions);
        logger.info("[x] Google map created with {} locations", locations.size());

        List<Marker> markers = getMarkers(locations);
        googleMap.addMarkers(markers);
        logger.info("[x] {} markers added", markers.size());

        return googleMap;
    }


    /**
     * Get latitude and longitude ({@link LatLong}) values for given images.
     *
     * @param images the list of images
     * @return a list of {@link LatLong}
     */
    private static List<LatLong> getLatLongs(List<Image> images) {
        return images.stream()
                .map(Image::getGeoLocation)
                .map(geoLocation -> {
                    double latitude = Double.parseDouble(geoLocation.getLatitude());
                    double longitude = Double.parseDouble(geoLocation.getLongitude());
                    return new LatLong(latitude, longitude);
                })
                .collect(Collectors.toList());
    }

    /**
     * Create default {@link MapOptions} to be applied to a map. Extend this for further marker customizations.
     * The default center location is Berlin to get a look of the entire map when the zoom is set to fit the window.
     *
     * @return the default map options
     */
    private static MapOptions getMapOptions() {
        LatLong berlin = new LatLong(52.520008, 13.404954);
        return new MapOptions()
                .center(berlin)
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(3);
    }

    /**
     * Create {@link Marker}s for each location in given list of locations.
     *
     * @param locations the list containing the locations
     * @return the list of markers created
     */
    private static List<Marker> getMarkers(List<LatLong> locations) {
        return locations.stream()
                .map(location -> {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(location);
                    logger.info("[x] Marker created for location: {}", location);
                    return new Marker(markerOptions);
                })
                .collect(Collectors.toList());
    }
}
