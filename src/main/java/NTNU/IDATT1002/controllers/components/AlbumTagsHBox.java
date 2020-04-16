package NTNU.IDATT1002.controllers.components;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.service.TagService;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

/**
 * HBox for holding an albums' tags.
 */
public class AlbumTagsHBox extends HBox {

    /**
     * Create HBox holding given tags and crete corresponding label.
     */
    public AlbumTagsHBox(List<Tag> tags) {
        Text tagsLabel = new Text("Tags: ");
        tagsLabel.setFont(Font.font(App.ex.getDefaultFont(), FontWeight.BOLD, 16));

        String tagsAsString = TagService.getTagsAsString(tags);
        Text tagsText = new Text(tagsAsString);
        tagsText.setFont(Font.font(App.ex.getDefaultFont(), 16));
        this.getChildren().addAll(tagsLabel, tagsText);
    }
}
