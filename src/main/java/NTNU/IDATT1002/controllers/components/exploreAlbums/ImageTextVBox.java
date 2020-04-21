package NTNU.IDATT1002.controllers.components.exploreAlbums;

import NTNU.IDATT1002.models.Image;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * VBox for holding an image's text components.
 */
public class ImageTextVBox extends VBox {

    private HBox title;

    /**
     * Create VBox holding the text fields of given imag.
     */
    public ImageTextVBox(Image image) {
        this.align();

        title = new TitleHBox(image.getTitle());
        HBox tags = new TagsHBox(image.getTags());
        HBox metadata = new MetadataHBox(image.getMetadata());

        this.getChildren().addAll(title, tags, metadata);
    }

    /**
     * Align components in a column.
     */
    private void align() {
        this.setSpacing(5);
        this.setPadding(new Insets(10, 0, 0, 25));
        this.setPrefHeight(300);
        this.setPrefWidth(987);
    }

    public HBox getTitle() {
        return this.title;
    }
}
