package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.service.ImageService;
import NTNU.IDATT1002.utils.ImageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Controls the buttons and changeable elements on view_.fxml,
 * a page where get a more detailed view of a picture
 * @version 1.0 22.03.2020
 */
public class ViewPicture implements Initializable{
    public ImageView tbar_logo;
    public TextField tbar_search;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;
    public ImageView picture;
    public Text picture_tagsField;
    public Text picture_title_field;
    public Text picture_descField;
    public Pane metadata_pane;
    public Button tbar_searchBtn;
    public Button tbar_albums;
    private ImageService imageService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EntityManager entityManager = App.ex.getEntityManager();
        imageService = new ImageService(entityManager);
        Long currentImageId = App.ex.getChosenImg();
        NTNU.IDATT1002.models.Image foundImage = imageService.findById(currentImageId).get();
        Image image = ImageUtil.convertToFXImage(foundImage);

        picture.setImage(image);

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
     * Method that opens large version of image in popup
     * @param mouseEvent
     */
    public void openPopUpPicture(MouseEvent mouseEvent) {
        //method that opens pop-up of picture
    }
}
