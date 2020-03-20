package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class MapPageController {
    public TextField tbar_search;
    public ImageView tbar_logo;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;
    public Button map_search_button;
    public TextField map_search_field;

    public void switchToSearch(ActionEvent actionEvent) throws IOException {
        App.setRoot("search_page");
    }

    public void switchToUpload(ActionEvent actionEvent) throws IOException {
        App.setRoot("upload_page");
    }

    public void switchToMap(ActionEvent actionEvent) throws IOException {
        App.setRoot("map_page");
    }

    public void switchToExplore(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore_page");
    }

    public void MapSearch(ActionEvent actionEvent) {

    }
}
