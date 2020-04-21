package NTNU.IDATT1002.controllers.components.exploreAlbums;

import NTNU.IDATT1002.App;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * HBox for holding a description.
 */
public class DescriptionHBox extends HBox {

    /**
     * Create HBox holding given description and corresponding label.
     */
    public DescriptionHBox(String description) {
        Text descriptionLabel = new Text("Description: ");
        descriptionLabel.setFont(Font.font(App.ex.getDefaultFont(), FontWeight.BOLD, 16));

        Text descriptionText = new Text(description);
        descriptionText.setWrappingWidth(500);
        descriptionText.setFont(Font.font(App.ex.getDefaultFont(),16));
        this.getChildren().addAll(descriptionLabel, descriptionText);
    }
}
