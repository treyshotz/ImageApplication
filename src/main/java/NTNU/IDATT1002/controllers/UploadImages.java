package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.service.AlbumService;
import NTNU.IDATT1002.service.ApplicationState;
import NTNU.IDATT1002.service.ImageService;
import NTNU.IDATT1002.service.TagService;
import NTNU.IDATT1002.utils.MetaDataExtractor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.controlsfx.control.CheckComboBox;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controls the buttons and changeable elements on upload_single.fxml,
 * a page where you add descriptions to your selected image
 *
 * @version 1.0 22.03.2020
 */
public class UploadImages extends NavBarController implements Initializable {

  public VBox uploadContainer;
  public VBox root;
  private AlbumService albumService;
  private ImageService imageService;

  public UploadImages(){
    EntityManager entityManager = App.ex.getEntityManager();
    albumService = new AlbumService(entityManager);
    imageService = new ImageService(entityManager);
  }
  /**
   * Method that runs when the controller is loaded
   * Sets the image url on the page to be the uploaded images url
   *
   * @param location
   * @param resources
   */
  public void initialize(URL location, ResourceBundle resources) {
    uploadContainer.getChildren().clear();
    List<File> files = App.ex.getUploadedFiles();
    int maxPerPage = Math.min(files.size(), 25);
    for (int i = 0; i < maxPerPage; i ++){
      //A container for image and text
      HBox imageContainer = new HBox();
      imageContainer.setPrefWidth(1520);
      imageContainer.setPrefHeight(300);

      insertImageToContainer(files.get(i), imageContainer);
      insertImageTextToContainer(files.get(i), imageContainer);
      uploadContainer.getChildren().add(imageContainer);
    }
    if (uploadContainer.getChildren().size() > 0){
      Button accept = new Button("Accept");
      accept.setOnAction(actionEvent -> {
        try {
          upload();
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
      uploadContainer.setAlignment(Pos.TOP_CENTER);
      uploadContainer.getChildren().add(accept);
    }
  }


  /**
   * Format and insert the first image in the given album to the given container.
   *
   * @param file the chosen file
   * @param imageContainer a container for the image
   */
  private void insertImageToContainer(File file, HBox imageContainer){
    ImageView imageView = new ImageView();
    imageView.setFitHeight(300.0);
    imageView.setFitWidth(533.0);
    imageView.setPickOnBounds(true);
    imageView.setPreserveRatio(true);
    imageView.setImage(new Image(file.toURI().toString()));

    HBox container = new HBox();
    container.setPrefWidth(533);
    container.setPrefHeight(300);
    container.setAlignment(Pos.TOP_CENTER);
    container.getChildren().add(imageView);

    imageContainer.getChildren().add(container);
  }

  /**
   * Att text elements from album to the container
   *
   * @param file the album to display
   * @param imageContainer the container for each separate album
   */
  private void insertImageTextToContainer(File file, HBox imageContainer) {
    //Creates a vbox so that nodes is aligned in a column
    VBox textContainer = new VBox();
    textContainer.setSpacing(5);
    textContainer.setPadding(new Insets(0, 0, 0, 25));
    textContainer.setPrefHeight(300);
    textContainer.setPrefWidth(987);

    insertImageTitle(file, textContainer);
    insertImageTags(file, textContainer);
    insertImageMetadata(file, textContainer);
    insertCheckedChoiceBox(textContainer);

    imageContainer.getChildren().add(textContainer);
  }

  /**
   * Insert title of the given album to the given container
   * It is clickable, and switches to View Album page of that album
   *
   * @param file
   * @param textContainer container for text elements of an album
   */
  private void insertImageTitle(File file, VBox textContainer) {
    HBox titleContainer = new HBox();

    Text titleLabel = new Text("Title: ");
    titleLabel.setFont(Font.font(App.ex.getDefaultFont(), FontWeight.BOLD, 24));

    TextField title = new TextField();
    title.setId("title");
    title.setFont(Font.font(App.ex.getDefaultFont(),24));

    titleContainer.getChildren().addAll(titleLabel, title);

    textContainer.getChildren().add(titleContainer);
  }

  /**
   * Insert tags of the given album to the given container
   *
   * @param file
   * @param textContainer container for text elements of an image
   */
  private void insertImageTags(File file, VBox textContainer) {
    HBox tagContainer = new HBox();

    Text tagLabel = new Text("Tags: ");
    tagLabel.setFont(Font.font(App.ex.getDefaultFont(), FontWeight.BOLD, 24));

    TextField tags = new TextField();
    tags.setId("tags");
    tags.setFont(Font.font(App.ex.getDefaultFont(),24));

    tagContainer.getChildren().addAll(tagLabel, tags);

    textContainer.getChildren().add(tagContainer);
  }


  /**
   * Insert description of the given album to the given container
   *
   * @param file
   * @param textContainer container for text elements of an image
   */
  private void insertImageMetadata(File file, VBox textContainer) {
    Text metadataLabel = new Text("Metadata: ");
    metadataLabel.setFont(Font.font(App.ex.getDefaultFont(), FontWeight.BOLD, 16));

    String metadataSting = MetaDataExtractor.getMetadata(file);
    TextArea metadata = new TextArea(metadataSting);

    metadata.setEditable(false);
    metadata.setPrefWidth(500);
    metadata.setFont(Font.font(App.ex.getDefaultFont(),16));


    textContainer.getChildren().addAll(metadataLabel, metadata);
  }

  /**
   * Method that generates a dropdown with checkable albums
   * The image is added to the following albums after it is created
   * @param textContainer container for text elements of an image
   */
  private void insertCheckedChoiceBox(VBox textContainer){
    ObservableList<String> options = FXCollections.observableArrayList();
    albumService.getAllAlbums().stream()
            //Filters the current users albums
            .filter(album -> album.getUser() == ApplicationState.getCurrentUser())
            //Adds a checkbox with albums title and id
            .forEach(album -> options.add(album.getTitle() + " #" + album.getId()));
    CheckComboBox<String> checkComboBox = new CheckComboBox<>(options);
    checkComboBox.setId("checkbox");
    checkComboBox.setTitle("Add to albums");
    textContainer.getChildren().add(checkComboBox);
  }

  /**
   * Method for uploading image to database with tags
   * Image itself is not stored but URL is
   *
   * @throws IOException
   */
  public void upload() throws IOException {
    //List of containers for each selected image
    List<Node> imageContainers = uploadContainer.getChildren().stream()
            .filter(child -> child instanceof HBox)
            .collect(Collectors.toList());
    for(int i = 0; i < imageContainers.size(); i++){
      Node imageContainer = imageContainers.get(i);
      //Getting all child nodes and sorts out those with correct id
      List<Node> childNodes = getAllNodes((Parent) imageContainer).stream()
              .filter(n -> n.getId() != null && (n.getId().equals("title") || n.getId().equals("tags") || n.getId().equals("checkbox")))
              .collect(Collectors.toList());

      //Todo: make title apply to images
      Node titleField = childNodes.get(0);
      Node tagsField = childNodes.get(1);
      Node comboBox = childNodes.get(2);

      //List of the albums checked on the dropdown list
      ObservableList<String> chosenAlbums = ((CheckComboBox<String>) comboBox).getCheckModel().getCheckedItems();
      ArrayList<String> albumsId = new ArrayList<>();
      //Splits the id so we can find albums by id afterwards
      chosenAlbums.forEach(album -> albumsId.add(album.substring(album.lastIndexOf('#')+1)));

      //Each of the uploaded images in DataExchange index match on each container displaying it on the page
      File file = App.ex.getUploadedFiles().get(i);
      if(file.length() > 4100000) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, file.getName() + "is too large. File limit is 4.1MB");
        alert.show();
        break;
      }
      String tagsString = ((TextField) tagsField).getText();
      List<Tag> tags = TagService.getTagsFromString(tagsString);

      //Try creating image with the tags entered
      Optional<NTNU.IDATT1002.models.Image> createdImage = imageService.createImage(ApplicationState.getCurrentUser(), file, tags);
      createdImage.ifPresent(image -> {
        //For each chosen album checked the image is added
        for (String id : albumsId) {
          Optional<Album> findAlbum = albumService.getAlbumById(Long.parseLong(id));
          findAlbum.ifPresent(album -> albumService.addImage(album, image));

        }
        //Removes container if it was uploaded from data exchange
        uploadContainer.getChildren().remove(imageContainer);
      });
    }
    //If only element in container is button we change to main page
    if (uploadContainer.getChildren().size() == 1) {
      App.setRoot("main");
    }
    else {
      Alert alert = new Alert(Alert.AlertType.INFORMATION, "Could not upload some of your images. Please try again.");
      alert.show();
    }
  }

  public static ArrayList<Node> getAllNodes(Parent root) {
    ArrayList<Node> nodes = new ArrayList<Node>();
    addAllDescendents(root, nodes);
    return nodes;
  }

  private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
    for (Node node : parent.getChildrenUnmodifiable()) {
      nodes.add(node);
      if (node instanceof Parent)
        addAllDescendents((Parent)node, nodes);
    }
  }
}
