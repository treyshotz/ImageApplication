package NTNU.IDATT1002.controllers.components.explore;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;


/**
 * A container representing a row holding a certain amount
 * of {@link ImageColumn}s which can be used to display
 * a number of elements at the same time.
 */
public class ImageRow extends HBox {
    private final int capacity;
    private int count;

    /**
     * Create a {@link ImageRow} with given capacity.
     *
     * @param capacity the amount of columns this row can hold
     */
    public ImageRow(int capacity) {
        this.capacity = capacity;
        this.count = 0;

        applyStyling();
    }

    /**
     * Style this {@link ImageRow}.
     */
    private void applyStyling() {
        this.setAlignment(Pos.CENTER);
        this.setMaxHeight(350.0);
        this.setMaxWidth(1590.0);
        this.setMinHeight(310.0);
        this.setMinWidth(400.0);
        this.setSpacing(10.0);
        this.getStylesheets().add("/NTNU/IDATT1002/style.css");
        this.paddingProperty().setValue(new Insets(10.0, 10.0, 10.0, 10.0));
    }

    /**
     * Add given {@link ImageColumn} and increase count.
     *
     * @param column the {@link ImageColumn} to add
     */
    public void addColumn(ImageColumn column) {
        this.getChildren().add(column);
        count++;
    }

    /**
     * Return whether this {@link ImageRow} has reached its capacity.
     */
    public boolean isFull() {
        return count >= capacity;
    }
}
