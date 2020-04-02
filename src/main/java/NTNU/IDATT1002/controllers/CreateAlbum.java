package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.ApplicationState;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.service.AlbumService;
import NTNU.IDATT1002.service.TagService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

/**
 * Controls the buttons and changeable elements on create_album.fxml,
 * a page where you create albums
 * @version 1.0 22.03.2020
 */
public class CreateAlbum {
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

    private AlbumService albumService;

    public CreateAlbum() {
        EntityManager entityManager = App.ex.getEntityManager();
        albumService = new AlbumService(entityManager);
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
     * Create an empty album. The user will default to the currently logged in user.
     *
     * @param actionEvent
     */
    public void createEmptyAlbum(ActionEvent actionEvent) {
        String title =  album_title_field.getText();
        String description = album_desc_field.getText();
        String tags = album_tag_field.getText();
        List<Tag> tagsToAdd = TagService.getTagsFromString(tags);
        User user = ApplicationState.getCurrentUser();

        albumService.createEmptyAlbum(title, description, user, tagsToAdd);
    }
}
