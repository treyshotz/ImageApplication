package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Explore {

    public ImageView tbar_logo;
    public TextField tbar_search;
    public Button tbar_searchBtn;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;
    public ScrollPane scrollPane;
    public Button footer_previousBtn;
    public Button footer_nextBtn;
    public Button tbar_albums;
    public Pane pane1_1;
    public Pane pane1_2;
    public Pane pane1_3;
    public Pane pane2_1;
    public Pane pane2_2;
    public Pane pane2_3;
    public Pane pane3_1;
    public Pane pane3_2;
    public Pane pane3_3;
    public Pane pane4_1;
    public Pane pane4_2;
    public Pane pane4_3;
    public Pane pane5_1;
    public Pane pane5_2;
    public Pane pane5_3;


    /**
     * Method that changes stage to Main page
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToMain(MouseEvent mouseEvent) throws IOException {
        App.setRoot("main");
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
     * Method for switching to Map page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToMap(ActionEvent actionEvent) throws IOException {
        App.setRoot("map");
    }

    /**
     * Method for switching to Search page. It reads the value of the search
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
     * Method for switching to Upload page
     * @param actionEvent the mouse has done something
     * @throws IOException this page does not exist
     */
    public void switchToUpload(ActionEvent actionEvent) throws IOException {
        App.setRoot("upload");
    }

    /**
     * Method that updates content to previous "page"
     * @param actionEvent
     * @throws IOException
     */
    public void switchToPrevious(ActionEvent actionEvent) throws IOException {
        //TODO: Make method that updates content to previous "page"
    }

    /**
     * Method that updates content to next "page"
     * @param actionEvent
     * @throws IOException
     */
    public void switchToNext(ActionEvent actionEvent) throws IOException {
        //TODO: Make method that updates content to next "page"
    }

    /**
     * Method for switching to Explore Albums page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToAlbums(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore_albums");
    }

    /**
     * Method that opens "View picture" page for the picture that got clicked
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToPicture(MouseEvent mouseEvent) throws IOException {
        //TODO: write method to switch to the correct picture depending on which pane gets clicked
        App.setRoot("view_picture");
    }
}
