/**
 * Controls the buttons and changable elements on upload page
 * @version 1.0 17.03.2020
 * @author Simon Jensen
 */

package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
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
 * @version 1.0 22.03.2020
 */
public class Upload {
    public ImageView tbar_logo;
    public TextField tbar_search;
    public Button tbar_searchBtn;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;

    public Button uploadBtn;
    public Pane drag_drop;
    public Button tbar_albums;

    /**
     * Method that changes stage to Main page
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToMain(MouseEvent mouseEvent) throws IOException {
        App.setRoot("main");
    }

    /**
     * Method that changes stage to Search page. It reads the value of the search
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
     * Method that changes stage to Explore page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToExplore(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore");
    }

    /**
     * Method that changes stage to Albums page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToAlbums(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore_albums");
    }

    /**
     * Method that changes stage to Map page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToMap(ActionEvent actionEvent) throws IOException {
        App.setRoot("map");
    }

    /**
     * Method that changes stage to Upload page
     * @param actionEvent the mouse has done something
     * @throws IOException this page does not exist
     */
    public void switchToUpload(ActionEvent actionEvent) throws IOException {
        App.setRoot("upload");
    }

    /**
     * Method that changs stage to Uploaded Single page
     * If the user has chosen 1 image this method is called
     * @throws IOException
     */
    private void switchToUploadedSingle() throws IOException {
        App.setRoot("uploaded_single");
    }

    /**
     * Method that changs stage to Uploaded Multiple page
     * If the user has chosen multiple images this method is called
     * @throws IOException
     */
    private void switchToUploadedMultiple() throws IOException {
        App.setRoot("uploaded_multiple");
    }




    /**
     * Method that opens file browser with an image filter
     * The user will choose what files to upload
     * @throws IOException
     */
    public void chooseFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose files to upload");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png","*.jpg", "*.jpeg"));
        // Show save file dialog
        List<File> list = fileChooser.showOpenMultipleDialog(uploadBtn.getScene().getWindow());

        if(!list.isEmpty()){
            //Store files in DataExchange
            App.ex.setUploadedFiles(list);
            if (list.size() == 1){
                switchToUploadedSingle();
            }
            else {
                switchToUploadedMultiple();
            }
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
        //TODO: Choose valid file types
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
            if (list.size() == 1){
                switchToUploadedSingle();
            }
            else {
                switchToUploadedMultiple();
            }
        }
    }
}
