package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.controllers.components.exploreAlbums.AlbumHBox;
import NTNU.IDATT1002.controllers.components.exploreAlbums.ImageHBox;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.service.AlbumService;
import NTNU.IDATT1002.service.ImageService;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Controls the buttons and changeable elements on search.fxml,
 * a page where you can search for images
 *
 * @version 1.0 22.03.2020
 */
public class Search extends NavBarController implements Initializable {

    @FXML
    public Text searchWord;
    @FXML
    public Text amountTotal;
    @FXML
    public Text amountAlbums;
    @FXML
    public Text amountImages;
    @FXML
    public VBox rootAlbumsContainer;
    @FXML
    public VBox rootImagesContainer;
    @FXML
    public Text albumsPlaceholder;
    @FXML
    public Text imagesPlaceholder;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public HBox progressBarContainer;
    @FXML
    public VBox pageRootContainer;
    @FXML
    public TabPane tabContainer;
    @FXML
    public Tab albumsTab;
    @FXML
    public Tab imagesTab;
    @FXML
    public Button previousBtn;
    @FXML
    public Button nextBtn;

    private static final int ELEMENTS_PER_PAGE = 25;
    private int imageStart = 0;
    private int imageEnd = ELEMENTS_PER_PAGE;
    private int albumStart = 0;
    private int albumEnd = ELEMENTS_PER_PAGE;

    private String searchTextField;

    private ObservableList<Album> listOfAlbums;
    private ObservableList<NTNU.IDATT1002.models.Image> listOfImages;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private static Logger logger = LoggerFactory.getLogger(Search.class);

    /**
     * Tell {@link DataExchange} that search page was visited.
     * Display search word.
     */
    public Search(){
        App.ex.newPage("search");
        searchTextField = App.ex.getSearchField();
    }

    /**
     * Generates content based on the search word. Searches both images and albums.
     * When the tasks is done the content is displayed as well as the number of each result type.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!searchTextField.isEmpty()){
            searchWord.setText(searchTextField);
            nextBtn.setDisable(true);
            previousBtn.setDisable(true);
            executorService.submit(fetchImages);

            fetchImages.setOnSucceeded(workerStateEvent -> {
                listOfImages = FXCollections.observableArrayList(fetchImages.getValue());
                amountImages.setText(String.valueOf(listOfImages.size()));

                VBox images = computeRootImagesContainerChildren(listOfImages, imageStart, imageEnd);
                replaceRootImagesWith(images);

                Platform.runLater(() -> {
                    if (fetchAlbums.isDone()){
                        amountTotal.setText(String.valueOf(listOfImages.size() + listOfAlbums.size()));
                        addTabListeners();
                        this.finalizeProgress();
                    }
                });
            });

            fetchImages.setOnFailed(workerStateEvent -> {
                logger.error("Failed to fetch images", workerStateEvent.getSource().getException());
            });

            executorService.submit(fetchAlbums);

            fetchAlbums.setOnSucceeded(workerStateEvent -> {
                listOfAlbums = FXCollections.observableArrayList(fetchAlbums.getValue());
                amountAlbums.setText(String.valueOf(listOfAlbums.size()));

                VBox albums = computeRootAlbumsContainerChildren(listOfAlbums, albumStart, albumEnd);
                replaceRootAlbumsWith(albums);

                newPageState(albumStart, albumEnd, listOfAlbums.size());

                Platform.runLater(() -> {
                    if (fetchImages.isDone()){
                        amountTotal.setText(String.valueOf(listOfImages.size() + listOfAlbums.size()));
                        addTabListeners();
                        this.finalizeProgress();
                    }
                });
            });

            fetchAlbums.setOnFailed(workerStateEvent -> {
                logger.error("Failed to fetch Albums", workerStateEvent.getSource().getException());
            });
        }
    }

    /**
     * Method that updates next and previous buttons disable-properties based on the active tab.
     * If albums tab is active and it is not possible to go to next page due to too few search results,
     * the button is disabled.
     */
    public void addTabListeners(){
        //If image tab is selected/unselected
        imagesTab.setOnSelectionChanged(event -> {
            if (imagesTab.isSelected()) {
                newPageState(imageStart, imageEnd, listOfImages.size());
            } else {
                newPageState(albumStart, albumEnd, listOfAlbums.size());
            }
        });
    }

    /**
     * Find out if it is possible to go to next or previous on active tab
     * @param start active tabs start value
     * @param end active tabs end value
     * @param size size of search results
     */
    private void newPageState(int start, int end, int size) {
            if (start == 0){
                previousBtn.setDisable(true);
            }
            else {
                previousBtn.setDisable(false);
            }
            if (end >= size){
                nextBtn.setDisable(true);
            }
            else {
                nextBtn.setDisable(false);
            }
    }

