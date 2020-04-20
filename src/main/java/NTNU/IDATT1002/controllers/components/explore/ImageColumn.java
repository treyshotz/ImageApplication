package NTNU.IDATT1002.controllers.components.explore;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.service.TagService;
import NTNU.IDATT1002.utils.ImageUtil;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


/**
 * Representing an individual column to present an image and other content.
 * To be used within an {@link ImageRow}.
 */
public class ImageColumn extends VBox {

    private Image image;

    /**
     * Create an {@link ImageColumn} containing given {@link Image}.
     *
     * @param image the image to display
     */
    public ImageColumn(Image image) {
        this.image = image;

        ImageView imageView = createImageView(image);
        StackPane stackPane = createStackPane(imageView);
        imageView.fitHeightProperty().bind(stackPane.heightProperty());
        imageView.fitWidthProperty().bind(stackPane.widthProperty());

        Text title = createTitle();
        Text tag = createText();

        applyStyling();

        this.getChildren().addAll(stackPane, title, tag);
    }

    /**
     * Return the {@link Text} holding the {@link Image} title.
     */
    private Text createTitle() {
        Text titleText = new Text(image.getTitle());
        titleText.setFont(Font.font(App.ex.getDefaultFont(), FontWeight.BOLD, 24));

        return titleText;
    }


    /**
     * Return the {@link Text} holding the {@link Image} tags.
     */
    private Text createText() {
        String tagsAsString = TagService.getTagsAsString(image.getTags());
        Text tagsText = new Text(tagsAsString);
        tagsText.setFont(Font.font(App.ex.getDefaultFont(), FontWeight.BOLD, 18));
        tagsText.setWrappingWidth(100);

        return tagsText;
    }


    /**
     * Return the {@link ImageView} holding the {@link Image}.
     */
    private ImageView createImageView(Image image) {
        ImageView imageView = new ImageView();
        imageView.setId(String.valueOf(image.getId()));

        imageView.setSmooth(false);
        imageView.setImage(ImageUtil.convertToFXImage(image));
        imageView.pickOnBoundsProperty().setValue(true);
        imageView.setPreserveRatio(true);

        return imageView;
    }


    /**
     * Return the {@link StackPane} binding this {@link ImageColumn}s
     * width and height to its properties.
     */
    private StackPane createStackPane(ImageView imageView) {
        StackPane stackPane = new StackPane();
        stackPane.setMinSize(100.0,100.0);

        stackPane.prefWidthProperty().bind(this.widthProperty());
        stackPane.prefHeightProperty().bind(this.heightProperty());
        stackPane.getChildren().add(imageView);

        return stackPane;
    }

    /**
     * Style this {@link ImageColumn}.
     */
    private void applyStyling() {
        this.setMinSize(360,310);
        this.setPrefSize(520,310);
        this.setAlignment(Pos.TOP_CENTER);
        this.setId(String.valueOf(image.getId()));
    }
}
