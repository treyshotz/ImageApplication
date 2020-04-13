package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.service.AlbumService;
import NTNU.IDATT1002.service.TagService;
import NTNU.IDATT1002.utils.ImageUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

/**
 * Controls the buttons and changeable elements on explore_albums.fxml,
 * a page where you explore albums
 * @version 1.1 04.04.2020
 */
public class ExploreAlbums implements Initializable {
    public ImageView tbar_logo;
    public TextField tbar_search;
    public Button tbar_map;
    public Button tbar_upload;
    public Button tbar_albums;
    public Button tbar_searchBtn;
    public Button tbar_explore;

    public ScrollPane scrollpane;
    //public Button footer_previous_page;
    //public Button footer_next_page;

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
            Platform.runLater(this::finalizeProgress);
            
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
     * Create the root container for displaying albums and add the retrieved albums.
     * It is currently a limit at 50 albums per page.
     * Load each corresponding images in a separate background task and add them when ready.
     *
     * @param listOfAlbums the albums to add
     * @return the VBox containing styled album HBoxes and ImageViews
     */
    public VBox createAlbumVBox(ObservableList<Album> listOfAlbums){
        VBox albumVBox = new VBox();
        int maxPerPage = Math.min(listOfAlbums.size(), 100);
        
        for (int i = 0; i < maxPerPage; i++) {
            Album album = listOfAlbums.get(i);
            HBox albumHBox = createAlbumHBox(album);

            Task<List<NTNU.IDATT1002.models.Image>> fetchImagesTask = fetchImages(album);
            executorService.submit(fetchImagesTask);

            fetchImagesTask.setOnSucceeded(workerStateEvent -> {
                ObservableList<NTNU.IDATT1002.models.Image> listOfImages = FXCollections.observableArrayList(fetchImagesTask.getValue());
                ImageView imageView = createAlbumImageView(listOfImages.get(0));
                Node imagePlaceholder = albumHBox.lookup("#imagePlaceholder");
                albumHBox.getChildren().remove(imagePlaceholder);
                albumHBox.getChildren().add(0, imageView);
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
    private Task<List<NTNU.IDATT1002.models.Image>> fetchImages(Album album) {
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
     * Set the progressbar to finished and remove it from the root container.
     */
    private void finalizeProgress() {
        progressBar.setProgress(1);
        pageRootContainer.getChildren().remove(progressBarContainer);
    }

    /**
     * Crete the HBox for a single album with a placeholder for an image to be added later.
     *
     * @param album the album to display
     * @return HBox with album fields and image placeholder
     */
    private HBox createAlbumHBox(Album album) {
        HBox albumContainer = new HBox();

        albumContainer.setId(album.getId().toString());
        albumContainer.setPrefWidth(1520);
        albumContainer.setPrefHeight(300);

        VBox albumTextVBox = createAlbumTextVBox(album);
        ImageView imageViewPlaceholder = createStyledImageView();
        imageViewPlaceholder.setId("imagePlaceholder");

        albumContainer.getChildren().addAll(imageViewPlaceholder, albumTextVBox);

        return albumContainer;
    }

    /**
     * Create a styled ImageView with no content.
     *
     * @return the ImageView created
     */
    private ImageView createStyledImageView() {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(300.0);
        imageView.setFitWidth(533.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        return imageView;
    }

    /**
     * Create the ImageView which holds the given image
     *
     * @param image the image to display
     */
    private ImageView createAlbumImageView(NTNU.IDATT1002.models.Image image) {
        ImageView imageView = createStyledImageView();
        imageView.setOnMouseClicked(mouseEvent -> {
            try{
                switchToViewAlbum(mouseEvent);
            } catch (IOException ex) {
                logger.error("[x] Failed to switch to Album View", ex);
            }
        });

        Image imageToSet = ImageUtil.convertToFXImage(image);
        imageView.setImage(imageToSet);

        return imageView;
    }

    /**
     * Create VBox holding the fields of given album.
     *
     * @param album the album to display
     */
    private VBox createAlbumTextVBox(Album album) {
        VBox textContainer = createAlignedVBox();

        HBox title = createAlbumTitleHBox(album.getTitle());
        HBox author = createAuthorHBox(album.getUser());
        HBox tags = createTagsHBox(album.getTags());
        HBox description = createDescriptionHBox(album.getDescription());

        textContainer.getChildren().addAll(title, author, tags, description);
        return textContainer;
    }

    /**
     * Create a VBox which aligns nodes in a column
     *
     * @return the styled VBox
     */
    private VBox createAlignedVBox() {
        VBox textContainer = new VBox();
        textContainer.setSpacing(5);
        textContainer.setPadding(new Insets(10, 0, 0, 25));
        textContainer.setPrefHeight(300);
        textContainer.setPrefWidth(987);

        return textContainer;
    }

    /**
     * Create HBox holding given title and corresponding label.
     * It is clickable, and switches to View Album page of that album
     *
     * @param title the title of the album
     */
    private HBox createAlbumTitleHBox(String title) {
        HBox content = new HBox();

        Text label = new Text("Title: ");
        label.setFont(Font.font("System", FontWeight.BOLD, 48));
        Text titleText = new Text(title);
        titleText.setFont(Font.font("System",48));

        content.getChildren().addAll(label, titleText);
        content.setOnMouseClicked(mouseEvent -> {
            try{
                switchToViewAlbum(mouseEvent);
            } catch (IOException ex) {
                logger.error("[x] Failed to switch to Album View", ex);
            }
        });

        return content;
    }

    /**
     * Create HBox holding given user and corresponding label.
     *
     * @param user the user/author of the album
     */
    private HBox createAuthorHBox(User user) {
        HBox content = new HBox();
        Text authorLabel = new Text("Author: ");
        authorLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        Text author = new Text(user.getUsername());
        author.setFont(Font.font("System",24));
        content.getChildren().addAll(authorLabel, author);

        return content;
    }

    /**
     * Create HBox holding given tags and corresponding label.
     *
     * @param tags the list of tags
     */
    private HBox createTagsHBox(List<Tag> tags) {
        HBox content = new HBox();
        Text tagsLabel = new Text("Tags: ");
        tagsLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        String tagsAsString = TagService.getTagsAsString(tags);
        Text tagsText = new Text(tagsAsString);
        tagsText.setFont(Font.font("System",16));
        content.getChildren().addAll(tagsLabel, tagsText);

        return content;
    }

    /**
     * Create HBox holding given description and corresponding label.
     *
     * @param description the description to display
     */
    private HBox createDescriptionHBox(String description) {
        HBox content = new HBox();

        Text descriptionLabel = new Text("Description: ");
        descriptionLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        Text descriptionText = new Text(description);
        descriptionText.setWrappingWidth(500);
        descriptionText.setFont(Font.font("System",16));
        content.getChildren().addAll(descriptionLabel, descriptionText);

        return content;
    }

    /**
     * Change scene to Main page.
     *
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToMain(MouseEvent mouseEvent) throws IOException {
        App.setRoot("main");
    }

    /**
     * Change scene to Search page. Reads the value of the search
     * field and if not empty it is passed to DataExchange.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void switchToSearch(ActionEvent actionEvent) throws IOException {
        if (!tbar_search.getText().isEmpty()){
            App.ex.setSearchField(tbar_search.getText());
        }
        App.setRoot("search");
    }

    /**
     * Change scene to Explore page.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void switchToExplore(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore");
    }

    /**
     * Change scene to Albums page.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void switchToAlbums(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore_albums");
    }

    /**
     * Change scene to Map page.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void switchToMap(ActionEvent actionEvent) throws IOException {
        App.setRoot("map");
    }

    /**
     * Change scene to Upload page.
     *
     * @param actionEvent the mouse has done something
     * @throws IOException this page does not exist
     */
    public void switchToUpload(ActionEvent actionEvent) throws IOException {
        App.setRoot("upload");
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
