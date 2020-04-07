package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Image;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


/**
 * Controls the buttons and changeable elements on search.fxml,
 * a page where you can search for images and sort them
 * @version 1.0 22.03.2020
 */
public class Search implements Initializable {

    public ImageView tbar_logo;
    public TextField tbar_search;
    public Button tbar_searchBtn;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;
    public Button tbar_albums;

    public Text search_result;
    public ScrollPane scrollpane;
    public ChoiceBox sorted_by_choicebox;
    public VBox vBox;



    /**
     * Method that writes the word that is searched for.
     * Also generates HBoxes with image title, tags...
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        if (!App.ex.getSearchField().isEmpty()){
            search_result.setText(App.ex.getSearchField());
        }

        List<NTNU.IDATT1002.models.Image> images = new ImageService(App.ex.getEntityManager()).searchResult(App.ex.getSearchField());

        for(int i = 0; i < images.size(); i++) {
            HBox hBox = new HBox();
            hBox.setPrefHeight(300);
            hBox.setPrefWidth(1920);
            hBox.setAlignment(Pos.CENTER);
            hBox.setStyle("-fx-background-color: #999999;");

            Pane pane = new Pane();
            pane.setPrefWidth(1400);
            pane.setPrefHeight(300);

            ImageView imageView = new ImageView();
            imageView.setImage(ImageUtil.convertToFXImage(images.get(i)));
            imageView.setFitHeight(300);
            imageView.setFitWidth(500);
            imageView.pickOnBoundsProperty().setValue(true);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    try{
                        switchToPicture(e);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            Text title = setText("TITLE:", 550, 66, 153, "System Bold", 48);
            Text tag = setText("TAG:", 550, 97, 70, "System Bold", 24);
            Text desc = setText("DESCRIPTION:", 550, 126, 129, "System Bold", 18);
            Text title_Field = setText("SKAL BILDENE HA TITTEL?", 700, 66, "System Bold", 48);
            String tagsString = TagService.getTagsAsString(images.get(i).getTags());
            Text tag_Field = setText(tagsString, 700, 97, "System Bold", 24);
            Text metadata_Field = setText(images.get(i).getMetadata().toString(), 700, 126, "System Bold", 18);

            pane.getChildren().addAll(imageView, title, tag, desc, title_Field, tag_Field, metadata_Field);
            hBox.getChildren().add(pane);
            vBox.getChildren().add(hBox);
        }
    }

    /**
     * Method that takes in a string of text and returns a text object
     * @param textIn
     * @param layoutX
     * @param layoutY
     * @param wrappingWidth
     * @param fontName
     * @param fontSize
     * @return
     */

    public Text setText(String textIn, int layoutX, int layoutY, double wrappingWidth, String fontName, double fontSize){
        Text text = new Text(textIn);
        text.setLayoutX(layoutX);
        text.setLayoutY(layoutY);
        text.setWrappingWidth(wrappingWidth);
        text.setFont(Font.font(fontName, fontSize));
        return text;
    }

    /**
     * Alternative setText method without wrappingWidth
     * @param textIn
     * @param layoutX
     * @param layoutY
     * @param fontName
     * @param fontSize
     * @return
     */

    public Text setText(String textIn, int layoutX, int layoutY, String fontName, double fontSize){
        Text text = new Text(textIn);
        text.setLayoutX(layoutX);
        text.setLayoutY(layoutY);
        text.setFont(Font.font(fontName, fontSize));
        return text;
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

    /**
     * Method for opening the chosen picture.
     * @param mouseEvent what is clicked on
     * @throws IOException
     */
    public void switchToPicture(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getSource() instanceof ImageView){
            App.ex.setChosenImg(((ImageView) mouseEvent.getSource()).getImage().getUrl());
            App.setRoot("view_picture");
        }
    }
}
