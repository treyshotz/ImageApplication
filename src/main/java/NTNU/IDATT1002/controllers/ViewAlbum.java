package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.ImageAlbum;
import NTNU.IDATT1002.service.ImageAlbumService;
import NTNU.IDATT1002.utils.PdfDocument;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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

    private ImageAlbumService imageAlbumService;

    private static Logger logger = LoggerFactory.getLogger(ViewAlbum.class);

    public ViewAlbum() {
        this.imageAlbumService =  new ImageAlbumService();
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

    /**
     * Create and save album pdf to users downloads directory.
     *
     * @param actionEvent
     */
    public void createPdf(ActionEvent actionEvent) {
        Long currentAlbumId = App.ex.getChosenAlbumId();
        ImageAlbum imageAlbum = imageAlbumService.getImageAlbumById(currentAlbumId)
                .orElseThrow(IllegalArgumentException::new);

        String destinationFile = String.format("%s/downloads/%s.pdf",
                System.getProperty("user.home"),
                imageAlbum.getTitle());

        PdfDocument document = new PdfDocument(imageAlbum, destinationFile);
        document.createPdfDocument();
        logger.info("[x] Saved PDF document to " + destinationFile);

        displayPdfLink(document.getPdfDocument());
    }

    /**
     * Replace create album pdf button with a button to open the given document.
     *
     * @param pdfDocument the pdf document to be opened
     */
    private void displayPdfLink(File pdfDocument) {
        create_album_pdf.setText("Open PDF");
        create_album_pdf.setOnAction(actionEvent -> openPdfDocument(actionEvent, pdfDocument));
    }

    /**
     * Open given file.
     *
     * @param actionEvent
     * @param file the file to open
     */
    private void openPdfDocument(ActionEvent actionEvent, File file) {
        HostServices hostServices = App.ex.getHostServices();
        hostServices.showDocument(file.getAbsolutePath());
    }
}
