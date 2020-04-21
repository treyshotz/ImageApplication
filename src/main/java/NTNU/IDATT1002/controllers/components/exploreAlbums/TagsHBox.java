package NTNU.IDATT1002.controllers.components.exploreAlbums;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.service.TagService;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

/**
 * HBox for holding tags of image or album.
 */
public class TagsHBox extends HBox {

    /**
     * Create HBox holding given tags and crete corresponding label.
     */
    public TagsHBox(List<Tag> tags) {
        Text tagsLabel = new Text("Tags: ");
        tagsLabel.setFont(Font.font(App.ex.getDefaultFont(), FontWeight.BOLD, 16));

        Text tagsText = new Text();
        if (tags != null){
            String tagsAsString = TagService.getTagsAsString(tags);
            tagsText.setText(tagsAsString);
            tagsText.setFont(Font.font(App.ex.getDefaultFont(), 16));
        }

        this.getChildren().addAll(tagsLabel, tagsText);
    }
}
