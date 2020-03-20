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

public class ExploreAlbumsController {
    public ImageView tbar_logo;
    public TextField tbar_search;
    public Button tbar_map;
    public Button tbar_upload;
    public ScrollPane scrollpane;
    public Button footer_previous_page;
    public Button footer_next_page;
    public ImageView tbar_logo1;
    public TextField tbar_search1;
    public Button tbar_map1;
    public Button tbar_upload1;
    public Button tbar_searchButton;
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

    public void switchToSearch(ActionEvent actionEvent) {

    }

    public void switchToMain(MouseEvent mouseEvent) {

    }

    public void switchToMap(ActionEvent actionEvent) {

    }

    public void switchToUpload(ActionEvent actionEvent) {

    }

    public void switchToPrevious(ActionEvent actionEvent) {

    }

    public void switchToNext(ActionEvent actionEvent) throws IOException {
        App.setRoot("search_page_2");
    }

    public void switchToCreateAlbum(ActionEvent actionEvent) {

    }

    public void switchToExplore(ActionEvent actionEvent) {

    }
}
