package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.service.ImageService;
import NTNU.IDATT1002.service.TagService;
import NTNU.IDATT1002.utils.ImageUtil;
import NTNU.IDATT1002.utils.MetadataStringFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Controls the buttons and changeable elements on view_.fxml,
 * a page where get a more detailed view of a image
 *
 * @version 1.0 22.03.2020
 */
public class ViewImage extends NavBarController implements Initializable{

    @FXML
    public ImageView imageContainer;
    public Text imageTagsField;
    public Text imageTitleField;
    public Text imageMetadataField;

    /**
     * Generates content on the page based on current clicked image id
     * in {@link DataExchange}
     * @param url
     * @param resourceBundle
     */
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
            imageTitleField.setText(image.getTitle());
            imageTagsField.setText(TagService.getTagsAsString(image.getTags()));
            imageMetadataField.setText(MetadataStringFormatter.format(image.getMetadata(), "\n"));
            imageMetadataField.setId(String.valueOf(image.getId()));
            imageMetadataField.setOnMouseClicked(this::openPopUpMetadata);
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
            stage.setWidth(1000);
            stage.setHeight(600);
            stage.setScene(scene);
            stage.showAndWait();
        }
    }

    /**
     * Makes a new stage and displays ALL metadata of the clicked image
     * @param mouseEvent
     */
    public void openPopUpMetadata(MouseEvent mouseEvent){
        Node clickedObject = (Node) mouseEvent.getSource();
        ImageService imageService = new ImageService(App.ex.getEntityManager());
        Optional<NTNU.IDATT1002.models.Image> findImage = imageService.findById(Long.parseLong(clickedObject.getId()));
        findImage.ifPresent(foundImage -> {
            Stage stage = new Stage();
            stage.setWidth(400);
            stage.setHeight(600);

            Text metadataLabel = new Text("All metadata: ");
            Text metadata = new Text(foundImage.getMetadata().getMiscMetadata());
            ScrollPane scrollPane = new ScrollPane();

            VBox textContainer = new VBox();
            metadata.wrappingWidthProperty().bind(stage.widthProperty().add(-25));
            textContainer.getChildren().addAll(metadataLabel, metadata);
            textContainer.setAlignment(Pos.TOP_LEFT);

            scrollPane.setFitToWidth(true);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setContent(textContainer);
            scrollPane.setMinHeight(textContainer.getMinHeight());

            Scene scene = new Scene(scrollPane);
            stage.setScene(scene);
            stage.showAndWait();
        });
    }
}
