package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controls the buttons and changeable elements on explore.fxml,
 * a page where you explore images
 * @version 1.0 22.03.2020
 */
public class Explore implements Initializable {

    public ImageView tbar_logo;
    public TextField tbar_search;
    public Button tbar_searchBtn;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;
    public Button tbar_albums;
    public ScrollPane scrollPane;
    public GridPane gridPane;
    public Button footer_previousBtn;
    public Button footer_nextBtn;


    /**
     * Method that runs when explore.fxml is set as scene
     * Generates content based on a list of images
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> urls = Arrays.asList("@../../Images/placeholder-1920x1080.png", "@../../Images/party.jpg", "@../../Images/placeholderLogo.png", "@../../Images/party.jpg","@../../Images/placeholder-1920x1080.png", "@../../Images/placeholderLogo.png", "@../../Images/placeholder-1920x1080.png", "@../../Images/placeholderLogo.png", "@../../Images/party.jpg", "@../../Images/placeholderLogo.png", "@../../Images/party.jpg","@../../Images/placeholder-1920x1080.png", "@../../Images/placeholderLogo.png", "@../../Images/placeholder-1920x1080.png", "@../../Images/party.jpg");
        //Limited elements to 15 since grid pane since is 3x15
        //Can implement automatic row adding when this limit exceeded later
        for(int i = 0; i < urls.size() && i < 15; i++) {
            //Row and column in gripdane
            int column = i%3;
            int row = (i-column)/3;

            //Make vbox container for content
            VBox v = new VBox();
            v.setPrefHeight(400);
            v.setPrefWidth(400);
            v.setAlignment(Pos.CENTER);
            v.setStyle("-fx-background-color: #999999;");

            //Image container
            ImageView iV = new ImageView();
            iV.setImage(new Image(urls.get(i)));
            iV.setFitHeight(250);
            iV.setFitWidth(400);
            iV.pickOnBoundsProperty().setValue(true);
            iV.setPreserveRatio(true);
            //Link to view image page
            iV.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    try{
                        switchToPicture(e);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            //Text describing the picture's title and tag
            Text title = new Text("TITLE:");
            title.setFont(Font.font("System Bold", 24));
            Text tag = new Text("#TAGS");
            tag.setFont(Font.font("System Bold", 18));

            //Add elements to vbox
            v.getChildren().addAll(iV, title, tag);

            //Add vbox to gridpane
            gridPane.add(v, column, row);
        }
    }

    /**
     * Method that changes scene to Main page
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToMain(MouseEvent mouseEvent) throws IOException {
        App.setRoot("main");
    }

    /**
     * Method that changes scene to Search page. It reads the value of the search
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
     * Method that changes scene to Explore page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToExplore(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore");
    }

    /**
     * Method that changes scene to Albums page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToAlbums(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore_albums");
    }

    /**
     * Method that changes scene to Map page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToMap(ActionEvent actionEvent) throws IOException {
        App.setRoot("map");
    }

    /**
     * Method that changes scene to Upload page
     * @param actionEvent the mouse has done something
     * @throws IOException this page does not exist
     */
    public void switchToUpload(ActionEvent actionEvent) throws IOException {
        App.setRoot("upload");
    }

    /**
     * Method that changes scene to View Picture page for the image that was clicked
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToPicture(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getSource() instanceof ImageView){
            App.ex.setChosenImg(((ImageView) mouseEvent.getSource()).getImage().getUrl());
            App.setRoot("view_picture");
        }
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
}
