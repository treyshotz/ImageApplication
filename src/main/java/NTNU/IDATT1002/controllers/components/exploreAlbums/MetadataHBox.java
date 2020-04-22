package NTNU.IDATT1002.controllers.components.exploreAlbums;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Metadata;
import NTNU.IDATT1002.utils.MetadataStringFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * HBox for holding metadata.
 */
public class MetadataHBox extends HBox {

    /**
     * Create HBox holding given metadata and corresponding label.
     */
    public MetadataHBox(Metadata metadata) {
        Text descriptionLabel = new Text("Metadata: ");
        descriptionLabel.setFont(Font.font(App.ex.getDefaultFont(), FontWeight.BOLD, 16));

        Text descriptionText = new Text(MetadataStringFormatter.format(metadata, "\n"));
        descriptionText.setWrappingWidth(500);
        descriptionText.setFont(Font.font(App.ex.getDefaultFont(),16));
        this.getChildren().addAll(descriptionLabel, descriptionText);
    }
}
