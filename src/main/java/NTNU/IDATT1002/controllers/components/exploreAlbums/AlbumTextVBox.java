package NTNU.IDATT1002.controllers.components.exploreAlbums;

import NTNU.IDATT1002.models.Album;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * VBox for holding an albums' text components.
 */
public class AlbumTextVBox extends VBox {

    private HBox title;

    /**
     * Create VBox holding the text fields of given album.
     */
    public AlbumTextVBox(Album album) {
        this.align();

        title = new TitleHBox(album.getTitle());
        HBox author = new UserHBox(album.getUser());
        HBox tags = new TagsHBox(album.getTags());
        HBox description = new DescriptionHBox(album.getDescription());

        this.getChildren().addAll(title, author, tags, description);
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
