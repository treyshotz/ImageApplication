package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.service.AlbumService;
import NTNU.IDATT1002.service.ApplicationState;
import NTNU.IDATT1002.service.ImageService;
import NTNU.IDATT1002.service.TagService;
import NTNU.IDATT1002.utils.ImageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controls the buttons and changeable elements on create_album.fxml,
 * a page where you create albums
 *
 * @version 1.0 22.03.2020
 */
public class CreateAlbum extends NavBarController implements Initializable {

  public TextField album_title_field;
  public TextField album_tag_field;
  public TextArea album_desc_field;
  public Pane metadata_pane;
  public Button create_album_button;
  public VBox fileContainer;

  private AlbumService albumService;
  private ImageService imageService;

  public CreateAlbum() {
    EntityManager entityManager = App.ex.getEntityManager();
    albumService = new AlbumService(entityManager);
    imageService = new ImageService(entityManager);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    List<Image> allImages = imageService.getImageFromUser(ApplicationState.getCurrentUser());
    for (Image image : allImages) {
      javafx.scene.image.Image convertedImage = ImageUtil.convertToFXImage(image);
      HBox container = new HBox();
      container.setPrefWidth(450);
      container.setAlignment(Pos.TOP_CENTER);
      ImageView imageView = new ImageView();
      imageView.setFitHeight(200);
      imageView.setFitWidth(350);
      imageView.setPickOnBounds(true);
      imageView.setPreserveRatio(true);
      imageView.setImage(convertedImage);
      CheckBox checkBox = new CheckBox();
      checkBox.setId(image.getId().toString());
      container.getChildren().addAll(imageView, checkBox);
      fileContainer.getChildren().add(container);
    }
  }

  /**
   * Method that creates album based on the user input and checked images
   *
   * @param actionEvent
   */
  public void createAlbum(ActionEvent actionEvent) {
    String title = album_title_field.getText();
    String description = album_desc_field.getText();
    String tags = album_tag_field.getText();


    if (tags.isEmpty()){ tags = " "; }

    List<Tag> tagsToAdd = TagService.getTagsFromString(tags);
    User user = ApplicationState.getCurrentUser();

      //temporary solution for the toString problem with album log creation, along with the if(tag) above
      if (description.isEmpty()) {
        description = "No desripton added";
      }

    List<Node> imageContainers = new ArrayList<>(fileContainer.getChildren());
    List<String> checkedImagesId = new ArrayList<>();
    //Each image and checkbox has an hbox container
    imageContainers.forEach(hbox ->
        ((HBox) hbox).getChildren().stream()
            //Filter children that is a checked checkbox
            .filter(child -> child instanceof CheckBox && ((CheckBox) child).isSelected())
            //Adds all checked image id
            .forEach(checked -> checkedImagesId.add(checked.getId()))
    );


    if (validateInpid()) {
      //Find the users images and makes a filter on the checked images
      List<Image> albumImages = imageService.getImageFromUser(user).stream().filter(image -> checkedImagesId.contains(image.getId().toString())).collect(Collectors.toList());

      if (albumImages.size() > 0) {
        Optional<Album> createdAlbum = albumService.createAlbum(title, description, user, tagsToAdd, albumImages);
        createdAlbum.ifPresent(album -> {
          App.ex.setChosenAlbumId(album.getId());
          try {
            App.setRoot("view_album");
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
      } else {
        Optional<Album> createdAlbum = albumService.createEmptyAlbum(title, description, user, tagsToAdd);
        createdAlbum.ifPresent(album -> {
          App.ex.setChosenAlbumId(album.getId());
          try {
            App.setRoot("view_album");
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
      }
    }
  }

  /**
   * Makes sure that user gave the album a title
   * @return
   */
  private boolean validateInpid() {
    boolean check = true;
    if (album_title_field.getText().isEmpty()) {
      album_title_field.clear();
      album_title_field.setStyle("-fx-prompt-text-fill: red");
      album_title_field.setPromptText("* Please enter a title for the album");
      check = false;
    }
    return check;
  }
}
