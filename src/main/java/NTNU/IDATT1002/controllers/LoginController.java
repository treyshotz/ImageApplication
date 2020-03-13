package NTNU.IDATT1002.controllers;

import java.io.IOException;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoginController {

    @FXML
    public void switchToSecondary() throws IOException {
        App.setRoot("signup");
    }


    public void switchToLoggedIn(ActionEvent actionEvent) throws IOException {
        App.setRoot("logged-in");
    }
}
