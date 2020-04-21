package NTNU.IDATT1002.controllers.components.exploreAlbums;

import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.utils.ImageUtil;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


/**
 * HBox for holding an albums text components ({@link ImageTextVBox})
 * and an preview {@link StyledImageView}.
 */
public class ImageHBox extends HBox {

    private ImageView placeholder;
    private ImageTextVBox imageTextVBox;

    /**
     * Create the HBox for a single image.
     */
    public ImageHBox(Image image) {
        this.setId(image.getId().toString());
        this.setPrefWidth(1520);
        this.setPrefHeight(300);

        imageTextVBox = new ImageTextVBox(image);
        placeholder = new StyledImageView();

        this.getChildren().addAll(placeholder, imageTextVBox);
    }

    /**
     * Replace the current image placeholder with given {@link Image}.
     *
     * @param image the image to replace with
     */
    public void replaceImageViewWith(Image image) {
        javafx.scene.image.Image imageToSet = ImageUtil.convertToFXImage(image);
        placeholder.setImage(imageToSet);
    }

    public void addOnMouseClickedEventHandlerToComponent(
            Node component, EventHandler<? super MouseEvent> eventHandler) {
        component.setOnMouseClicked(eventHandler);
    }

    public Node getImageView() {
        return this.placeholder;
    }

    public Node getImageTitleHBox() {
        return this.imageTextVBox.getTitle();
    }
}
