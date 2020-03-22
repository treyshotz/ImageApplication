package NTNU.IDATT1002.controllers;

import java.io.IOException;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Controls the buttons and changeable elements on signup.fxml,
 * a page where you create a new user for the application
 * @version 1.0 22.03.2020
 */
public class SignUp {

    public GridPane signup_form;
    public TextField signup_firstName;
    public TextField signup_lastName;
    public TextField signup_username;
    public TextField signup_email;
    public PasswordField signup_password;
    public TextField signup_phoneCode;
    public TextField signup_phoneNr;
    public DatePicker signup_birthDate;

    public Button signup_btn;

    /**
     * Method that changes stage to Login
     * @param actionEvent
     * @throws IOException
     */
    public void signup(ActionEvent actionEvent) throws IOException {
        //TODO: Verify that all fields is properly filled
        //TODO: Register new user in database
        App.setRoot("login");
    }
}