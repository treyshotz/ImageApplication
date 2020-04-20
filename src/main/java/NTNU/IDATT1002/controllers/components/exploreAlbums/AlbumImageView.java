package NTNU.IDATT1002.controllers.components.exploreAlbums;

import NTNU.IDATT1002.utils.ImageUtil;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * ImageView for holding an albums preview image.
 */
public class AlbumImageView extends ImageView {

    /**
     * Create an empty {@link ImageView} with default styling.
     */
    public AlbumImageView() {
        this.applyStyling();
    }

    /**
     * Create the ImageView to hold given image
     *
     * @param image the image to display
     */
    public AlbumImageView(NTNU.IDATT1002.models.Image image) {
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
