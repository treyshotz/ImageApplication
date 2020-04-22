package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.controllers.components.explore.ImageColumn;
import NTNU.IDATT1002.controllers.components.explore.ImageRow;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.repository.Page;
import NTNU.IDATT1002.service.ImageService;
import NTNU.IDATT1002.service.PageableService;
import javafx.scene.Node;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Controls the buttons and changeable elements on explore.fxml,
 * a page where you explore images.
 *
 * @version 1.0 22.03.2020
 */
public class Explore extends PaginatedContent<Image> {

    private final int MAX_PER_ROW = 3;

    /**
     * Tell {@link PaginatedContent} which fields to sort {@link Image}s by.
     * Tell {@link DataExchange} that explore page is visited
     */
    public Explore() {
        super("uploadedAt", "title");
        App.ex.newPage("explore");
    }

    /**
     * Return an {@link ImageService} to use in {@link PaginatedContent}.
     */
    @Override
    protected PageableService<Image> getService() {
        return new ImageService(App.ex.getEntityManager());
    }

    /**
     * Aggregate given {@link Page}'s content to {@link ImageRow}s
     * and {@link ImageColumn}s respectively.
     *
     * @param page the {@link Page} who's content to parse
     * @return the list of content parsed into a list of {@link ImageRow}s
     */
    @Override
    public List<ImageRow> getContentsFrom(Page<Image> page) {
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
