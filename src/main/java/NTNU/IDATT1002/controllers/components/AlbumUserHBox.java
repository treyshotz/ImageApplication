package NTNU.IDATT1002.controllers.components;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.User;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * HBox for holding an albums' user.
 */
public class AlbumUserHBox extends HBox {

    /**
     * Create HBox holding given user and create a corresponding label.
     */
    public AlbumUserHBox(User user) {
        Text authorLabel = new Text("Author: ");
        authorLabel.setFont(Font.font(App.ex.getDefaultFont(), FontWeight.BOLD, 24));

        Text author = new Text(user.getUsername());
        author.setFont(Font.font(App.ex.getDefaultFont(),24));

        this.getChildren().addAll(authorLabel, author);
    }

}
