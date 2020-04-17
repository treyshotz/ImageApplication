package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controls the buttons and changeable elements on explore.fxml,
 * a page where you explore images
 * @version 1.0 22.03.2020
 */
public class Explore extends NavBarController implements Initializable {

    public ScrollPane scrollPane;
    public GridPane gridPane;
    public Button footer_previousBtn;
    public Button footer_nextBtn;

    private List<NTNU.IDATT1002.models.Image> images;
    private int start;
    private int end;

    /**
     * Method that runs when explore.fxml is set as scene
     * Generates content based on a list of images
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        images = new ImageService(App.ex.getEntityManager()).getAllImages();
        start = 0;
        end = 15;

        generateImages(start, end);
    }

    public void generateImages(int start, int end){
        gridPane.getChildren().clear();
        //Limited elements to 15 since grid pane since is 3x5
        for(int i = start; i < images.size() && i < end; i++) {
            int index = i%15;
            //Row and column in gripdane
            int column = index%3;
            int row = (index-column)/3;

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
                        switchToViewImage(e);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            //Text describing the picture's title and tag
            Text title = new Text(images.get(i).getTitle());
            title.setFont(Font.font("System Bold", 24));
            String tagsAsString = TagService.getTagsAsString(images.get(i).getTags());
            Text tag = new Text(tagsAsString);
            tag.setFont(Font.font("System Bold", 18));

            //Add elements to vbox
            vBox.getChildren().addAll(imageView, title, tag);

            //Add vbox to gridpane
            gridPane.add(vBox, column, row);
        }
    }

    /**
     * Method that changes scene to View Picture page for the image that was clicked
     * @param mouseEvent
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

    /**
     * Method that updates content to previous "page"
     * @param actionEvent
     * @throws IOException
     */
    public void switchToPrevious(ActionEvent actionEvent) throws IOException {
        if (start - 15 >= 0){
            start -= 15;
            end -= 15;
            generateImages(start, end);

            if (start == 0){
                footer_previousBtn.setDisable(true);
            }
            footer_nextBtn.setDisable(false);
        }
    }

    /**
     * Method that updates content to next "page"
     * @param actionEvent
     * @throws IOException
     */
    public void switchToNext(ActionEvent actionEvent) throws IOException {
        if (start + 15 < images.size()){
            start += 15;
            end += 15;
            generateImages(start, end);

            if (end >= images.size()){
                footer_nextBtn.setDisable(true);
            }
            footer_previousBtn.setDisable(false);
        }
    }
}
