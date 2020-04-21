package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.controllers.components.explore.ImageRow;
import NTNU.IDATT1002.repository.Page;
import NTNU.IDATT1002.repository.PageRequest;
import NTNU.IDATT1002.repository.Sort;
import NTNU.IDATT1002.service.PageableService;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controller class for serving paginated content.
 *
 * Comes with predefined selections of {@link Sort} and the amount of results per page.
 * To properly integrate this class, these FXML components are required,
 * but can be placed and styled where appropriate.
 *
 * <pre>{@code
 * // Display for the total amount of results
 * <Text fx:id="total" >
 *    <font>
 *       <Font name="System Bold Italic" size="36.0" />
 *    </font>
 * </Text>
 *
 * // Sort selection
 * <ChoiceBox fx:id="sortChoiceBox" prefHeight="52.0" prefWidth="228.0" value="Newest first">
 *    <items>
 *       <FXCollections fx:factory="observableArrayList">
 *          <String fx:value="Newest first" />
 *          <String fx:value="Oldest first" />
 *          <String fx:value="Alphabetically (A-Z)" />
 *          <String fx:value="Alphabetically (Z-A)" />
 *       </FXCollections>
 *    </items>
 * </ChoiceBox>
 *
 * // Container for the page's content and progress indicator
 * <VBox fx:id="pageContainer">
 *     <VBox fx:id="showResultsPerPageContainer" >
 *         <VBox>
 *             <ProgressIndicator fx:id="pageProgressIndicator"/>
 *         </VBox>
 *     </VBox>
 * </VBox>
 * }</pre>
 *
 * @param <T> the type of entity content served
 */
public abstract class PaginatedContent<T> extends NavBarController implements Initializable {

    private final Integer[] AMOUNT_SELECTION = {15, 30, 50, 100};

    private PageableService<T> service;

    private Pagination pagination;

    private Integer pageSize = 15;

    private Page<T> currentPage;

    private Sort currentSort = Sort.empty();

    private PageRequest currentPageRequest = PageRequest.of(0, pageSize, currentSort);

    private Map<String, Sort> choiceSortMapping;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox pageContainer;
    @FXML
    private ChoiceBox<String> sortChoiceBox;
    @FXML
    private VBox showResultsPerPageContainer;
    @FXML
    private ProgressIndicator pageProgressIndicator;
    @FXML
    private Text total;

    protected abstract PageableService<T> getService();
    protected abstract List<? extends Node> getContentsFrom(Page<T> page);

    /**
     * Initialize sort choice mapping to appropriate sort defined by given fields.
     *
     * @param dateField the date sort field defined by {@link T}
     * @param titleField the title sort field defined by {@link T}
     */
    public PaginatedContent(String dateField, String titleField) {
        this.currentSort =  Sort.by(dateField).descending();
        this.choiceSortMapping = Map.of(
            "Newest first", Sort.by(dateField).descending(),
            "Oldest first", Sort.by(dateField).ascending(),
            "Alphabetically (A-Z)", Sort.by(titleField).ascending(),
            "Alphabetically (Z-A)", Sort.by(titleField).descending()
        );
    }

    /**
     * Generate content and bindings, including the first page of all images.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service = getService();
        bindSortChoiceBox();
        createAndShowResultsSelection();
        updatePage();
    }

    /**
     * Bind {@link ChoiceBox} for sort selections to the page.
     */
    private void bindSortChoiceBox() {
        sortChoiceBox.setOnAction(e -> sortSelectionChanged(sortChoiceBox));
    }

    /**
     * Action to take when user selected a new sorting option in given {@link ChoiceBox}.
     *
     * @param sortChoiceBox the {@link ChoiceBox} holding the list of choice strings
     */
    private void sortSelectionChanged(ChoiceBox<String> sortChoiceBox) {
        currentSort = choiceSortMapping.getOrDefault(sortChoiceBox.getValue(), Sort.empty());
        pageContainer.getChildren().remove(pagination);
        updatePage();
    }

    /**
     * Create and show a {@link ComboBox} selection holding the options
     * for choosing the amount of results per page.
     */
    protected void createAndShowResultsSelection() {
        Label descriptionLabel = new Label("Show: ");
        descriptionLabel.setFont(Font.font(App.ex.getDefaultFont(), 20.0));
        descriptionLabel.setTextFill(Color.web("#000000"));

        ComboBox<Integer> amounts =new ComboBox<>(FXCollections
                                                          .observableArrayList(AMOUNT_SELECTION));
        amounts.setValue(pageSize);

        amounts.setOnAction(e -> pageSizeSelectionChanged(amounts));
        HBox perPageSelection = new HBox();
        perPageSelection.setAlignment(Pos.TOP_RIGHT);

        perPageSelection.getChildren().addAll(descriptionLabel, amounts);
        showResultsPerPageContainer.getChildren().add(perPageSelection);
    }

    /**
     * Action to take when user selected a new option in given {@link ComboBox}.
     *
     * @param choices the {@link ComboBox} holding the list of choice integers
     */
    protected void pageSizeSelectionChanged(ComboBox<Integer> choices) {
        pageSize = choices.getValue();
        pageContainer.getChildren().remove(pagination);
        updatePage();
    }

    /**
     * Update view with the first page of results with index 0, in the background.
     * Includes the results in a {@link Pagination}.
     */
    public void updatePage() {
        Task<Page<T>> firstPageTask = getPageTaskFor(0);
        new Thread(firstPageTask).start();

        firstPageTask.setOnSucceeded(event -> {
            currentPage = firstPageTask.getValue();
            pageContainer.getChildren().add(getPagination());
            total.setText(String.valueOf(currentPage.getTotal()));
        });
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
    protected Node getPageFactory(Integer pageIndex) {
        pageProgressIndicator.setVisible(true);
        Task<Page<T>> newPageTask = getPageTaskFor(pageIndex);
        new Thread(newPageTask).start();

        scrollToTop();

        VBox imageRowsContainer = new VBox();
        newPageTask.setOnSucceeded(pageChanged(newPageTask, imageRowsContainer));

        return imageRowsContainer;
    }

    /**
     * Return a {@link Task} for fetching and updating the current page
     * with given page number and specified page size.
     *
     * @param pageNumber the page number
     */
    protected Task<Page<T>> getPageTaskFor(int pageNumber) {
        return new Task<>() {
            @Override
            protected Page<T> call() {
                currentPageRequest = PageRequest.of(pageNumber, pageSize, currentSort);
                currentPage = service.findAll(currentPageRequest);
                return currentPage;
            }
        };
    }

    /**
     * Define actions to take when the page changes.
     *
     * @param newPageTask the {@link Task} holding the page
     * @param pageContentContainer the container for page content
     * @return the actions to take when the page changes
     */
    protected EventHandler<WorkerStateEvent> pageChanged(Task<Page<T>> newPageTask, VBox pageContentContainer) {
        return event -> {
            pageProgressIndicator.setVisible(false);
            currentPage = newPageTask.getValue();

            List<? extends Node> pageContent = getContentsFrom(currentPage);
            pageContentContainer.getChildren().addAll(pageContent);
        };
    }

    /**
     * Scroll view point to top animation.
     */
    protected void scrollToTop() {
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(scrollPane.vvalueProperty(), 0.0);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

}
