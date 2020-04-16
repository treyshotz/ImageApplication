package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.service.ImageService;
import NTNU.IDATT1002.service.TagService;
import NTNU.IDATT1002.utils.ImageUtil;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Controls the buttons and changeable elements on view_.fxml,
 * a page where get a more detailed view of a image
 * @version 1.0 22.03.2020
 */
public class ViewImage implements Initializable{

    public ImageView imageContainer;
    public Text imageTagsField;
    public Text imageTitleField;
    public Text imageMetadataField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EntityManager entityManager = App.ex.getEntityManager();
        ImageService imageService = new ImageService(entityManager);
        Long currentImageId = App.ex.getChosenImg();
        Optional<NTNU.IDATT1002.models.Image> foundImage = imageService.findById(currentImageId);
        imageContainer.setFitHeight(540);
        imageContainer.setFitWidth(960);
        foundImage.ifPresent(image -> {
            Image convertedImage = ImageUtil.convertToFXImage(image);
            imageContainer.setImage(convertedImage);
            imageTitleField.setText("KAN VI PLIS LEGGE INN TITTEL I DB");
            imageTagsField.setText(TagService.getTagsAsString(image.getTags()));
            imageMetadataField.setText(image.getMetadata().toString());
        });
    }

    /**
     * Makes a new stage and display the clicked image in max size
     * @param mouseEvent
     */
    public void openPopUpImage(MouseEvent mouseEvent) {
        Node clickedObject = (Node) mouseEvent.getSource();
        if (clickedObject instanceof ImageView){
            Stage stage = new Stage();
            BorderPane pane = new BorderPane();

            ImageView imageView = new ImageView();
            imageView.fitWidthProperty().bind(stage.widthProperty());
            imageView.fitHeightProperty().bind(stage.heightProperty());
            imageView.setPreserveRatio(true);
            imageView.setPickOnBounds(true);
            imageView.setImage(((ImageView) clickedObject).getImage());
            pane.setCenter(imageView);


            Scene scene = new Scene(pane);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.showAndWait();
        }
    }
}