    /**
     * Background task for fetching images
     */
    private Task<List<Image>> fetchImages = new Task<>() {
        @Override
        protected List<Image> call() {
            try {
                return new ImageService(App.ex.getEntityManager()).searchResult(searchTextField);
            } catch (Exception e) {
                logger.error("[x] Failed to fetch albums", e);
            }
            return new ArrayList<>();
        }

        @Override
        protected void cancelled() {
            logger.error("Finding images task was cancelled.",
                    this.getException());;
        }

        @Override
        protected void failed() {
            logger.error("Finding images task failed.",
                    this.getException());;
        }
    };

    /**
     * Background task for fetching albums
     */
    private Task<List<Album>> fetchAlbums = new Task<>() {
        @Override
        protected List<Album> call() {
            try {
                return new AlbumService(App.ex.getEntityManager()).searchResult(searchTextField);
            } catch (Exception e) {
                logger.error("[x] Failed to fetch albums", e);
            }
            return new ArrayList<>();
        }

        @Override
        protected void cancelled() {
            logger.error("Finding albums task was cancelled.",
                    this.getException());;
        }

        @Override
        protected void failed() {
            logger.error("Finding albums task failed.",
                    this.getException());;
        }
    };


    /**
     * Updates the content in the album tab content container.
     *
     * @param albums the {@link VBox} containing the albums
     */
    private void replaceRootAlbumsWith(VBox albums) {
        rootAlbumsContainer.getChildren().clear();
        rootAlbumsContainer.setMinHeight(albums.getMinHeight());
        rootAlbumsContainer.setSpacing(20);
        rootAlbumsContainer.getChildren().addAll(albums.getChildren());
    }

    /**
     * Updates the content in the image tab content container
     *
     * @param images the {@link VBox} containing the images
     */
    private void replaceRootImagesWith(VBox images) {
        rootImagesContainer.getChildren().clear();
        rootImagesContainer.setMinHeight(images.getMinHeight());
        rootImagesContainer.setSpacing(20);
        rootImagesContainer.getChildren().addAll(images.getChildren());
    }

    /**
     * Set the progressbar to finished and remove it from the root container.
     */
    private void finalizeProgress() {
        pageRootContainer.getChildren().remove(progressBarContainer);
    }

    /**
     * Create a {@link VBox} of image {@link HBox} children to add to a root container.
     *
     * @param listOfImages the images to add
     * @param start min index of image list to be added
     * @param end max index of images list to be added
     * @return the VBox containing image containers
     */
    public VBox computeRootImagesContainerChildren(ObservableList<Image> listOfImages , int start, int end){
        int loadEnd = Math.min(listOfImages.size(), end); //Will not exceed list of image size if end is higher
        List<HBox> imageHBoxes = new ArrayList<>();

        for (int i = start; i < loadEnd; i++) {
            Image image = listOfImages.get(i);
            ImageHBox imageHBox = new ImageHBox(image);
            setSwitchToImageOnImageComponents(imageHBox);
            imageHBoxes.add(imageHBox);
            imageHBox.replaceImageViewWith(image);
        }

        VBox container = new VBox();
        container.getChildren().addAll(imageHBoxes);
        return container;
    }

    /**
     * Create a {@link VBox} of album {@link HBox} children to add to a root container.
     * Load each corresponding preview images in a separate background task and add them when ready.
     *
     * @param listOfAlbums the albums to add
     * @param start min index of albums list to be added
     * @param end max index of albums list to be added
     * @return the VBox containing album containers
     */
    public VBox computeRootAlbumsContainerChildren(ObservableList<Album> listOfAlbums, int start, int end){
        int loadEnd = Math.min(listOfAlbums.size(), end);
        List<HBox> albumHBoxes = new ArrayList<>();

        for (int i = start; i < loadEnd; i++) {
            Album album = listOfAlbums.get(i);
            AlbumHBox albumHBox = new AlbumHBox(album);

            Task<Optional<Image>> fetchPreviewImageTask = fetchPreviewImageFrom(album);
            executorService.submit(fetchPreviewImageTask);

            fetchPreviewImageTask.setOnSucceeded(event -> {
                Optional<Image> previewImage = fetchPreviewImageTask.getValue();

                previewImage.ifPresent(albumHBox::replaceAlbumImageViewWith);
                setSwitchToAlbumOnAlbumComponents(albumHBox);
            });

            albumHBoxes.add(albumHBox);
        }

        VBox container = new VBox();
        container.getChildren().addAll(albumHBoxes);
        return container;
    }

