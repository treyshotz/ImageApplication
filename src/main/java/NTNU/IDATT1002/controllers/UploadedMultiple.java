package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Controls the buttons and changeable elements on upload_multiple.fxml,
 * a page where you add descriptions to your selected images
 * @version 1.0 22.03.2020
 */
public class UploadedMultiple {


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
    public Button tbar_albums;

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
     * @param actionEvent
     * @throws IOException
     */
    public void switchToUpload(ActionEvent actionEvent) throws IOException {
        App.setRoot("upload");
    }

    /**
     * Method for uploading several images to database with title, tags and description
     * Image itself is not stored but URL is
     * @param actionEvent
     * @throws IOException
     */
    public void uploadMultiple(ActionEvent actionEvent) throws IOException {
        //TODO: write method to accept and upload the photo with chosen settings, titles..
        App.setRoot("main");
    }
}
