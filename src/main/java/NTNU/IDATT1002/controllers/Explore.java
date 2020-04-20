package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.controllers.components.explore.ImageColumn;
import NTNU.IDATT1002.controllers.components.explore.ImageRow;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.repository.Page;
import NTNU.IDATT1002.repository.PageRequest;
import NTNU.IDATT1002.service.ImageService;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Controls the buttons and changeable elements on explore.fxml,
 * a page where you explore images.
 *
 * @version 1.0 22.03.2020
 */
public class Explore extends NavBarController implements Initializable {

    private final int MAX_PER_ROW = 3;
    private final Integer[] AMOUNT_SELECTION = {15, 30, 50, 100};
    private Integer pageSize = 15;

    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox pageContainer;
    @FXML
    public VBox resultsPerPage;
    @FXML
    public ProgressIndicator pageProgressIndicator;

    private Page<Image> currentPage;
    private PageRequest currentPageRequest;
    private Pagination pagination;
    private ImageService imageService;

    public Explore(){
        App.ex.newPage("explore");
    }

    /**
     * Generate content, including the first page of all images.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageService = new ImageService(App.ex.getEntityManager());
        createAndShowResultsSelection();
        updatePage();
    }

    /**
     * Create and show a {@link ComboBox} selection holding the options
     * for choosing the amount of results per page.
     */
    private void createAndShowResultsSelection() {
        Label description_label = new Label("Show: ");
        description_label.setFont(Font.font(App.ex.getDefaultFont(), 20.0));
        description_label.setTextFill(Color.web("#000000"));

        ComboBox<Integer> amounts =new ComboBox<>(FXCollections
                                     .observableArrayList(AMOUNT_SELECTION));
        amounts.setValue(pageSize);

        amounts.setOnAction(e -> amountSelectionChanged(amounts));
        HBox perPageSelection = new HBox();
        perPageSelection.setAlignment(Pos.TOP_RIGHT);

        perPageSelection.getChildren().addAll(description_label, amounts);
        resultsPerPage.getChildren().add(perPageSelection);
    }

    /**
     * Action to take when user selected a new option in given {@link ComboBox}.
     *
     * @param choices the {@link ComboBox} holding the list of choice integers
     */
    private void amountSelectionChanged(ComboBox<Integer> choices) {
        pageSize = choices.getValue();
        pageContainer.getChildren().remove(pagination);
        updatePage();
    }

    /**
     * Update view with the first page of results with index 0, in the background.
     * Includes the results in a {@link Pagination}.
     */
    private void updatePage() {
        Task<Page<Image>> firstPageTask = getPageTaskFor(0);
        new Thread(firstPageTask).start();

        firstPageTask.setOnSucceeded(event -> {
            currentPage = firstPageTask.getValue();
            pageContainer.getChildren().add(getPagination());
        });
    }

    /**
     * Return a {@link Task} for fetching and updating the current page
     * with given page number and specified page size.
     *
     * @param pageNumber the page number
     */
    private Task<Page<Image>> getPageTaskFor(int pageNumber) {
        return new Task<>() {
            @Override
            protected Page<Image> call() {
                currentPageRequest = PageRequest.of(pageNumber, pageSize);
                currentPage = imageService.findAll(currentPageRequest);
                return currentPage;
            }
        };
    }

    /**
     * Return the {@link Pagination} for this view.
     *
     * Sets page count, current page index, max page indicator count
     * and a page factory defining action to take when user clicks on a new page.
     */
    private Pagination getPagination() {
        pagination = new Pagination();
        pagination.setCache(true);
        pagination.setCacheHint(CacheHint.SPEED);

        pagination.setPageCount(currentPage.getLastPageNumber() + 1);
        pagination.setCurrentPageIndex(currentPageRequest.getPageNumber());
        pagination.setMaxPageIndicatorCount(Math.min(currentPage.getLastPageNumber() + 1, 10));
        pagination.setPageFactory(this::getPageFactory);

        return pagination;
    }

    /**
     * Define actions to take when user clicks on a new page.
     *
     * Fetches a {@link Page} with given page index in the background
     * and updates the view with formatted {@link ImageRow}s.
     *
     * @param pageIndex the new page number
     * @return a page factory
     */
    private Node getPageFactory(Integer pageIndex) {
        pageProgressIndicator.setVisible(true);
        Task<Page<Image>> newPageTask = getPageTaskFor(pageIndex);
        new Thread(newPageTask).start();

        scrollToTop();

        VBox imageRowsContainer = new VBox();
        newPageTask.setOnSucceeded(event -> {
            pageProgressIndicator.setVisible(false);
            currentPage = newPageTask.getValue();

            List<ImageRow> imageRows = getContentsFrom(currentPage);
            imageRowsContainer.getChildren().addAll(imageRows);
        });

        return imageRowsContainer;
    }

    /**
     * Scroll view point to top animation.
     */
    private void scrollToTop() {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(scrollPane.vvalueProperty(), 0.0);
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    /**
     * Aggregate given {@link Page}'s content to {@link ImageRow}s
     * and {@link ImageColumn}s respectively.
     *
     * @param page the {@link Page} who's content to parse
     * @return the list of content parsed into a list of {@link ImageRow}s
     */
    public List<ImageRow> getContentsFrom(Page<Image> page){
        List<ImageRow> rows = new ArrayList<>();
        ImageRow currentRow = new ImageRow(MAX_PER_ROW);

        for(Image image : page)
            currentRow = parseColumn(rows, currentRow, image);

        return rows;
    }

    /**
     * Parse given {@link Image} into a {@link ImageColumn}
     * and add to given {@link ImageRow}.
     *
     * Starts on a new {@link ImageRow} if the current is full.
     *
     * @param rows the list of aggregated {@link ImageRow}s
     * @param currentRow the current row to add the new {@link ImageColumn} to
     * @param image the current {@link Image} to parse
     * @return the current {@link ImageRow}, the given one if it is not full.
     */
    private ImageRow parseColumn(List<ImageRow> rows, ImageRow currentRow, Image image) {
        ImageColumn imageColumn = new ImageColumn(image);
        addSwitchToViewImageEventHandlers(imageColumn);

        currentRow.addColumn(imageColumn);

        if (currentRow.isFull()) {
            rows.add(currentRow);
            currentRow = new ImageRow(MAX_PER_ROW);
        }

        return currentRow;
    }

    /**
     * Add switch to view image {@link javafx.event.EventHandler}s to given {@link ImageColumn}.
     */
    private void addSwitchToViewImageEventHandlers(ImageColumn imageColumn) {
        imageColumn.setOnMouseClicked(e -> {
            try{
                switchToViewImage(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        imageColumn.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)){
                try {
                    switchToViewImage(keyEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Change scene to View Picture page for the image that was interacted with.
     */
    public void switchToViewImage(InputEvent event) throws IOException {
        Node node = (Node) event.getSource();
        String nodeId = node.getId();

        if (nodeId == null)
            return;

        long imageId = Long.parseLong(nodeId);

        if (imageId == 0)
            return;

        App.ex.setChosenImg(imageId);
        App.setRoot("view_image");
    }
}
