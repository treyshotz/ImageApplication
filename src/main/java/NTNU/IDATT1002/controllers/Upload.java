package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controls the buttons and changeable elements on upload.fxml,
 * a page where you select images to upload
 *
 * @version 1.0 22.03.2020
 */
public class Upload extends NavBarController {

    public Button uploadBtn;
    public Pane drag_drop;

    public Upload(){
        App.ex.newPage("upload");
    }

    /**
     * Changes page to upload images page
     * @throws IOException
     */
    private void switchToUploadImages() throws IOException {
        App.setRoot("upload_images");
    }

    /**
     * Method that opens file browser with an image filter.
     * The user will choose what files to upload. If the size of one or more files
     * exceeds 4.1 MB, an error is displayed.
     * @throws IOException
     */
    public void chooseFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose files to upload");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png","*.jpg", "*.jpeg"));
        // Show save file dialog
        List<File> list = fileChooser.showOpenMultipleDialog(uploadBtn.getScene().getWindow());

        for(File file : list) {
            if(file.length() > 4100000) {
                Alert alert = new Alert(Alert.AlertType.ERROR, file.getName() + " is too large. File limit is 4.1MB");
                alert.show();
                list = new ArrayList<>();
            }
        }

        if(!list.isEmpty()){
            //Store files in DataExchange
            App.ex.setUploadedFiles(list);
            switchToUploadImages();
        }
    }

    /**
     * Method that finds the extension of a file
     * @param fileName the name of the file (img.jpg, img2.png ect.)
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
     * Method that decides if something can be dropped
     * The method is called whenever something is hoovered over the drag-drop pane
     * @param event something is dragged over the container
     */
    public void acceptDrop(DragEvent event) {
        List<String> validExtensions = Arrays.asList("jpg", "png", "jpeg");
        //Checks if the event contains files
        if(event.getDragboard().hasFiles()){
            //If not all files hoovered is in validExtension
            if (!validExtensions.containsAll(
                    event.getDragboard().getFiles().stream()
                            .map(file -> getExtension(file.getName()))
                            .collect(Collectors.toList()))) {

                event.consume();
                return;
            }
            //Makes it possible to transfer/drop the files
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    /**
     * Method that stores the dropped files
     * @param event something is dropped into the container
     * @throws IOException
     */
    public void droppedFiles(DragEvent event) throws IOException {
        List<File> list = event.getDragboard().getFiles();
        if(!list.isEmpty()){
            //Stores files to DataExchange
            App.ex.setUploadedFiles(list);
            switchToUploadImages();
        }
    }
}
