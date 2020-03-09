package NTNU.IDATT1002;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("signup");
    }


    public void switchToLoggedIn(ActionEvent actionEvent) throws IOException {
        App.setRoot("logged-in");
    }
}
