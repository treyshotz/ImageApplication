package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class LoggedInController {
    public TextField tbar_search;
    public ImageView tbar_logo;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;
    public Button main_upload;
    public Button tbar_searchButton;

    public void switchToSearch(ActionEvent actionEvent) throws IOException {
        App.setRoot("search_page");
    }

    public void switchToExplore(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore_page");
    }

    public void switchToMap(ActionEvent actionEvent) {

    }

    public void switchToUpload(ActionEvent actionEvent) {

    }
}