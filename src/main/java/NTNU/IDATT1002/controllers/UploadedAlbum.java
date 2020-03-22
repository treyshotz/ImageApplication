package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class UploadedAlbum {


    public ImageView tbar_logo;
    public TextField tbar_search;
    public Button tbar_searchBtn;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;

    public TextField photo_title;
    public TextField photo_tag;
    public TextArea photo_desc;
    public ImageView photo_image;

    public TextField photo_title2;
    public TextField photo_tag2;
    public TextArea photo_desc2;
    public ImageView photo_image2;

    public TextField photo_title3;
    public TextField photo_tag3;
    public TextArea photo_desc3;
    public ImageView photo_image3;

    public TextField photo_title4;
    public TextField photo_tag4;
    public TextArea photo_desc4;
    public ImageView photo_image4;

    public Button acceptBtn;

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
     * Method for switching to Upload page.
     * @param actionEvent the mouse has done something
     * @throws IOException this page does not exist
     */
    public void switchToUpload(ActionEvent actionEvent) throws IOException {
        App.setRoot("upload");
    }

    /**
     * Method for uploading image with title, tags and description to database
     * Image itself is not stored but Url is
     * @param actionEvent the mouse has done something
     * @throws IOException this page does not exist
     */
    public void uploadAlbum(ActionEvent actionEvent) throws IOException {
        //TODO: write method to accept and upload the photo with chosen settings, titles etc and then setRoot to main page
        App.setRoot("main");
    }
}
