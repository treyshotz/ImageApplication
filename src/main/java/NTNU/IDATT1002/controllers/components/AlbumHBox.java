package NTNU.IDATT1002.controllers.components;

import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.utils.ImageUtil;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


/**
 * HBox for holding an albums text components ({@link AlbumTextVBox})
 * and an preview {@link AlbumImageView}.
 */
public class AlbumHBox extends HBox {

    private ImageView previewImageView;
    private AlbumTextVBox albumTextVBox;

    /**
     * Create the HBox for a single album with a placeholder for a preview image to be added later.
     */
    public AlbumHBox(Album album) {
        this.setId(album.getId().toString());
        this.setPrefWidth(1520);
        this.setPrefHeight(300);

        albumTextVBox = new AlbumTextVBox(album);
        previewImageView = new AlbumImageView();

        this.getChildren().addAll(previewImageView, albumTextVBox);
    }

    /**
     * Replace the current image preview placeholder with given {@link ImageView}.
     *
     * @param image
     */
    public void replaceAlbumImageViewWith(Image image) {
        javafx.scene.image.Image imageToSet = ImageUtil.convertToFXImage(image);

        previewImageView.setImage(imageToSet);
    }

    public Node addOnMouseClickedEventHandlerToComponent(
            Node component, EventHandler<? super MouseEvent> eventHandler) {
        component.setOnMouseClicked(eventHandler);
        return component;
    }

    public Node getAlbumImageView() {
        return this.previewImageView;
    }

    public Node getAlbumTitleHBox() {
        return this.albumTextVBox.getTitle();
    }
}
