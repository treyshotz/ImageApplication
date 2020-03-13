package NTNU.IDATT1002.controllers;

import java.io.IOException;

import NTNU.IDATT1002.App;
import javafx.fxml.FXML;

public class SignUpController {

    @FXML
    public void switchToPrimary() throws IOException {
        App.setRoot("login");
    }
}