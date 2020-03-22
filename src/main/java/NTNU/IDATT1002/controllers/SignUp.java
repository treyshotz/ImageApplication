package NTNU.IDATT1002.controllers;

import java.io.IOException;

import NTNU.IDATT1002.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

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
     * Method that switches to login page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToLogin(ActionEvent actionEvent) throws IOException {
        App.setRoot("login");
    }
}