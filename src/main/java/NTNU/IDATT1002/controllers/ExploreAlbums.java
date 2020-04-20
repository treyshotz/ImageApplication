package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.controllers.components.exploreAlbums.AlbumHBox;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Image;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
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
import java.util.Optional;
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
            VBox albums = computeRootAlbumsContainerChildren(listOfAlbums);

            replaceRootPlaceholderWith(albums);
            Platform.runLater(this::finalizeProgress);
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
     * Replace loading placeholder with the real albums.
     *
     * @param albums the {@link VBox} containing the albums
     */
    private void replaceRootPlaceholderWith(VBox albums) {
        rootAlbumsContainer.setSpacing(20);
        rootAlbumsContainer.setMinHeight(albums.getMinHeight());
        rootAlbumsContainer.getChildren().remove(albumsPlaceholder);
        rootAlbumsContainer.getChildren().addAll(albums);
    }

    /**
     * Set the progressbar to finished and remove it from the root container.
     */
    private void finalizeProgress() {
        pageRootContainer.getChildren().remove(progressBarContainer);
    }

    /**
     * Create a {@link VBox} of album {@link HBox} children to add to a root container.
     * It is currently a limit at 50 albums per page.
     * Load each corresponding preview images in a separate background task and add them when ready.
     *
     * @param listOfAlbums the albums to add
     * @return the VBox containing album containers
     */
    public VBox computeRootAlbumsContainerChildren(ObservableList<Album> listOfAlbums){
        int maxPerPage = Math.min(listOfAlbums.size(), 50);
        List<HBox> albumHBoxes = new ArrayList<>();

        for (int i = 0; i < maxPerPage; i++) {
            Album album = listOfAlbums.get(i);
            AlbumHBox albumHBox = new AlbumHBox(album);

            Task<Optional<Image>> fetchPreviewImageTask = fetchPreviewImageFrom(album);
            executorService.submit(fetchPreviewImageTask);

            fetchPreviewImageTask.setOnSucceeded(event -> {
                Optional<Image> previewImage = fetchPreviewImageTask.getValue();

                previewImage.ifPresent(image -> {
                    albumHBox.replaceAlbumImageViewWith(image);
                    setSwitchToAlbumOnAlbumComponents(albumHBox);
                });
            });

            albumHBoxes.add(albumHBox);
        }

        VBox container = new VBox();
        container.getChildren().addAll(albumHBoxes);
        return container;
    }

    /**
     * Fetch a single image preview from given album in a background task.
     *
     * @param album the album whose images to fetch
     * @return task to return a list of fetched images
     */
    private Task<Optional<Image>> fetchPreviewImageFrom(Album album) {
        return new Task<>() {
            @Override
            protected Optional<Image> call() {
                try {
                    return albumService.findPreviewImage(album);
                } catch (Exception e) {
                    logger.error("[x] Failed to fetch preview image for album {}", album, e);
                }

                return Optional.empty();
            }

            @Override
            protected void cancelled() {
                logger.error("Finding preview image for album task cancelled. Album: {}", album);
            }

            @Override
            protected void failed() {
                logger.error("Finding preview image task for album failed. Album: {}", album);
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
