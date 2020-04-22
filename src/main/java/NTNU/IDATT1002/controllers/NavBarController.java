package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.service.UserService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Class for controlling actions in the navigation bar
 *
 * @version 1.0 20.04.2020
 */
public class NavBarController {

    public ImageView navBarLogo;
    public TextField navBarSearch;
    public Button navBarSearchBtn;
    public Button navBarExplore;
    public Button navBarMap;
    public Button navBarUpload;
    public Button navBarAlbums;
    public Button navBarLogOut;
    public Button returnButton;

    /**
     * Method that changes scene to Main page
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToMain(MouseEvent mouseEvent) throws IOException {
        doBeforePageExit();

        App.ex.setChosenAlbumId(null);
        App.setRoot("main");
    }

    /**
     * If {@link DataExchange} has a previous page it changes
     * to it.
     * @param actionEvent
     * @throws IOException
     */
    public void goToPrevious(ActionEvent actionEvent) throws IOException {
        doBeforePageExit();

        String previousPage = App.ex.previousPage();
        if (previousPage != null)
            App.setRoot(previousPage);

    }

    /**
     * Method that changes scene to Search page. It reads the value of the search
     * field and if not empty it is passed to {@link DataExchange}
     * @param actionEvent
     * @throws IOException
     */
    public void switchToSearch(ActionEvent actionEvent) throws IOException {
        doBeforePageExit();

        if (!navBarSearch.getText().isEmpty())
            App.ex.setSearchField(navBarSearch.getText());


        App.ex.setChosenAlbumId(null);
        App.setRoot("search");
    }

    /**
     * Method that changes scene to Explore page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToExplore(ActionEvent actionEvent) throws IOException {
        doBeforePageExit();

        App.ex.setChosenAlbumId(null);
        App.setRoot("explore");
    }

    /**
     * Method that changes scene to Albums page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToAlbums(ActionEvent actionEvent) throws IOException {
        doBeforePageExit();

        App.ex.setChosenAlbumId(null);
        App.setRoot("explore_albums");
    }

    /**
     * Method that changes scene to Map page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToMap(ActionEvent actionEvent) throws IOException {
        doBeforePageExit();

        App.ex.setChosenAlbumId(null);
        App.setRoot("map");
    }

    /**
     * Method that changes scene to Upload page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToUpload(ActionEvent actionEvent) throws IOException {
        doBeforePageExit();

        App.ex.setChosenAlbumId(null);
        App.setRoot("upload");
    }

    /**
     * Method for logging out user. Also empties the list of
     * visited pages and chosen album in {@link DataExchange}.
     * @param actionEvent
     * @throws IOException
     */
    public void logOut(ActionEvent actionEvent) throws IOException {
        doBeforePageExit();

        App.ex.emptyPageLog();
        UserService.logOut();
        App.ex.setChosenAlbumId(null);
        App.setRoot("login");
    }

    /**
     * Actions to perform when exiting the page.
     * Can be implemented by subclasses to define custom actions on page exit.
     */
    public void doBeforePageExit(){

    };
}
