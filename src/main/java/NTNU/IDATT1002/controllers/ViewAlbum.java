package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.service.AlbumDocument;
import NTNU.IDATT1002.service.AlbumService;
import NTNU.IDATT1002.service.TagService;
import NTNU.IDATT1002.utils.ImageUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javax.persistence.EntityManager;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;

/**
 * Controls the buttons and changeable elements on view_album.fxml,
 * a page where get a more detailed view of an album
 * @version 1.0 22.03.2020
 */
public class ViewAlbum implements Initializable {
    public TextField tbar_search;
    public ImageView tbar_logo;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;
    public Button tbar_searchBtn;
    public Button tbar_albums;
    public Pane metadataPane;
    public Button createAlbumPdf;
    public ImageView mainPicture;
    public Text pictureTitleField;
    public Text pictureTagsField;

    @FXML
    public VBox albumTextContainer;
    public HBox albumImages;
    public Button viewOnMapBtn;

    private AlbumService albumService;
    private Album currentAlbum;

    

    /**
     * Initialize view with real album data.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EntityManager entityManager = App.ex.getEntityManager();
        albumService =  new AlbumService(entityManager);
        Long currentAlbumId = App.ex.getChosenAlbumId();

        Optional<Album> foundAlbum = albumService.getAlbumById(currentAlbumId);
        foundAlbum.ifPresent(album -> {
            currentAlbum = album;
            NTNU.IDATT1002.models.Image titleImage = album.getImages().get(0);
            Image image = ImageUtil.convertToFXImage(titleImage);
            mainPicture.setImage(image);
            pictureTitleField.setText(album.getTitle());
            pictureTagsField.setText(TagService.getTagsAsString(album.getTags()));
            insertAlbumTextToContainer(album);
            for (NTNU.IDATT1002.models.Image i: album.getImages()) {
                ImageView iV = new ImageView();
                iV.setFitHeight(64);
                iV.setFitWidth(114);
                iV.setPreserveRatio(true);
                iV.setId(i.getId().toString());
                iV.setImage(ImageUtil.convertToFXImage(i));
                albumImages.getChildren().add(iV);
                iV.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent mouseEvent) {
                        setActiveImage(mouseEvent);
                    }
                });
            }
        });
    }

    /**
     * Changes the current main picture
     * //TODO: Make it change main picture title and tags
     * @param mouseEvent something is clicked
     */
    private void setActiveImage(MouseEvent mouseEvent) {
        Object clickedObject = mouseEvent.getSource();
        if (clickedObject instanceof ImageView) {
            ImageView clickedImageView = (ImageView) mouseEvent.getSource();
            Long clickedImageId = Long.parseLong(clickedImageView.getId());
            Optional<NTNU.IDATT1002.models.Image> newImage = currentAlbum.getImages().stream().filter(img -> img.getId().equals(clickedImageId)).findFirst();
            newImage.ifPresent(img -> {
                Image image = ImageUtil.convertToFXImage(img);
                mainPicture.setImage(image);
            });
        }
    }

    /**
     * Att text elements from album to the container
     *
     * @param album the album to display
     */
    private void insertAlbumTextToContainer(Album album) {
        //Creates a vbox so that nodes is aligned in a column
        albumTextContainer.setSpacing(5);
        albumTextContainer.setPadding(new Insets(0, 0, 20, 0));

        insertAlbumTitle(album, albumTextContainer);
        insertAlbumAuthor(album, albumTextContainer);
        insertAlbumTags(album, albumTextContainer);
        insertAlbumDescription(album, albumTextContainer);
    }

