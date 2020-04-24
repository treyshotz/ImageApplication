package NTNU.IDATT1002.controllers.components.exploreAlbums;

import javafx.scene.image.ImageView;

/**
 * ImageView for holding an image.
 */
public class StyledImageView extends ImageView {

    /**
     * Create an empty {@link ImageView} with default styling.
     */
    public StyledImageView() {
        this.applyStyling();
        this.getStyleClass().add("exploreImages");
        this.setFocusTraversable(true);
    }

    /**
     * Apply styling. Default height and width.
     */
    private void applyStyling() {
        this.setFitHeight(300.0);
        this.setFitWidth(533.0);
        this.setPickOnBounds(true);
        this.setPreserveRatio(true);
    }
}
