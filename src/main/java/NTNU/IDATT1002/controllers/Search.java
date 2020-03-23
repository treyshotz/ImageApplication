package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import com.sun.scenario.effect.impl.state.HVSeparableKernel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
        List<String> urls = Arrays.asList("@../../Images/placeholder-1920x1080.png", "@../../Images/party.jpg", "@../../Images/placeholderLogo.png");
        for(int i = 0; i < urls.size(); i++) {
            HBox h = new HBox();
            h.setPrefHeight(300);
            h.setPrefWidth(1920);
            h.setAlignment(Pos.CENTER);
            h.setStyle("-fx-background-color: #999999;");

            Pane p = new Pane();
            p.setPrefWidth(1400);
            p.setPrefHeight(300);

            ImageView iV = new ImageView();
            iV.setImage(new Image(urls.get(i)));
            iV.setFitHeight(300);
            iV.setFitWidth(500);
            iV.pickOnBoundsProperty().setValue(true);
            iV.setPreserveRatio(true);
            iV.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
            Text title_Field = setText(urls.get(i), 700, 66, "System Bold", 48);
            Text tag_Field = setText("####", 700, 97, "System Bold", 24);
            Text desc_Field = setText("####", 700, 126, "System Bold", 18);

            p.getChildren().addAll(iV, title, tag, desc, title_Field, tag_Field, desc_Field);
            h.getChildren().add(p);
            vBox.getChildren().add(h);
        }
    }

    public Text setText(String text, int layoutX, int layoutY, double wrappingWidth, String fontName, double fontSize){
        Text t = new Text(text);
        t.setLayoutX(layoutX);
        t.setLayoutY(layoutY);
        t.setWrappingWidth(wrappingWidth);
        t.setFont(Font.font(fontName, fontSize));
        return t;
    }

    public Text setText(String text, int layoutX, int layoutY, String fontName, double fontSize){
        Text t = new Text(text);
        t.setLayoutX(layoutX);
        t.setLayoutY(layoutY);
        t.setFont(Font.font(fontName, fontSize));
        return t;
    }

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
