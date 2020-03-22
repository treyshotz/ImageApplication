package NTNU.IDATT1002.controllers;

import java.io.IOException;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Login {

    public Button signup;
    public Button login;

    /**
     * Method that switches to sign up page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToSignup(ActionEvent actionEvent) throws IOException {
        App.setRoot("signup");
    }

    /**
     * Method that switches to main page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToMain(ActionEvent actionEvent) throws IOException {
        App.setRoot("main");
    }
}
