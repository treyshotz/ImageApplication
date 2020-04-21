package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.service.AlbumService;
import NTNU.IDATT1002.service.ImageService;
import com.google.maps.*;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.TextFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Controls the buttons and changeable elements on map.fxml,
 * a page where you can find images by location
 *
 * @version 1.0 22.03.2020
 */
public class Map extends NavBarController implements Initializable, MapComponentInitializedListener {

    @FXML
    public TextField search;
    @FXML
    public Button searchBtn;
    @FXML
    private GoogleMapView mapView;
    @FXML
    private TextField addressTextField;

    private List<String> autoCompletions = new ArrayList<>();
    private GoogleMap googleMap;
    private GeoApiContext geoApiContext;
    private StringProperty address = new SimpleStringProperty();
    private ImageService imageService;
    private AlbumService albumService;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private static Logger logger = LoggerFactory.getLogger(Map.class);

    /**
     * Tell {@link DataExchange} that map page is visited.
     */
    public Map() {
        App.ex.newPage("map");
        EntityManager entityManager = App.ex.getEntityManager();
        imageService = new ImageService(entityManager);
        albumService = new AlbumService(entityManager);
    }

    /**
     * Initialize {@link GoogleMapView} and {@link GeoApiContext} with required API key.
     * Also add listener for map initialization and bind the address text field to a {@link SimpleStringProperty}.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.setKey(App.ex.getApiKey());
        mapView.addMapInializedListener(this);
        address.bind(addressTextField.textProperty());

        geoApiContext = App.ex.getGeoApiContext();
    }

    /**
     * Create map an fetch images from the appropriate task and add individual markers for images.
     */
    @Override
    public void mapInitialized() {
        ImageMapFactory imageMapFactory = new ImageMapFactory();
        googleMap = imageMapFactory.createMap(mapView);
        
        Task<List<Image>> fetchImagesTask = getImageListTask();
        executorService.submit(fetchImagesTask);

        fetchImagesTask.setOnSucceeded(workerStateEvent -> {
            List<Image> images = fetchImagesTask.getValue();
            List<Marker> markers = imageMapFactory.createMarkers(images);
            googleMap.addMarkers(markers);
        });
    }

    /**
     * Decide which images to fetch. Fetch album images if user is trying to view album, else all images.
     *
     * @return a task which fetches images
     */
    private Task<List<Image>> getImageListTask() {
        if (App.ex.getChosenAlbumId() == null)
            return fetchAllImages;

        return fetchAlbumImages;
    }

    /**
     * Background task for fetching all images.
     */
    private Task<List<Image>> fetchAllImages = new Task<>() {
        @Override
        protected List<Image> call() {
            try {
                return imageService.getAllImages();
            } catch (Exception e) {
                logger.error("[x] Failed to fetch images", e);
            }
            return new ArrayList<>();
        }
    };

    /**
     * Background task for fetching all images in the current chosen album
     * in {@link DataExchange}.
     */
    private Task<List<Image>> fetchAlbumImages =  new Task<>() {
        @Override
        protected List<Image> call() {
            try {
                Optional<Album> optionalAlbum = albumService.getAlbumById(App.ex.getChosenAlbumId());

                if (optionalAlbum.isPresent())
                    return optionalAlbum.get().getImages();

            } catch (Exception e) {
                logger.error("[x] Failed to fetch images", e);
            }
            return new ArrayList<>();
        }
    };

    /**
     * Query for autocomplete predictions and bind them to the corresponding text field.
     *
     * @param keyEvent
     */
    public void queryAutocomplete(KeyEvent keyEvent) {
        QueryAutocompleteRequest queryAutocompleteRequest = PlacesApi.queryAutocomplete(geoApiContext, address.get());

        queryAutocompleteRequest.setCallback(new PendingResult.Callback<>() {
            @Override
            public void onResult(AutocompletePrediction[] autocompletePredictions) {
                autoCompletions = Stream.of(autocompletePredictions)
                        .map(prediction -> prediction.description)
                        .collect(Collectors.toList());

                TextFields.bindAutoCompletion(addressTextField,  autoCompletions);
            }

            @Override
            public void onFailure(Throwable e) {
                logger.error("[x] Failed to fetch predictions. Query='{}'", address.get(), e);
            }
        });
    }

    /**
     * Search a Geocoding address with the current input in the address search text field.
     * Centers the map on the first result.
     *
     * @param event
     */
    public void searchGeocodingAddress(ActionEvent event) {
        GeocodingApiRequest geoCodingAddressRequest = GeocodingApi.newRequest(geoApiContext).address(address.get());

        geoCodingAddressRequest.setCallback(new PendingResult.Callback<>() {
            @Override
            public void onResult(GeocodingResult[] result) {
                LatLng firstLatLngFound = result[0].geometry.location;
                logger.info("[x] Geocoding result found: {}", result[0].formattedAddress);

                Platform.runLater(() -> centerMapOnLocation(firstLatLngFound));
            }

            @Override
            public void onFailure(Throwable e) {
                logger.error("[x] Failed to fetch Geocoding locations", e);
                if (e instanceof ArrayIndexOutOfBoundsException)
                    Platform.runLater(() -> showInfoAlert("No locations found"));
                else
                    Platform.runLater(() -> showInfoAlert("Oops, an error occurred while searching."));
            }
        });
    }

    /**
     * Center map on given {@link LatLng} and set appropriate zoom level.
     *
     * @param latLng the location to center on
     */
    private void centerMapOnLocation(LatLng latLng) {
        LatLong newLatLong = new LatLong(latLng.lat, latLng.lng);
        googleMap.setCenter(newLatLong);
        googleMap.setZoom(10);
    }

    /**
     * Show an info alert to the user with given message.
     *
     * @param message the message containing info to the user
     */
    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }
}

