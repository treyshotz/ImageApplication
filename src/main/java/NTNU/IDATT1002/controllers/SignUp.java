package NTNU.IDATT1002.controllers;

import java.io.IOException;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class SignUp {

    public Button signup;

    /**
     * Method that switches to login page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToLogin(ActionEvent actionEvent) throws IOException {
        App.setRoot("login");
    }
}