/**
 * Controls the buttons and changable elements on upload page
 * @version 1.0 17.03.2020
 * @author Simon Jensen
 */

package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UploadController {
    public Button uploadBtn;
    public VBox thumbnailsField;
    public Pane dragDropField;
    public TextField searchField;

    /**
     * Method that changes stage to Explore page
     * @throws IOException
     */
    public void switchToExplore() throws IOException {
        App.setRoot("explore");
    }

    /**
     * Method that changes stage to Album page
     * @throws IOException
     */
    public void switchToAlbum() throws IOException {
        App.setRoot("album");
    }

    /**
     * Method that changes stage to Map page
     * @throws IOException
     */
    public void switchToMap() throws IOException {
        App.setRoot("map");
    }

    /**
     * Method for searching by tags, ect.
     * @param keyEvent
     */
    public void search(KeyEvent keyEvent) {
        //TODO: Make search method
    }

    /**
     * Method that opens filebrowser with an image filter.
     * The user chooses what files to upload
     * @throws MalformedURLException
     */
    public void chooseFile() throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image files");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png","*.jpg", "*.jpeg"));
        // Show save file dialog
        List<File> list = fileChooser.showOpenMultipleDialog(uploadBtn.getScene().getWindow());

        if (list != null){
            for (File file : list){
                //Made a method to confirm that the images was uploaded correctly
                addThumbnail(file);
            }
        }
    }

    /**
     * Method that finds the extension/filetype
     * @param fileName the name of the file (img.jpg ect.)
     * @return file extension (jpg, png ect.)
     */
    public String getExtension(String fileName){
        String extension = "";

        int i = fileName.lastIndexOf('.');
        //if the name is not empty
        if (i > 0 && i < fileName.length() - 1){
            return fileName.substring(i + 1).toLowerCase();
        }
        return extension;
    }

    /**
     * Method that decides whenever the file hoovered over the pane is valid or not.
     * Called when something is hoovered over the pane.
     * @param event something is dragged over the container
     */
    public void acceptDrop(DragEvent event) {
        // Extensions that are valid to be drag-n-dropped
        //TODO: Choose valid file types
        List<String> validExtensions = Arrays.asList("jpg", "png", "jpeg");
        //Checks if the event contains files
        if(event.getDragboard().hasFiles()){
            if (!validExtensions.containsAll(
                    //Makes a list out of the events file extensions
                    event.getDragboard().getFiles().stream()
                            .map(file -> getExtension(file.getName()))
                            .collect(Collectors.toList()))) {

                event.consume();
                return;
            }
            //Makes it possible to transfer the files
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    /**
     * Method that stores the dropped files. It is not possible to transfer files
     * without them already being accepted in acceptDrop() method.
     * @param event something is dropped into the container
     * @throws MalformedURLException
     */
    public void droppedFiles(DragEvent event) throws MalformedURLException {
        List<File> list = event.getDragboard().getFiles();
        for (File file : list){
            addThumbnail(file);
        }
    }

    /**
     * Test-method to confirm that the image drop went well
     * @param file an image file
     * @throws MalformedURLException
     */
    //TODO: Remove method?
    public void addThumbnail(File file) throws MalformedURLException {
        Image image = new Image(file.toURI().toURL().toString());
        ImageView view = new ImageView();
        view.setImage(image);

        //setting the fit height and width of the image view
        view.setFitHeight(30);
        view.setFitWidth(30);

        thumbnailsField.getChildren().add(view);
    }
}
