package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.ImageAlbum;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.service.ImageAlbumService;
import NTNU.IDATT1002.utils.ImageUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javax.persistence.EntityManager;
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
    //public Button footer_previous_page;
    //public Button footer_next_page;

    public Text albumAmount;
    public ChoiceBox sortedByChoicebox;
    public Button createAlbumButton;


    public ImageView albumImage;
    public VBox vBox;

    private ImageAlbumService imageAlbumService;

    public ExploreAlbums() {
        EntityManager entityManager = App.ex.getEntityManager();
        imageAlbumService = new ImageAlbumService(entityManager);
    }

    /**
     * Initialize page with all albums. Max 50 per page.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<ImageAlbum> albums = imageAlbumService.getAllImageAlbums();
            int maxPerPage = Math.min(albums.size(), 50);

            for (int i = 0; i < maxPerPage; i++) {
                //A container for image and album text
                HBox albumContainer = new HBox();
                //Stores album id here so that it can be passed to data exchange,
                //and the correct album will appear on View Album page when clicked
                albumContainer.setId(albums.get(i).getId().toString());
                albumContainer.setPrefWidth(1520);
                albumContainer.setPrefHeight(300);

                insertAlbumImageToContainer(albums.get(i), albumContainer);
                insertAlbumTextToContainer(albums.get(i), albumContainer);

                vBox.getChildren().add(albumContainer);
            }
        }
        catch (Exception e){
            //TODO: if no albums exist... msg?
        }
    }

    /**
     * Format and insert the first image in the given album to the given container.
     *
     * @param album the album from the database
     * @param albumContainer the container for each separate album
     */
    private void insertAlbumImageToContainer(ImageAlbum album, HBox albumContainer) {
        albumImage = new ImageView();
        albumImage.setFitHeight(300.0);
        albumImage.setFitWidth(533.0);
        albumImage.setPickOnBounds(true);
        albumImage.setPreserveRatio(true);
        albumImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                try{
                    switchToViewAlbum(mouseEvent);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        NTNU.IDATT1002.models.Image titleImage = album.getImages().get(0);
        Image image = ImageUtil.convertToFXImage(titleImage);
        albumImage.setImage(image);

        albumContainer.getChildren().add(albumImage);
    }

    /**
     * Att text elements from album to the container
     *
     * @param album the album to display
     * @param albumContainer the container for each separate album
     */
    private void insertAlbumTextToContainer(ImageAlbum album, HBox albumContainer) {
        //Creates a vbox so that nodes is aligned in a column
        VBox textContainer = new VBox();
        textContainer.setSpacing(5);
        textContainer.setPadding(new Insets(10, 0, 0, 25));
        textContainer.setPrefHeight(300);
        textContainer.setPrefWidth(987);

        insertAlbumTitle(album, textContainer);
        insertAlbumAuthor(album, textContainer);
        insertAlbumTags(album, textContainer);
        insertAlbumDescription(album, textContainer);

        albumContainer.getChildren().add(textContainer);
    }

    /**
     * Insert title of the given album to the given container
     * It is clickable, and switches to View Album page of that album
     *
     * @param album the album which title to display
     * @param textContainer container for text elements of an album
     */
    private void insertAlbumTitle(ImageAlbum album, VBox textContainer) {
        HBox content = new HBox();

        Text titleLabel = new Text("Title: ");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 48));

        Text title = new Text(album.getTitle());
        title.setFont(Font.font("System",48));

        content.getChildren().addAll(titleLabel, title);
        content.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                try{
                    switchToViewAlbum(mouseEvent);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        textContainer.getChildren().add(content);
    }

    /**
     * Insert author of the given album to the given container
     *
     * @param album the album which author to display
     * @param textContainer container for text elements of an album
     */
    private void insertAlbumAuthor(ImageAlbum album, VBox textContainer) {
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
    private void insertAlbumTags(ImageAlbum album, VBox textContainer) {
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
    private void insertAlbumDescription(ImageAlbum album, VBox textContainer) {
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
     * Method to open specific albums. It takes a clicked element within an album container
     * and finds the fx:id of the main parent, (who's id is the same as the album in the database), and
     * passes the value to Data Exchange so that Image View will know which album was clicked
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
