package NTNU.IDATT1002.controllers.components;

import NTNU.IDATT1002.App;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * HBox for holding an albums' title.
 */
public class AlbumTitleHBox extends HBox {

    /**
     * Create HBox holding given title and create a corresponding label.
     * It is clickable, and switches to View Album page of that album todo
     */
    public AlbumTitleHBox(String title) {
        Text label = new Text("Title: ");
        label.setFont(Font.font(App.ex.getDefaultFont(), FontWeight.BOLD, 48));
        Text titleText = new Text(title);
        titleText.setFont(Font.font(App.ex.getDefaultFont(),48));

        this.getChildren().addAll(label, titleText);
    }
}
