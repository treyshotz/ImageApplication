package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.ApplicationState;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.service.ImageService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controls the buttons and changeable elements on upload_single.fxml,
 * a page where you add descriptions to your selected image
 *
 * @version 1.0 22.03.2020
 */
public class UploadedSingle implements Initializable {

  ImageService imageService;

  public ImageView tbar_logo;
  public TextField tbar_search;
  public Button tbar_searchBtn;
  public Button tbar_explore;
  public Button tbar_map;
  public Button tbar_upload;

  public TextField photo_title;
  public TextField photo_tag;
  public TextArea photo_desc;
  public ImageView photo_image;

  public Button acceptBtn;
  public Button tbar_albums;


  /**
   * Method that runs when the controller is loaded
   * Sets the image url on the page to be the uploaded images url
   *
   * @param location
   * @param resources
   */
  public void initialize(URL location, ResourceBundle resources) {
    photo_image.setImage(new Image(App.ex.getUploadedFiles()
            .get(0)
            .toURI()
            .toString()));

    EntityManager entityManager = App.ex.getEntityManager();
    imageService = new ImageService(entityManager);
  }

  /**
   * Method that changes stage to Main page
   *
   * @param mouseEvent
   * @throws IOException
   */
  public void switchToMain(MouseEvent mouseEvent) throws IOException {
    App.setRoot("main");
  }

  /**
   * Method that changes stage to Search page. It reads the value of the search
   * field and if not empty it is passed to dataexchange
   *
   * @param actionEvent
   * @throws IOException
   */
  public void switchToSearch(ActionEvent actionEvent) throws IOException {
    if (!tbar_search.getText().isEmpty()) {
      App.ex.setSearchField(tbar_search.getText());
    }
    App.setRoot("search");
  }

  /**
   * Method that changes stage to Explore page
   *
   * @param actionEvent
   * @throws IOException
   */
  public void switchToExplore(ActionEvent actionEvent) throws IOException {
    App.setRoot("explore");
  }

  /**
   * Method that changes stage to Albums page
   *
   * @param actionEvent
   * @throws IOException
   */
  public void switchToAlbums(ActionEvent actionEvent) throws IOException {
    App.setRoot("explore_albums");
  }

  /**
   * Method that changes stage to Map page
   *
   * @param actionEvent
   * @throws IOException
   */
  public void switchToMap(ActionEvent actionEvent) throws IOException {
    App.setRoot("map");
  }

  /**
   * Method that changes stage to Upload page
   *
   * @param actionEvent
   * @throws IOException
   */
  public void switchToUpload(ActionEvent actionEvent) throws IOException {
    App.setRoot("upload");
  }

  /**
   * Method for uploading image to database with tags
   * Image itself is not stored but URL is
   *
   * @param actionEvent
   * @throws IOException
   */

  public void uploadSingle(ActionEvent actionEvent) throws IOException {
    List<File> list = App.ex.getUploadedFiles();
    File fil = list.get(0);
    ArrayList<Tag> tags = new ArrayList<>();
    imageService.createImage(ApplicationState.getCurrentUser(), fil, tags);

    App.setRoot("main");
  }

  /***
   * Method for splitting the tag textField into tags in a list
   * @param photo_tag
   * @return list of string
   */
  public List<String> tagStringSplit(TextField photo_tag) {
    String tagTekst = photo_tag.getText();
    return Arrays.asList(tagTekst.split("(?=#)"));


  }
}