    /**
     * Insert title of the given album to the given container
     * It is clickable, and switches to View Album page of that album
     *
     * @param album the album which title to display
     * @param textContainer container for text elements of an album
     */
    private void insertAlbumTitle(Album album, VBox textContainer) {
        HBox content = new HBox();

        Text titleLabel = new Text("Title: ");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 48));

        Text title = new Text(album.getTitle());
        title.setFont(Font.font("System",48));

        content.getChildren().addAll(titleLabel, title);

        textContainer.getChildren().add(content);
    }

    /**
     * Insert author of the given album to the given container
     *
     * @param album the album which author to display
     * @param textContainer container for text elements of an album
     */
    private void insertAlbumAuthor(Album album, VBox textContainer) {
        HBox content = new HBox();
        Text authorLabel = new Text("Author: ");
        authorLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        Text author = new Text(album.getUser().getUsername());
        author.setFont(Font.font("System",24));

        content.getChildren().addAll(authorLabel, author);
        textContainer.getChildren().add(content);
    }

    /**
     * Insert tags of the given album to the given container
     *
     * @param album the album which tags to display
     * @param textContainer container for text elements of an album
     */
    private void insertAlbumTags(Album album, VBox textContainer) {
        HBox content = new HBox();
        Text tagsLabel = new Text("Tags: ");
        tagsLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        String tagsAsString = album.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.joining(" "));
        Text tags = new Text(tagsAsString);
        tags.setFont(Font.font("System",16));

        content.getChildren().addAll(tagsLabel, tags);
        textContainer.getChildren().add(content);
    }


    /**
     * Insert description of the given album to the given container
     *
     * @param album the album which description to display
     * @param textContainer container for text elements of an album
     */
    private void insertAlbumDescription(Album album, VBox textContainer) {
        Text descriptionLabel = new Text("Description: ");
        descriptionLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        Text description = new Text(album.getDescription());
        description.setWrappingWidth(500);
        description.setFont(Font.font("System",16));


        textContainer.getChildren().addAll(descriptionLabel, description);
    }

    /**
     * Method that changes scene to Main page
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToMain(MouseEvent mouseEvent) throws IOException {
        App.ex.setChosenAlbumId(null);
        App.setRoot("main");
    }

    /**
     * Method that changes scene to Search page. It reads the value of the search
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
     * Method that changes scene to Explore page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToExplore(ActionEvent actionEvent) throws IOException {
        App.ex.setChosenAlbumId(null);
        App.setRoot("explore");
    }

    /**
     * Method that changes scene to Albums page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToAlbums(ActionEvent actionEvent) throws IOException {
        App.ex.setChosenAlbumId(null);
        App.setRoot("explore_albums");
    }

    /**
     * Method that changes scene to Map page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToMap(ActionEvent actionEvent) throws IOException {
        App.ex.setChosenAlbumId(null);
        App.setRoot("map");
    }

    /**
     * Method that changes scene to Upload page
     * @param actionEvent the mouse has done something
     * @throws IOException this page does not exist
     */
    public void switchToUpload(ActionEvent actionEvent) throws IOException {
        App.ex.setChosenAlbumId(null);
        App.setRoot("upload");
    }

    public void openPopUpPicture(MouseEvent mouseEvent) {
        //write method that opens a pop-up view of the main picture
    }

    public void changeMainPicture1(MouseEvent mouseEvent) {
        //write method that switches to main picture to be picture 1 in the scrollbar-view
    }

    public void changeMainPicture2(MouseEvent mouseEvent) {
        //write method that switches to main picture to be picture 2 in the scrollbar-view
    }

    public void changeMainPicture3(MouseEvent mouseEvent) {
        //write method that switches to main picture to be picture 3 in the scrollbar-view
    }

    public void changeMainPicture4(MouseEvent mouseEvent) {
        //write method that switches to main picture to be picture 4 in the scrollbar-view
    }

    public void changeMainPicture5(MouseEvent mouseEvent) {
        //write method that switches to main picture to be picture 5 in the scrollbar-view
    }

    public void changeMainPicture6(MouseEvent mouseEvent) {
        //write method that switches to main picture to be picture 6 in the scrollbar-view
    }

    public void loadPreviousScrollbarView(ActionEvent actionEvent) {
        //write method that loads the previous 6 images in the album into the scrollbar-view
    }

    public void loadNextScrollbarView(ActionEvent actionEvent) {
        //write method that loads the next 6 images in the album into the scrollbar-view
    }

    /**
     * Retrieve and display album document.
     *
     * @param actionEvent
     */
    public void createPdf(ActionEvent actionEvent) {
        Long currentAlbumId = App.ex.getChosenAlbumId();
        AlbumDocument document = albumService.getDocument(currentAlbumId);

        displayDocumentLink(document.getDocument());
    }

    /**
     * Replace create album pdf button with a button to open the given document.
     *
     * @param pdfDocument the pdf document to be opened
     */
    private void displayDocumentLink(File pdfDocument) {
        createAlbumPdf.setText("Open PDF");
        createAlbumPdf.setOnAction(actionEvent -> openDocument(actionEvent, pdfDocument));
    }

    /**
     * Open given file.
     *
     * @param actionEvent
     * @param file the file to open
     */
    private void openDocument(ActionEvent actionEvent, File file) {
        HostServices hostServices = App.ex.getHostServices();
        hostServices.showDocument(file.getAbsolutePath());
    }

    public void viewOnMap(ActionEvent actionEvent) throws IOException {
        App.setRoot("map");
    }
}