    /**
     * Add {@link MouseEvent} event handler to appropriate components in given {@link AlbumHBox}.
     *
     * @param imageHBox the composite component.
     */
    private void setSwitchToImageOnImageComponents(ImageHBox imageHBox) {
        EventHandler<MouseEvent> mouseEventEventHandler = mouseEvent -> {
            try {
                switchToViewImage(mouseEvent);
            } catch (IOException ex) {
                logger.error("[x] Failed to switch to Album View", ex);
            }
        };

        Stream.of(imageHBox.getImageTitleHBox(),
                imageHBox.getImageView())
                .forEach(node -> imageHBox.addOnMouseClickedEventHandlerToComponent(node, mouseEventEventHandler));
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
     * Fetch the images from given album in a background task.
     *
     * @param album the album whose images to fetch
     * @return task to return a list of fetched images
     */
    private Task<Optional<Image>> fetchPreviewImageFrom(Album album) {
        return new Task<>() {
            @Override
            protected Optional<Image> call() {
                try {
                    return new AlbumService(App.ex.getEntityManager()).findPreviewImage(album);
                } catch (Exception e) {
                    logger.error("[x] Failed to fetch preview image for album {}", album, e);
                }

                return Optional.empty();
            }

            @Override
            protected void cancelled() {
                logger.error("Finding preview image for album task cancelled. Album: {}", album,
                        this.getException());
            }

            @Override
            protected void failed() {
                logger.error("Finding preview image task for album failed. Album: {}", album,
                        this.getException());
            }
        };
    }

    /**
     * It finds out witch is current selected tab and if it is possible to load previous content on that page.
     * If yes it updates content to previous elements of the list.
     * @param actionEvent
     * @throws IOException
     */
    public void switchToPrevious(ActionEvent actionEvent) throws IOException {
        if (tabContainer.getSelectionModel().getSelectedItem().equals(imagesTab)){
            if (imageStart != 0){
                imageStart -= ELEMENTS_PER_PAGE;
                imageEnd -= ELEMENTS_PER_PAGE;
                VBox newImages = computeRootImagesContainerChildren(listOfImages, imageStart, imageEnd);
                replaceRootImagesWith(newImages);
                nextBtn.setDisable(false);
            }
            if (imageStart == 0){
                previousBtn.setDisable(true);
            }
        }
        else{
            if (albumStart >= ELEMENTS_PER_PAGE){
                albumStart -= ELEMENTS_PER_PAGE;
                albumEnd -= ELEMENTS_PER_PAGE;
                VBox newAlbums = computeRootAlbumsContainerChildren(listOfAlbums, albumStart, albumEnd);
                replaceRootAlbumsWith(newAlbums);
                nextBtn.setDisable(false);
            }
            if (albumStart == 0){
                previousBtn.setDisable(true);
            }
        }
    }

    /**
     * It finds out witch is current selected tab and if it is possible to load next content on that page.
     * If yes it updates content to next elements of the list.
     * @param actionEvent
     * @throws IOException
     */
    public void switchToNext(ActionEvent actionEvent) throws IOException {
        //Selected tab is image
        if (tabContainer.getSelectionModel().getSelectedItem().equals(imagesTab)){
            if (imageEnd < listOfImages.size()){
                imageStart += ELEMENTS_PER_PAGE;
                imageEnd += ELEMENTS_PER_PAGE;
                VBox newImages = computeRootImagesContainerChildren(listOfImages, imageStart, imageEnd);
                replaceRootImagesWith(newImages);
                previousBtn.setDisable(false);
            }
            if (imageEnd >= listOfImages.size()){
                nextBtn.setDisable(true);
            }
        }
        //Selected tab is album
        else{
            if (albumEnd < listOfAlbums.size()){
                albumStart += ELEMENTS_PER_PAGE;
                albumEnd += ELEMENTS_PER_PAGE;
                VBox newAlbums = computeRootAlbumsContainerChildren(listOfAlbums, albumStart, albumEnd);
                replaceRootAlbumsWith(newAlbums);
                previousBtn.setDisable(false);
            }
            if (albumEnd >= listOfAlbums.size()){
                nextBtn.setDisable(true);
            }
        }
    }

    /**
     * Changes to view image page. It takes a clicked element within an image container
     * and finds the fx:id of the main parent, and
     * passes the value to {@link DataExchange}.
     *
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToViewImage(MouseEvent mouseEvent) throws IOException {
        long imageId = findId(mouseEvent);

        if (imageId != 0) {
            App.ex.setChosenImg(imageId);
            App.setRoot("view_image");
        }
    }

    /**
     * Changes to view album page. It takes a clicked element within an album container
     * and finds the fx:id of the main parent, and
     * passes the value to {@link DataExchange}.
     *
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToViewAlbum(MouseEvent mouseEvent) throws IOException {
        long albumId = findId(mouseEvent);

        if (albumId != 0) {
            App.ex.setChosenAlbumId(albumId);
            App.setRoot("view_album");
        }
    }

    /**
     * It takes a clicked element within an container
     * and finds the fx:id of the main parent
     * @param mouseEvent
     * @return main containers id of clicked element
     */
    public long findId(MouseEvent mouseEvent){
        boolean idFound = false;
        long id = 0;
        Node node = (Node) mouseEvent.getSource();
        while (!idFound){
            if (node.getId() != null){
                id = Long.parseLong(node.getId());
                idFound = true;
            }
            node = node.getParent();
        }
        return id;
    }
}
