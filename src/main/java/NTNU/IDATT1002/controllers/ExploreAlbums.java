package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.controllers.components.AlbumHBox;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.service.AlbumService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
import java.util.stream.Stream;

/**
 * Controls the buttons and changeable elements on explore_albums.fxml,
 * a page where you explore albums
 * @version 1.1 04.04.2020
 */
public class ExploreAlbums extends NavBarController implements Initializable {

    public ScrollPane scrollpane;
    public Text albumAmount;
    public ChoiceBox sortedByChoicebox;
    public Button createAlbumButton;

    @FXML
    public VBox pageRootContainer;
    @FXML
    private HBox progressBarContainer;
    @FXML
    private ProgressBar progressBar;
    @FXML
    public VBox rootAlbumsContainer;
    @FXML
    private Text albumsPlaceholder;

    private AlbumService albumService;
    private ObservableList<Album> listOfAlbums;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private static Logger logger = LoggerFactory.getLogger(ExploreAlbums.class);

    public ExploreAlbums() {
        EntityManager entityManager = App.ex.getEntityManager();
        albumService = new AlbumService(entityManager);
    }

    /**
     * Initialize page and load albums separately with a single image displayed. 
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        executorService.submit(fetchAlbums);

        fetchAlbums.setOnSucceeded(workerStateEvent -> {
            listOfAlbums = FXCollections.observableArrayList(fetchAlbums.getValue());
            VBox albumVBox = createAlbumVBox(listOfAlbums);
            albumVBox.setSpacing(20);
            Platform.runLater(this::finalizeProgress);

            rootAlbumsContainer.setMinHeight(albumVBox.getMinHeight());
            rootAlbumsContainer.getChildren().remove(albumsPlaceholder);
            rootAlbumsContainer.getChildren().add(albumVBox);
        });

    }

    /**
     * Background task for fetching albums 
     */
    private Task<List<Album>> fetchAlbums = new Task<>() {
        @Override
        protected List<Album> call() {
            try {
                return albumService.getAllAlbums();
            } catch (Exception e) {
                logger.error("[x] Failed to fetch albums", e);
            }
            return new ArrayList<>();
        }
    };

    /**
     * Set the progressbar to finished and remove it from the root container.
     */
    private void finalizeProgress() {
        progressBar.setProgress(1);
        pageRootContainer.getChildren().remove(progressBarContainer);
    }

    /**
     * Create the root container for displaying albums and add the retrieved albums.
     * It is currently a limit at 50 albums per page.
     * Load each corresponding images in a separate background task and add them when ready.
     *
     * @param listOfAlbums the albums to add
     * @return the VBox containing styled album HBoxes and ImageViews
     */
    public VBox createAlbumVBox(ObservableList<Album> listOfAlbums){
        VBox albumVBox = new VBox();
        int maxPerPage = Math.min(listOfAlbums.size(), 50);
        
        for (int i = 0; i < maxPerPage; i++) {
            Album album = listOfAlbums.get(i);
            AlbumHBox albumHBox = new AlbumHBox(album);

            Task<List<NTNU.IDATT1002.models.Image>> fetchImagesTask = fetchImagesFrom(album);
            executorService.submit(fetchImagesTask);

            fetchImagesTask.setOnSucceeded(workerStateEvent -> {
                NTNU.IDATT1002.models.Image previewImage = FXCollections.observableArrayList(
                        fetchImagesTask.getValue())
                        .get(0);

                albumHBox.replaceAlbumImageViewWith(previewImage);
                setSwitchToAlbumOnAlbumComponents(albumHBox);
            });

            albumVBox.getChildren().add(albumHBox);
        }

        return albumVBox;
    }

    /**
     * Fetch the images from given album in a background task.
     *
     * @param album the album whose images to fetch
     * @return task to return a list of fetched images
     */
    private Task<List<NTNU.IDATT1002.models.Image>> fetchImagesFrom(Album album) {
        return new Task<>() {
            @Override
            protected List<NTNU.IDATT1002.models.Image> call() {
                try {
                    return album.getImages();
                } catch (Exception e) {
                    logger.error("[x] Failed to fetch images for album {}", album, e);
                }
                return new ArrayList<>();
            }
        };
    }

    /**
     * Add {@link MouseEvent} event handler to appropriate components in given {@link AlbumHBox}.
     *
     * @param albumHBox the composite component.
     */
    private void setSwitchToAlbumOnAlbumComponents(AlbumHBox albumHBox) {
        EventHandler<MouseEvent> mouseEventEventHandler = mouseEvent -> {
            try {
                switchToViewAlbum(mouseEvent);
            } catch (IOException ex) {
                logger.error("[x] Failed to switch to Album View", ex);
            }
        };

        Stream.of(albumHBox.getAlbumTitleHBox(),
                  albumHBox.getAlbumImageView())
                .forEach(node -> albumHBox.addOnMouseClickedEventHandlerToComponent(node, mouseEventEventHandler));
    }

    /**
     * Change scene to Create Album page.
     *
     * @param actionEvent the mouse has done something
     * @throws IOException this page does not exist
     */
    public void switchToCreateAlbum(ActionEvent actionEvent) throws IOException {
        App.setRoot("create_album");
    }

    /**
     * Update content to previous "page".
     *
     * @param actionEvent
     * @throws IOException
     */
    public void switchToPrevious(ActionEvent actionEvent) throws IOException {
        //TODO: Make method that updates content
    }

    /**
     * Update content to next "page".
     *
     * @param actionEvent
     * @throws IOException
     */
    public void switchToNext(ActionEvent actionEvent) throws IOException {
        //TODO: Make method that updates content
    }

    /**
     * Open specific albums. It takes a clicked element within an album container
     * and finds the fx:id of the main parent, (who's id is the same as the album in the database), and
     * passes the value to Data Exchange so that Image View will know which album was clicked.
     *
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToViewAlbum(MouseEvent mouseEvent) throws IOException {
        boolean albumIdFound = false;
        long albumId = 0;
        Node node = (Node) mouseEvent.getSource();
        while (!albumIdFound){
            if (node.getId() != null){
                albumId = Long.parseLong(node.getId());
                albumIdFound = true;
            }
            node = node.getParent();
        }

        if (albumId != 0) {
            App.ex.setChosenAlbumId(albumId);
            App.setRoot("view_album");
        }
    }

}
