package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.ApplicationState;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.service.AlbumService;
import NTNU.IDATT1002.service.ImageService;
import NTNU.IDATT1002.service.TagService;
import NTNU.IDATT1002.utils.ImageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.hibernate.boot.jaxb.internal.stax.HbmEventReader;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controls the buttons and changeable elements on create_album.fxml,
 * a page where you create albums
 * @version 1.0 22.03.2020
 */
public class CreateAlbum implements Initializable {
    public TextField tbar_search;
    public ImageView tbar_logo;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;
    public TextField album_title_field;
    public TextField album_tag_field;
    public TextArea album_desc_field;
    public Pane metadata_pane;
    public GridPane image_grid;
    public Button add_images_button;
    public Button create_album_button;
    public Button tbar_albums;
    public Button tbar_searchBtn;
    public VBox fileContainer;

    private AlbumService albumService;
    private ImageService imageService;

    public CreateAlbum() {
        EntityManager entityManager = App.ex.getEntityManager();
        albumService = new AlbumService(entityManager);
        imageService = new ImageService(entityManager);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Image> allImages = imageService.getImageFromUser(ApplicationState.getCurrentUser());
        for (Image image : allImages){
            javafx.scene.image.Image convertedImage = ImageUtil.convertToFXImage(image);
            HBox container = new HBox();
            container.setPrefWidth(450);
            container.setAlignment(Pos.TOP_CENTER);
            ImageView imageView = new ImageView();
            imageView.setFitHeight(200);
            imageView.setFitWidth(350);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            imageView.setImage(convertedImage);
            CheckBox checkBox = new CheckBox();
            checkBox.setId(image.getId().toString());
            container.getChildren().addAll(imageView, checkBox);
            fileContainer.getChildren().add(container);
        }
    }

    /**
     * Method that changes stage to Main page
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToMain(MouseEvent mouseEvent) throws IOException {
        App.setRoot("main");
    }

    /**
     * Method that changes stage to Search page. It reads the value of the search
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
     * Method that changes stage to Explore page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToExplore(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore");
    }

    /**
     * Method that changes stage to Albums page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToAlbums(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore_albums");
    }

    /**
     * Method that changes stage to Map page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToMap(ActionEvent actionEvent) throws IOException {
        App.setRoot("map");
    }

    /**
     * Method that changes stage to Upload page
     * @param actionEvent the mouse has done something
     * @throws IOException this page does not exist
     */
    public void switchToUpload(ActionEvent actionEvent) throws IOException {
        App.setRoot("upload");
    }

    /**
     * Method that creates album based on the user input and checked images
     * @param actionEvent
     */
    public void createAlbum(ActionEvent actionEvent){
        String title =  album_title_field.getText();
        String description = album_desc_field.getText();
        String tags = album_tag_field.getText();
        List<Tag> tagsToAdd = TagService.getTagsFromString(tags);
        User user = ApplicationState.getCurrentUser();

        List<Node> imageContainers = new ArrayList<>(fileContainer.getChildren());
        List<String> checkedImagesId = new ArrayList<>();
        //Each image and checkbox has an hbox container
        imageContainers.forEach(hbox ->
                ((HBox) hbox).getChildren().stream()
                        //Filter children that is a checked checkbox
                        .filter(child -> child instanceof CheckBox && ((CheckBox) child).isSelected())
                        //Adds all checked image id
                        .forEach(checked -> checkedImagesId.add(checked.getId()))
        );

        //Find the users images and makes a filter on the checked images
        List<Image> albumImages = imageService.getImageFromUser(user).stream().filter(image -> checkedImagesId.contains(image.getId().toString())).collect(Collectors.toList());

        if (albumImages.size() > 0){
            Optional<Album> createdAlbum = albumService.createAlbum(title, description, user, tagsToAdd, albumImages);
            createdAlbum.ifPresent(album -> {
                App.ex.setChosenAlbumId(album.getId());
                try {
                    App.setRoot("view_album");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        else {
            Optional<Album> createdAlbum = albumService.createEmptyAlbum(title, description, user, tagsToAdd);
            createdAlbum.ifPresent(album -> {
                App.ex.setChosenAlbumId(album.getId());
                try {
                    App.setRoot("view_album");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
