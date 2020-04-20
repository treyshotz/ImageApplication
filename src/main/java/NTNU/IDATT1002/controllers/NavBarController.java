package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.service.UserService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class NavBarController {

    public ImageView navBarLogo;
    public TextField navBarSearch;
    public Button navBarSearchBtn;
    public Button navBarExplore;
    public Button navBarMap;
    public Button navBarUpload;
    public Button navBarAlbums;

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
        if (!navBarSearch.getText().isEmpty()){
            App.ex.setSearchField(navBarSearch.getText());
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

    public void logOut(ActionEvent actionEvent) throws IOException {
        UserService.logOut();
        App.ex.setChosenAlbumId(null);
        App.setRoot("login");
    }
}
