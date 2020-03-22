package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ViewPicture {
    public ImageView tbar_logo;
    public TextField tbar_search;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;
    public ImageView picture;
    public Text picture_tagsField;
    public Text picture_title_field;
    public Text desc_textField;
    public Pane metadata_pane;
    public Button tbar_searchBtn;
    public Button tbar_albums;

    public void switchToSearch(ActionEvent actionEvent) {

    }

    public void switchToExplore(ActionEvent actionEvent) {

    }

    public void switchToMap(ActionEvent actionEvent) {

    }

    public void switchToUpload(ActionEvent actionEvent) {

    }

    public void openPopUpPicture(MouseEvent mouseEvent) {
        //method that opens pop-up of picture
    }

    public void switchToMain(MouseEvent mouseEvent) throws IOException {
        App.setRoot("main");
    }

    public void switchToAlbums(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore_albums");
    }
}
