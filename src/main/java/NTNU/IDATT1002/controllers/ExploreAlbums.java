package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Controls the buttons and changeable elements on explore_albums.fxml,
 * a page where you explore albums
 * @version 1.0 22.03.2020
 */
public class ExploreAlbums {
    public ImageView tbar_logo;
    public TextField tbar_search;
    public Button tbar_map;
    public Button tbar_upload;
    public ScrollPane scrollpane;
    public Button footer_previous_page;
    public Button footer_next_page;
    public Button tbar_searchBtn;
    public Button tbar_explore;
    public Text album_amount;
    public ChoiceBox sorted_by_choicebox;
    public Button create_album_button;
    public ImageView album_image;
    public Text album_author;
    public Text album_title;
    public Text album_desc;
    public Text album_tags;
    public Text album_author2;
    public Text album_title2;
    public Text album_desc2;
    public Text album_tags2;
    public ImageView album_image2;
    public ImageView album_image3;
    public Text album_author3;
    public Text album_title3;
    public Text album_desc3;
    public Text album_tags3;
    public ImageView album_image4;
    public Text album_author4;
    public Text album_title4;
    public Text album_desc4;
    public Text album_tags4;
    public ImageView album_image5;
    public Text album_author5;
    public Text album_title5;
    public Text album_desc5;
    public Text album_tags5;
    public Button tbar_albums;
    public Button open_album4;
    public Button open_album3;
    public Button open_album2;
    public Button open_album1;
    public Button open_album;

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
     * Method that changes stage to Create Album page
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
        //TODO: write method to open the specific album chosen
        App.setRoot("view_album");
    }
}
