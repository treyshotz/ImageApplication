package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.service.ImageService;
import NTNU.IDATT1002.service.TagService;
import NTNU.IDATT1002.utils.ImageUtil;
import NTNU.IDATT1002.utils.MetaDataExtractor;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Controls the buttons and changeable elements on view_.fxml,
 * a page where get a more detailed view of a image
 * @version 1.0 22.03.2020
 */
public class ViewImage implements Initializable{
    public ImageView tbar_logo;
    public TextField tbar_search;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;
    public Button tbar_searchBtn;
    public Button tbar_albums;

    public ImageView imageContainer;
    public Text imageTagsField;
    public Text imageTitleField;
    public Text imageMetadataField;

    private ImageService imageService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EntityManager entityManager = App.ex.getEntityManager();
        imageService = new ImageService(entityManager);
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
