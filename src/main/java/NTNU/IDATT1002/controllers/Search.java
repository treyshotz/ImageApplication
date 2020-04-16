package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.service.ImageService;
import NTNU.IDATT1002.service.TagService;
import NTNU.IDATT1002.utils.ImageUtil;
import NTNU.IDATT1002.utils.MetadataStringFormatter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Controls the buttons and changeable elements on search.fxml,
 * a page where you can search for images and sort them
 * @version 1.0 22.03.2020
 */
public class Search extends NavBarController implements Initializable {

    public Text search_result;
    public ScrollPane scrollpane;
    public VBox vBox;
    public Text amount;


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

        amount.setText(String.valueOf(images.size()));

        for(int i = 0; i < images.size(); i++) {
            HBox hBox = new HBox();
            hBox.setPrefHeight(300);
            hBox.setPrefWidth(1920);
            hBox.setAlignment(Pos.TOP_LEFT);

            Pane pane = new Pane();
            pane.setPrefWidth(1400);
            pane.setPrefHeight(300);

            ImageView imageView = new ImageView();
            imageView.setId(String.valueOf(images.get(i).getId()));
            imageView.setImage(ImageUtil.convertToFXImage(images.get(i)));
            imageView.setFitHeight(300);
            imageView.setFitWidth(500);
            imageView.pickOnBoundsProperty().setValue(true);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    try{
                        switchToViewImage(e);
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
            Text metadata_Field = setText(MetadataStringFormatter.format(images.get(i).getMetadata(), "\n"), 700, 126, "System Bold", 18);
            VBox metaBox = new VBox();
            metaBox.getChildren().add(metadata_Field);
            ScrollPane meta = new ScrollPane();
            meta.setMaxWidth(630);
            meta.setPrefWidth(630);
            meta.setContent(metaBox);
            meta.setLayoutX(700);
            meta.setLayoutY(126);
            meta.setMaxHeight(150);


            pane.getChildren().addAll(imageView, title, tag, desc, title_Field, tag_Field, meta);
            hBox.getChildren().add(pane);
            vBox.getChildren().add(hBox);
            vBox.setMinHeight(1550+(images.size()*310));
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
    public void switchToViewImage(MouseEvent mouseEvent) throws IOException {
        long imageId = 0;
        Node node = (Node) mouseEvent.getSource();
        if (node.getId() != null){
            imageId = Long.parseLong(node.getId());
        }

        if (imageId != 0) {
            App.ex.setChosenImg(imageId);
            App.setRoot("view_image");
        }
    }
}
