package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.service.AlbumDocument;
import NTNU.IDATT1002.service.AlbumService;
import NTNU.IDATT1002.service.TagService;
import NTNU.IDATT1002.utils.ImageUtil;
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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    public ImageView mainImageContainer;
    public Text mainImageTitle;
    public Text mainImageTags;

    @FXML
    public VBox albumTextContainer;
    public Button viewOnMapBtn;
    public HBox albumImagesContainer;

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
            List<NTNU.IDATT1002.models.Image> albumImages = album.getImages();
            //If album has an image
            if (albumImages.size() > 0) {
                NTNU.IDATT1002.models.Image mainImage = albumImages.get(0);
                mainImageContainer.setImage(ImageUtil.convertToFXImage(mainImage));
                mainImageTitle.setText("ADD IMAGE TITLE");
                mainImageTags.setText(TagService.getTagsAsString(mainImage.getTags()));
                insertAlbumTextToContainer(album);
                for (NTNU.IDATT1002.models.Image image : albumImages) {
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(64);
                    imageView.setFitWidth(114);
                    imageView.setPreserveRatio(true);
                    imageView.setId(image.getId().toString());
                    imageView.setImage(ImageUtil.convertToFXImage(image));
                    albumImagesContainer.getChildren().add(imageView);
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            setActiveImage(mouseEvent);
                        }
                    });
                }
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
            Optional<NTNU.IDATT1002.models.Image> findImage = currentAlbum.getImages().stream().filter(img -> img.getId().equals(clickedImageId)).findFirst();
            findImage.ifPresent(newImage -> {
                Image image = ImageUtil.convertToFXImage(newImage);
                mainImageTitle.setText("ADD IMAGE TITLE");
                mainImageTags.setText(TagService.getTagsAsString(newImage.getTags()));
                mainImageContainer.setImage(image);
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
