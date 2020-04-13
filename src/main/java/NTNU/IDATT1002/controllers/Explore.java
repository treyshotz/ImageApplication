package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.service.ImageService;
import NTNU.IDATT1002.service.TagService;
import NTNU.IDATT1002.utils.ImageUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import java.util.stream.Collectors;

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
        List<NTNU.IDATT1002.models.Image> images = new ImageService(App.ex.getEntityManager()).getAllImages();
        //Limited elements to 15 since grid pane since is 3x5
        //Can implement automatic row adding when this limit exceeded later
        for(int i = 0; i < images.size() && i < 100; i++) {
            //Row and column in gripdane
            int column = i%3;
            int row = (i-column)/3;

            //Add rows
            if(images.size() > 15){
                gridPane.setMinHeight(1600 + (((i-15)*160)));
                for (int j = 0; j < ((i-15)/3); j++){
                    gridPane.addRow(j);
                }
            }

            //Make vbox container for content
            VBox vBox = new VBox();
            vBox.setPrefHeight(400);
            vBox.setPrefWidth(400);
            vBox.setMaxHeight(400);
            vBox.setAlignment(Pos.CENTER);
            //vBox.setStyle("-fx-background-color: #999999;");

            //Image container
            ImageView imageView = new ImageView();
            imageView.setId(String.valueOf(images.get(i).getId()));
            imageView.setImage(ImageUtil.convertToFXImage(images.get(i)));
            imageView.setFitHeight(250);
            imageView.setFitWidth(400);
            imageView.pickOnBoundsProperty().setValue(true);
            imageView.setPreserveRatio(true);
            //Link to view image page
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
            String tagsAsString = TagService.getTagsAsString(images.get(i).getTags());
            Text tag = new Text("TAGS:\n " + tagsAsString);
            tag.setFont(Font.font("System Bold", 18));

            //Add elements to vbox
            vBox.getChildren().addAll(imageView, title, tag);

            //Add vbox to gridpane
            gridPane.add(vBox, column, row);
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
        long imageId = 0;
        Node node = (Node) mouseEvent.getSource();
        if (node.getId() != null){
                imageId = Long.parseLong(node.getId());
        }

        if (imageId != 0) {
            App.ex.setChosenImg(imageId);
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
