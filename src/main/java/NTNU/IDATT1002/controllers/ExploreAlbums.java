package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.ImageAlbum;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.service.ImageAlbumService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controls the buttons and changeable elements on explore_albums.fxml,
 * a page where you explore albums
 * @version 1.0 22.03.2020
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
    public Button footer_previous_page;
    public Button footer_next_page;

    public Text album_amount;
    public ChoiceBox sorted_by_choicebox;
    public Button create_album_button;


    public ImageView album_image;
    public Text album_title;
    public Text album_author;
    public Text album_desc;
    public Text album_tags;
    public Button open_album;

    @FXML
    private GridPane albums_grid_pane;
    private Pane paneContainer;

    private ImageAlbumService imageAlbumService;

    public ExploreAlbums() {
        imageAlbumService = new ImageAlbumService();
    }


    /**
     * Initialize page with all albums. Max 5 per page.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<ImageAlbum> albums = imageAlbumService.getAllImageAlbums();

        int maxPerPage = Math.min(albums.size(), 5);

        for(int i = 0; i<maxPerPage; i++) {
            paneContainer = new Pane();
            paneContainer.setPrefWidth(200);
            paneContainer.setPrefHeight(100);

            addSingleAlbumContentToPane(albums.get(i), paneContainer);

            albums_grid_pane.add(paneContainer, 0, i);
        }
    }

    /**
     * Add all components needed to display an album.
     *
     * @param album the album to display
     * @param pane the pane to add the album to
     */
    private void addSingleAlbumContentToPane(ImageAlbum album, Pane pane) {
        insertImageViewToPane(album, pane);
        insertAlbumTitleLabelToPane(pane);
        insertAlbumAuthorLabelToPane(pane);
        insertAlbumDescriptionLabelToPane(pane);
        insertAlbumAuthorToPane(album, pane);
        insertAlbumTitleToPane(album, pane);
        insertAlbumDescriptionToPane(album, pane);
        insertAlbumTagsLabelToPane(pane);
        insertAlbumTagsToPane(album, pane);
        insertOpenAlbumButtonToPane(album, pane);
    }

    /**
     * Format and insert the first image in the given album to the given pane.
     *
     * @param album the album to display image from
     * @param pane the pane to add the image to
     */
    private void insertImageViewToPane(ImageAlbum album, Pane pane) {
        // Set and format image
        album_image = new ImageView();
        album_image.setFitHeight(307.0);
        album_image.setFitWidth(516.0);
        album_image.setLayoutX(-2.0);
        album_image.setLayoutY(-1.0);
        album_image.setPickOnBounds(true);
        album_image.setPreserveRatio(true);

        NTNU.IDATT1002.models.Image titleImage = album.getImages().get(0);
        album_image.setId(titleImage.getId().toString());

        Image image = new Image("@../../Images/placeholder-1920x1080.png"); // TODO: display image here
        album_image.setImage(image);

        pane.getChildren().add(album_image);
    }

    /**
     * Insert album title label to given pane.
     *
     * @param pane the pane to add the title to
     */
    private void insertAlbumTitleLabelToPane(Pane pane) {
        // set and format title label
        Text text = new Text();
        text.setText("ALBUM: ");
        text.setFont(Font.font(48.0));
        text.setLayoutX(551.0);
        text.setLayoutY(63.0);
        text.setStrokeType(StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setWrappingWidth(200);

        pane.getChildren().add(text);
    }

    /**
     * Insert author label of the given album to the given pane
     *
     * @param pane the pane to add the author label to
     */
    private void insertAlbumAuthorLabelToPane(Pane pane) {
        // set and format author label
        Text authorLabel = new Text();
        authorLabel.setText("AUTHOR: ");
        authorLabel.setFont(Font.font(24));
        authorLabel.setLayoutX(551.0);
        authorLabel.setLayoutY(97.0);
        authorLabel.setStrokeType(StrokeType.OUTSIDE);
        authorLabel.setStrokeWidth(0.0);
        authorLabel.setWrappingWidth(150.0);

        pane.getChildren().add(authorLabel);
    }

    /**
     * Insert description label of the given album to the given pane
     *
     * @param pane the pane to add the description label to
     */
    private void insertAlbumDescriptionLabelToPane(Pane pane) {
        // set and format description label
        Text descriptionLabel = new Text();
        descriptionLabel.setText("DESCRIPTION: ");
        descriptionLabel.setFont(Font.font(18.0));
        descriptionLabel.setLayoutX(551.0);
        descriptionLabel.setLayoutY(157.0);
        descriptionLabel.setStrokeType(StrokeType.OUTSIDE);
        descriptionLabel.setStrokeWidth(0.0);
        descriptionLabel.setWrappingWidth(129.0);

        pane.getChildren().add(descriptionLabel);
    }

    /**
     * Insert author of the given album to the given pane
     *
     * @param album the album which author to display
     * @param pane the pane to add the author to
     */
    private void insertAlbumAuthorToPane(ImageAlbum album, Pane pane) {
        // set and format author
        album_author = new Text();
        album_author.setId("album_author");
        album_author.setText(album.getUser().getUsername());
        album_author.setFont(Font.font(24.0));
        album_author.setLayoutX(707.0);
        album_author.setLayoutY(97.0);
        album_author.setStrokeType(StrokeType.OUTSIDE);
        album_author.setStrokeWidth(0.0);
        album_author.setWrappingWidth(270.0);

        pane.getChildren().add(album_author);
    }

    /**
     * Insert title of the given album to the given pane
     *
     * @param album the album which title to display
     * @param pane the pane to add the title to
     */
    private void insertAlbumTitleToPane(ImageAlbum album, Pane pane) {
        album_title = new Text();
        album_title.setId("album_title");
        album_title.setText(album.getTitle());
        album_title.setFont(Font.font(48.0));
        album_title.setLayoutX(751.0);
        album_title.setLayoutY(65.0);
        album_title.setStrokeType(StrokeType.OUTSIDE);
        album_title.setStrokeWidth(0.0);
        album_title.setWrappingWidth(653.0);

        pane.getChildren().add(album_title);
    }

    /**
     * Insert description of the given album to the given pane
     *
     * @param album the album which description to display
     * @param pane the pane to add the description to
     */
    private void insertAlbumDescriptionToPane(ImageAlbum album, Pane pane) {
        album_desc = new Text();
        album_desc.setId("album_desc");
        album_desc.setText(album.getDescription());
        album_desc.setFont(Font.font(18.0));
        album_desc.setLayoutX(707.0);
        album_desc.setLayoutY(157.0);
        album_desc.setStrokeType(StrokeType.OUTSIDE);
        album_desc.setStrokeWidth(0.0);
        album_desc.setWrappingWidth(229.0);

        pane.getChildren().add(album_desc);
    }

    /**
     * Insert tags label of the given album to the given pane
     *
     * @param pane the pane to add the tags label to
     */
    private void insertAlbumTagsLabelToPane(Pane pane) {
        Text tagsLabel = new Text();
        tagsLabel.setText("TAGS: ");
        tagsLabel.setFont(Font.font(24));
        tagsLabel.setLayoutX(551.0);
        tagsLabel.setLayoutY(129.0);
        tagsLabel.setStrokeType(StrokeType.OUTSIDE);
        tagsLabel.setStrokeWidth(0.0);
        tagsLabel.setWrappingWidth(150.0);

        pane.getChildren().add(tagsLabel);
    }

    /**
     * Insert tags of the given album to the given pane
     *
     * @param album the album which tags to display
     * @param pane the pane to add the tags to
     */
    private void insertAlbumTagsToPane(ImageAlbum album, Pane pane) {
        String tagsAsString = album.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.joining(", "));

        System.out.println(tagsAsString);

        album_tags = new Text();
        album_tags.setText("tag1, tag2");
        album_tags.setFont(Font.font(24.0));
        album_tags.setLayoutX(707.0);
        album_tags.setLayoutY(129.0);
        album_tags.setStrokeType(StrokeType.OUTSIDE);
        album_tags.setStrokeWidth(0.0);
        album_tags.setWrappingWidth(270.0);

        pane.getChildren().add(album_tags);
    }

    /**
     * Insert an 'open album' button to the given album to the given pane.
     * If pushed it will take the user to the appropriate album.
     *
     * @param album the album which the button should take the user to
     * @param pane the pane to add the button to
     */
    private void insertOpenAlbumButtonToPane(ImageAlbum album, Pane pane) {
        // set and format open album button
        open_album = new Button();
        open_album.setId(album.getId().toString());
        open_album.setText("Open Album");
        open_album.setFont(Font.font(18.0));
        open_album.setLayoutX(551.0);
        open_album.setLayoutY(250.0);
        open_album.setOnAction(event -> {
            try {
                switchToViewAlbum(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        pane.getChildren().add(open_album);
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

    /**
     * Method that changes scene to Create Album page
     * @param actionEvent the mouse has done something
     * @throws IOException this page does not exist
     */
    public void switchToCreateAlbum(ActionEvent actionEvent) throws IOException {
        App.setRoot("create_album");
    }

    /**
     * Method that updates content to previous "page"
     * @param actionEvent
     * @throws IOException
     */
    public void switchToPrevious(ActionEvent actionEvent) throws IOException {
        //TODO: Make method that updates content
    }

    /**
     * Method that updates content to next "page"
     * @param actionEvent
     * @throws IOException
     */
    public void switchToNext(ActionEvent actionEvent) throws IOException {
        //TODO: Make method that updates content
    }

    /**
     * Method to open specific albums using the "Open Album" button
     * @param actionEvent
     * @throws IOException
     */
    public void switchToViewAlbum(ActionEvent actionEvent) throws IOException {
        Button source = (Button) actionEvent.getSource();
        Long albumId = Long.parseLong(source.getId());
        App.ex.setChosenAlbumId(albumId);

        App.setRoot("view_album");
    }

}
