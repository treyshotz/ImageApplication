package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.ImageAlbum;
import NTNU.IDATT1002.service.ImageAlbumService;
import NTNU.IDATT1002.service.TagService;
import NTNU.IDATT1002.utils.PdfDocument;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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


    public Text album_titleField;
    public Text album_authorField;
    public Text album_tagsField;
    public Text album_descField;

    public Pane metadata_pane;
    public Button create_album_pdf;

    public ImageView main_picture;

    public ImageView scroll_picture6;
    public ImageView scroll_picture5;
    public ImageView scroll_picture4;
    public ImageView scroll_picture3;
    public ImageView scroll_picture2;
    public ImageView scroll_picture1;
    public Button scroll_button_next;
    public Button scroll_button_previous;
    public Text picture_title_field;
    public Text picture_tagsField;

    @FXML
    public GridPane album_fields_grid_pane;
    private Pane album_field_container;

    private ImageAlbumService imageAlbumService;
    private Long currentAlbumId;

    public ViewAlbum() {
        imageAlbumService = new ImageAlbumService();
        currentAlbumId = App.ex.getChosenAlbumId();
    }

    /**
     * Initialize view with real album data.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        album_field_container = new Pane();
        album_field_container.setPrefHeight(1011.0);
        album_field_container.setPrefWidth(975.0);
        album_field_container.setStyle("-fx-background-color: #999999;");

        Optional<ImageAlbum> foundImageAlbum = imageAlbumService.getImageAlbumById(currentAlbumId);
        foundImageAlbum.ifPresent(imageAlbum -> addAlbumToPane(imageAlbum, album_field_container));

        album_fields_grid_pane.add(album_field_container, 0, 0);
    }

    /**
     * Add given album to given pane.
     *
     * @param imageAlbum the album to add
     * @param pane the pane to add the album to 
     */
    private void addAlbumToPane(ImageAlbum imageAlbum, Pane pane) {
        addAlbumFieldsToPane(imageAlbum, pane);
    }

    /**
     * Add albums fields and labels to display an album by.
     * This includes title, author, tags and description
     *
     * @param album the album to display
     * @param pane the pane to add the album to
     */
    private void addAlbumFieldsToPane(ImageAlbum album, Pane pane) {
        insertAlbumTitleLabelToPane(pane);
        insertAlbumTitleToPane(album, pane);

        insertAlbumAuthorLabelToPane(pane);
        insertAlbumAuthorToPane(album, pane);

        insertAlbumTagsLabelToPane(pane);
        insertAlbumTagsToPane(album, pane);

        insertAlbumDescriptionLabelToPane(pane);
        insertAlbumDescriptionToPane(album, pane);
    }


    /**
     * Insert album title label to given pane.
     *
     * @param pane the pane to add the title to
     */
    private void insertAlbumTitleLabelToPane(Pane pane) {
        Text text = new Text();
        text.setText("TITLE: ");
        text.setFont(Font.font(36.0));
        text.setLayoutX(76.0);
        text.setLayoutY(170.0);
        text.setStrokeType(StrokeType.OUTSIDE);

        pane.getChildren().add(text);
    }

    /**
     * Insert title of the given album to the given pane
     *
     * @param album the album which title to display
     * @param pane the pane to add the title to
     */
    private void insertAlbumTitleToPane(ImageAlbum album, Pane pane) {
        album_titleField = new Text();
        album_titleField.setId("album_titleField");
        album_titleField.setText(album.getTitle());
        album_titleField.setFont(Font.font(36.0));
        album_titleField.setLayoutX(190.0);
        album_titleField.setLayoutY(170.0);
        album_titleField.setStrokeType(StrokeType.OUTSIDE);
        album_titleField.setStrokeWidth(0.0);

        pane.getChildren().add(album_titleField);
    }

    /**
     * Insert author label of the given album to the given pane
     *
     * @param pane the pane to add the author label to
     */
    private void insertAlbumAuthorLabelToPane(Pane pane) {
        Text authorLabel = new Text();
        authorLabel.setText("AUTHOR: ");
        authorLabel.setFont(Font.font(24.0));
        authorLabel.setLayoutX(76.0);
        authorLabel.setLayoutY(206.0);
        authorLabel.setStrokeType(StrokeType.OUTSIDE);
        authorLabel.setStrokeWidth(0.0);

        pane.getChildren().add(authorLabel);
    }

    /**
     * Insert author of the given album to the given pane
     *
     * @param album the album which author to display
     * @param pane the pane to add the author to
     */
    private void insertAlbumAuthorToPane(ImageAlbum album, Pane pane) {
        album_authorField = new Text();
        album_authorField.setId("album_authorField");
        album_authorField.setText(album.getUser().getUsername());
        album_authorField.setFont(Font.font(24.0));
        album_authorField.setLayoutX(200.0);
        album_authorField.setLayoutY(206.0);
        album_authorField.setStrokeType(StrokeType.OUTSIDE);
        album_authorField.setStrokeWidth(0.0);

        pane.getChildren().add(album_authorField);
    }

    /**
     * Insert tags label of the given album to the given pane
     *
     * @param pane the pane to add the tags label to
     */
    private void insertAlbumTagsLabelToPane(Pane pane) {
        Text tagsLabel = new Text();
        tagsLabel.setText("TAGS: ");
        tagsLabel.setFont(Font.font(24.0));
        tagsLabel.setLayoutX(76.0);
        tagsLabel.setLayoutY(239.0);
        tagsLabel.setStrokeType(StrokeType.OUTSIDE);
        tagsLabel.setStrokeWidth(0.0);

        pane.getChildren().add(tagsLabel);
    }

    /**
     * Insert tags of the given album to the given pane
     *
     * @param album the album which tags to display
     * @param pane the pane to add the tags to
     */
    private void insertAlbumTagsToPane(ImageAlbum album, Pane pane) {
        String tagsAsString = TagService.getTagsAsString(album.getTags());

        album_tagsField = new Text();
        album_tagsField.setText(tagsAsString);
        album_tagsField.setFont(Font.font(24.0));
        album_tagsField.setLayoutX(156.0);
        album_tagsField.setLayoutY(239.0);
        album_tagsField.setStrokeType(StrokeType.OUTSIDE);
        album_tagsField.setStrokeWidth(0.0);

        pane.getChildren().add(album_tagsField);
    }


    /**
     * Insert description label of the given album to the given pane
     *
     * @param pane the pane to add the description label to
     */
    private void insertAlbumDescriptionLabelToPane(Pane pane) {
        Text descriptionLabel = new Text();
        descriptionLabel.setText("DESCRIPTION: ");
        descriptionLabel.setFont(Font.font(24.0));
        descriptionLabel.setLayoutX(76.0);
        descriptionLabel.setLayoutY(271.0);
        descriptionLabel.setStrokeType(StrokeType.OUTSIDE);
        descriptionLabel.setStrokeWidth(0.0);
        descriptionLabel.setWrappingWidth(164.24609375);

        pane.getChildren().add(descriptionLabel);
    }

    /**
     * Insert description of the given album to the given pane
     *
     * @param album the album which description to display
     * @param pane the pane to add the description to
     */
    private void insertAlbumDescriptionToPane(ImageAlbum album, Pane pane) {
        album_descField = new Text();
        album_descField.setId("album_descField");
        album_descField.setText(album.getDescription());
        album_descField.setFont(Font.font(14.0));
        album_descField.setLayoutX(76.0);
        album_descField.setLayoutY(294.0);
        album_descField.setStrokeType(StrokeType.OUTSIDE);
        album_descField.setStrokeWidth(0.0);
        album_descField.setWrappingWidth(822.0);

        pane.getChildren().add(album_descField);
    }

    /**
     * Method that changes scene to Main page
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToMain(MouseEvent mouseEvent) throws IOException {
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
        App.setRoot("search");
    }

    /**
     * Method that changes scene to Explore page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToExplore(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore");
    }

    /**
     * Method that changes scene to Albums page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToAlbums(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore_albums");
    }

    /**
     * Method that changes scene to Map page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToMap(ActionEvent actionEvent) throws IOException {
        App.setRoot("map");
    }

    /**
     * Method that changes scene to Upload page
     * @param actionEvent the mouse has done something
     * @throws IOException this page does not exist
     */
    public void switchToUpload(ActionEvent actionEvent) throws IOException {
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
     * Create and save album pdf to users downloads directory.
     *
     * @param actionEvent
     */
    public void createPdf(ActionEvent actionEvent) {
        Long currentAlbumId = App.ex.getChosenAlbumId();
        ImageAlbum imageAlbum = imageAlbumService.getImageAlbumById(currentAlbumId)
                .orElseThrow(IllegalArgumentException::new);

        String destinationFile = String.format("%s/downloads/%s.pdf",
                System.getProperty("user.home"),
                imageAlbum.getTitle());

        PdfDocument document = new PdfDocument(imageAlbum, destinationFile);
        document.createPdfDocument();
        logger.info("[x] Saved PDF document to " + destinationFile);

        displayPdfLink(document.getPdfDocument());
    }

    /**
     * Replace create album pdf button with a button to open the given document.
     *
     * @param pdfDocument the pdf document to be opened
     */
    private void displayPdfLink(File pdfDocument) {
        create_album_pdf.setText("Open PDF");
        create_album_pdf.setOnAction(actionEvent -> openPdfDocument(actionEvent, pdfDocument));
    }

    /**
     * Open given file.
     *
     * @param actionEvent
     * @param file the file to open
     */
    private void openPdfDocument(ActionEvent actionEvent, File file) {
        HostServices hostServices = App.ex.getHostServices();
        hostServices.showDocument(file.getAbsolutePath());
    }
}
