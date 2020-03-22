package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ViewAlbumController {
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

    public void switchToSearch(ActionEvent actionEvent) throws IOException {
        App.setRoot("search_page");
    }

    public void switchToUpload(ActionEvent actionEvent) throws IOException {
        App.setRoot("upload_page");
    }

    public void switchToMap(ActionEvent actionEvent) throws IOException {
        App.setRoot("map_page");
    }

    public void switchToExplore(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore_page");
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

    public void switchToMain(MouseEvent mouseEvent) throws IOException {
        App.setRoot("main");
    }

    public void switchToAlbums(ActionEvent actionEvent) throws IOException {
        App.setRoot("explore_albums");
    }
}
