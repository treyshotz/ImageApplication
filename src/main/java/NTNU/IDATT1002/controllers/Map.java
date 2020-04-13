package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.service.AlbumService;
import NTNU.IDATT1002.service.ImageService;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import java.util.Optional;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controls the buttons and changeable elements on map.fxml,
 * a page where you can find images by location
 * @version 1.0 22.03.2020
 */
public class Map implements Initializable, MapComponentInitializedListener {
    public ImageView tbar_logo;
    public TextField tbar_search;
    public Button tbar_searchBtn;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;

    public TextField search;
    public Button searchBtn;
    public Button tbar_albums;

    @FXML
    private GoogleMapView mapView;
    private GoogleMap map;

    private ImageService imageService;
    private AlbumService albumService;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private static Logger logger = LoggerFactory.getLogger(Map.class);


    public Map() {
        EntityManager entityManager = App.ex.getEntityManager();
        imageService = new ImageService(entityManager);
        albumService = new AlbumService(entityManager);
    }

    /**
     * Register the api key for Google Maps API and listen for map initialization
     * in order to update the view once it has been initialized.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.setKey("AIzaSyB_ox5XC8zYBS__aezKumB-KSgKGUjcRx4");
        mapView.addMapInializedListener(this);
    }


    /**
     * Check is there is a current album in dataexchange first and uses images from album to display on map.
     * If none is found it proceeds to get all images available and display on map
     */
    @Override
    public void mapInitialized() {
        List<Image> albumImages = new ArrayList<>();
        Long currentAlbumId;

        if(App.ex.getChosenAlbumId() == null) {
            executorService.submit(fetchImages);

            fetchImages.setOnSucceeded(workerStateEvent -> {
                List<Image> allImages = fetchImages.getValue();
                ImageMapFactory.createMap(mapView, allImages);
            });
        }
        else {
            currentAlbumId = App.ex.getChosenAlbumId();
            Optional<Album> optionalAlbum = albumService.getAlbumById(currentAlbumId);
            if (optionalAlbum.isPresent()) {
                Album album = optionalAlbum.get();
                albumImages = album.getImages();
            }
            ImageMapFactory.createMap(mapView, albumImages);
        }
    }

    /**
     * Background task for fetching all images.
     */
    private Task<List<Image>> fetchImages = new Task<>() {
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
     * Change scene to Main page
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToMain(MouseEvent mouseEvent) throws IOException {
        App.ex.setChosenAlbumId(null);
        App.setRoot("main");
    }

    /**
     * Change scene to Search page. It reads the value of the search
     * field and if not empty it is passed to dataexchange
     * @param actionEvent
     * @throws IOException
     */
    public void switchToSearch(ActionEvent actionEvent) throws IOException {
        if (!tbar_search.getText().isEmpty()){
            App.ex.setSearchField(tbar_search.getText());
        }
        App.ex.setChosenAlbumId(null);
        App.setRoot("search");
    }

    /**
     * Change scene to Explore page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToExplore(ActionEvent actionEvent) throws IOException {
        App.ex.setChosenAlbumId(null);
        App.setRoot("explore");
    }

    /**
     * Change scene to Albums page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToAlbums(ActionEvent actionEvent) throws IOException {
        App.ex.setChosenAlbumId(null);
        App.setRoot("explore_albums");
    }

    /**
     * Change scene to Map page.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void switchToMap(ActionEvent actionEvent) throws IOException {
        App.ex.setChosenAlbumId(null);
        App.setRoot("map");
    }

    /**
     * Change scene to Upload page.
     *
     * @param actionEvent the mouse has done something
     * @throws IOException this page does not exist
     */
    public void switchToUpload(ActionEvent actionEvent) throws IOException {
        App.ex.setChosenAlbumId(null);
        App.setRoot("upload");
    }

    /**
     * Search for images on a specific place.
     *
     * @param actionEvent
     */
    public void MapSearch(ActionEvent actionEvent) {
        //TODO: Make method
    }
}

