package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Controls the buttons and changeable elements on view_album.fxml,
 * a page where get a more detailed view of an album
 * @version 1.0 22.03.2020
 */
public class ViewAlbum {
    public TextField tbar_search;
    public ImageView tbar_logo;
    public Button tbar_explore;
    public Button tbar_map;
    public Button tbar_upload;
    public Button create_album_pdf;
    public Pane metadata_pane;
    public Text desc_textField;
    public Text album_tagsField;
    public Text album_titleField;
    public Button scroll_button_next;
    public Button scroll_button_previous;
    public ImageView scroll_picture6;
    public ImageView scroll_picture5;
    public ImageView scroll_picture4;
    public ImageView scroll_picture3;
    public ImageView scroll_picture2;
    public ImageView scroll_picture1;
    public Text picture_title_field;
    public Text picture_tagsField;
    public ImageView main_picture;
    public Button tbar_searchBtn;
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

    public void openPopUpPicture(MouseEvent mouseEvent) {
        //write method that opens a pop-up view of the main picture
    }

    public void changeMainPicture1(MouseEvent mouseEvent) {
        //write method that switches to main picture to be picture 1 in the scrollbar-view
    }

    public void changeMainPicture2(MouseEvent mouseEvent) {
        //write method that switches to main picture to be picture 2 in the scrollbar-view
    }

    public void changeMainPicture3(MouseEvent mouseEvent) {
        //write method that switches to main picture to be picture 3 in the scrollbar-view
    }

    public void changeMainPicture4(MouseEvent mouseEvent) {
        //write method that switches to main picture to be picture 4 in the scrollbar-view
    }

    public void changeMainPicture5(MouseEvent mouseEvent) {
        //write method that switches to main picture to be picture 5 in the scrollbar-view
    }

    public void changeMainPicture6(MouseEvent mouseEvent) {
        //write method that switches to main picture to be picture 6 in the scrollbar-view
    }

    public void loadPreviousScrollbarView(ActionEvent actionEvent) {
        //write method that loads the previous 6 images in the album into the scrollbar-view
    }

    public void loadNextScrollbarView(ActionEvent actionEvent) {
        //write method that loads the next 6 images in the album into the scrollbar-view
    }

    public void createPdf(ActionEvent actionEvent) {
        //write method that generates and downloads a PDF version of the album
    }
}
