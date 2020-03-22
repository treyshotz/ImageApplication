package NTNU.IDATT1002.controllers;

import java.io.IOException;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * Controls the buttons and changeable elements on login.fxml,
 * the page where you log into the application
 * @version 1.0 22.03.2020
 */
public class Login {

    public Button signup;
    public Button login;

    /**
     * Method that changes stage to Sign Up page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToSignup(ActionEvent actionEvent) throws IOException {
        App.setRoot("signup");
    }

    /**
     * Method that changes stage to Main page
     * @param actionEvent
     * @throws IOException
     */
    public void login(ActionEvent actionEvent) throws IOException {
        //TODO: Verify username and password
        App.setRoot("main");
    }
}
