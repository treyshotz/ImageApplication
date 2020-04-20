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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
    public Button footer_previousBtn;
    public Button footer_nextBtn;
    public HBox rowOfResults1;
    public HBox rowOfResults2;
    public HBox rowOfResults3;
    public HBox rowOfResults4;
    public HBox rowOfResults5;
    public VBox containerOfElements;

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
        //Limited elements to 15 since grid pane since is 3x5
        for(int i = start; i < images.size() && i < end; i++) {
            int index = i%15;
            //Row and column in gripdane
            int column = index%3;
            int r = (index-column)/3;

            //Make vbox container for content
            containerOfElements = new VBox();
            containerOfElements.setMinSize(100,100);
            containerOfElements.setPrefSize(520,310);
            containerOfElements.setAlignment(Pos.TOP_CENTER);



            //Make stackpane to resize images
            StackPane stackPane = new StackPane();
            stackPane.setMinSize(100,100);
            stackPane.prefWidthProperty().bind(containerOfElements.widthProperty());
            stackPane.prefHeightProperty().bind(containerOfElements.heightProperty());

            //Image container
            ImageView imageView = new ImageView();
            imageView.getStyleClass().add("exploreImages");
            imageView.setFocusTraversable(true);
            imageView.setId(String.valueOf(images.get(i).getId()));
            imageView.setImage(ImageUtil.convertToFXImage(images.get(i)));
            imageView.fitHeightProperty().bind(stackPane.heightProperty());
            imageView.fitWidthProperty().bind(stackPane.widthProperty());
            imageView.pickOnBoundsProperty().setValue(true);
            imageView.setPreserveRatio(true);
            //Link to view image page using tab functionality
            imageView.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode().equals(KeyCode.ENTER)){
                        try {
                            switchToViewImageEnter(keyEvent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

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
            stackPane.getChildren().add(imageView);
            containerOfElements.getChildren().addAll(stackPane, title, tag);

            //add vBox to correct hBox
            if (r==1){
                rowOfResults1.getChildren().addAll(containerOfElements);
            } else if (r==2){
                rowOfResults2.getChildren().addAll(containerOfElements);
            } else if (r==3){
                rowOfResults3.getChildren().addAll(containerOfElements);
            } else if (r==4){
                rowOfResults4.getChildren().addAll(containerOfElements);
            } else {
                rowOfResults5.getChildren().addAll(containerOfElements);
            }
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
     * Method that changes scene to View Picture page for the image that is in focus when Enter key is pressed
     * @param keyEvent
     * @throws IOException
     */
    public void switchToViewImageEnter(KeyEvent keyEvent) throws IOException {
        long imageInFocusId = 0;
        Node node = (Node) keyEvent.getSource();
        if (node.getId() != null){
            imageInFocusId = Long.parseLong(node.getId());
        }

        if (imageInFocusId != 0) {
            App.ex.setChosenImg(imageInFocusId);
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
