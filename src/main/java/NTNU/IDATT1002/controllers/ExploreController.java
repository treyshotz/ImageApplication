package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ExploreController {
    public ImageView tbar_logo;
    public TextField tbar_search;
    public Button tbar_map;
    public Button tbar_upload;
    public ScrollPane scrollpane;
    public Button footer_previous_page;
    public Button footer_next_page;

    public void switchToMain(MouseEvent mouseEvent) throws IOException {
        App.setRoot("logged-in");
    }

    public void switchToSearch(ActionEvent actionEvent) throws IOException{
        App.setRoot("search_page");
    }

    public void switchToMap(ActionEvent actionEvent) throws IOException{
        App.setRoot("map_page");
    }

    public void switchToUpload(ActionEvent actionEvent) throws IOException{
        App.setRoot("upload_page");
    }

    public void switchToPrevious(ActionEvent actionEvent) {

    }

    public void switchToNext(ActionEvent actionEvent) {

    }
}
