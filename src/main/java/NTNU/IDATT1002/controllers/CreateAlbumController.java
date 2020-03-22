package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CreateAlbumController {
    public TextField tbar_search;
    public ImageView tbar_logo;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;
    public TextField album_title_field;
    public TextField album_tag_field;
    public TextArea album_desc_field;
    public Pane metadata_pane;
    public GridPane image_grid;
    public Button add_images_button;
    public Button create_album_button;
    public Button tbar_albums;
    public Button tbar_searchBtn;

    public void switchToSearch(ActionEvent actionEvent) throws IOException {
        App.setRoot("search");
    }

    public void switchToUpload(ActionEvent actionEvent) throws IOException {
        App.setRoot("upload");
    }

    public void switchToMap(ActionEvent actionEvent) throws IOException {
        App.setRoot("map");
    }

    public void switchToExplore(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore");
    }

    public void switchToMain(MouseEvent mouseEvent) throws IOException {
        App.setRoot("main");
    }

    public void switchToAlbums(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore_albums");
    }
}
