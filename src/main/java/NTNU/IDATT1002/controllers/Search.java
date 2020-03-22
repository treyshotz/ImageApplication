package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the buttons and changeable elements on search.fxml,
 * a page where you can search for images and sort them
 * @version 1.0 22.03.2020
 */
public class Search implements Initializable {

    public ImageView tbar_logo;
    public TextField tbar_search;
    public Button tbar_searchBtn;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;

    public Text search_result;
    public ScrollPane scrollpane;
    public ChoiceBox sorted_by_choicebox;

    public Button footer_previousBtn;
    public Button footer_nextBtn;
    public Button tbar_albums;
    public Pane pane1;
    public ImageView picture;
    public Text tag_field;
    public Text title_field;
    public Text desc_field;
    public Button openPic_btn;
    public Pane pane11;
    public ImageView picture1;
    public Text tag_field1;
    public Text title_field1;
    public Text desc_field1;
    public Button openPic_btn1;
    public Pane pane12;
    public ImageView picture2;
    public Text tag_field2;
    public Text title_field2;
    public Text desc_field2;
    public Button openPic_btn2;
    public Pane pane13;
    public ImageView picture3;
    public Text tag_field3;
    public Text title_field3;
    public Text desc_field3;
    public Button openPic_btn3;
    public Pane pane14;
    public ImageView picture4;
    public Text tag_field4;
    public Text title_field4;
    public Text desc_field4;
    public Button openPic_btn4;


    /**
     * Method that writes the word that is searched for
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
    if (!App.ex.getSearchField().isEmpty()){
            search_result.setText(App.ex.getSearchField());
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
     * Method for opening the chosen picture
     * @param actionEvent
     * @throws IOException
     */
    public void switchToPicture(ActionEvent actionEvent) throws IOException {
        //TODO: Make method that opens the chosen picture
        App.setRoot("view_picture");
    }
}
