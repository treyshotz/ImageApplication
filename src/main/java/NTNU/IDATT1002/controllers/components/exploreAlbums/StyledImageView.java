package NTNU.IDATT1002.controllers.components.exploreAlbums;

import NTNU.IDATT1002.utils.ImageUtil;
import javafx.scene.image.Image;
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
    }

    /**
     * Create the ImageView to hold given image
     *
     * @param image the image to display
     */
    public StyledImageView(NTNU.IDATT1002.models.Image image) {
        this.applyStyling();
        Image imageToSet = ImageUtil.convertToFXImage(image);
        this.setImage(imageToSet);
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
