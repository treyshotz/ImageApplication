package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class UploadedPhotoController {
    public TextField tbar_search;
    public ImageView tbar_logo;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;
    public TextField title_field;
    public TextField tag_field;
    public TextArea desc_field;
    public Button accept_button;
    public ImageView uploaded_image;

    public void switchToSearch(ActionEvent actionEvent) {

    }

    public void switchToUpload(ActionEvent actionEvent) {

    }

    public void switchToMap(ActionEvent actionEvent) {

    }

    public void switchToExplore(ActionEvent actionEvent) {

    }

    public void uploadPhoto(ActionEvent actionEvent) throws IOException {
        //write method to accept and upload the photo with chosen settings, titles etc and then setRoot to logged-in page
        App.setRoot("logged-in");
    }
}
