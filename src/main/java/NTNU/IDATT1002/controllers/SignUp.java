package NTNU.IDATT1002.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.service.UserService;
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
    public UserService userService = new UserService();

    public Button signup_btn;

    /**
     * Method that changes stage to Login
     * @param actionEvent
     * @throws IOException
     */
    public void signup(ActionEvent actionEvent) throws IOException {
        String username = signup_username.getText();
        String firstName = signup_firstName.getText();
        String lastName = signup_lastName.getText();
        String email = signup_email.getText();
        String password = signup_password.getText();
        String phoneCode = signup_phoneCode.getText();
        String phoneNr = signup_phoneNr.getText();
        //TODO: Find out how to take date as a Date object
        Date date = new Date(System.currentTimeMillis());

        if(userService.createUser(email, username, firstName, lastName, phoneCode, phoneNr, date, password).isPresent()) {
            //TODO: Return message to user to confirm that user has been succsessfully registered
            App.setRoot("login");
        }


        //TODO: Verify that all fields is properly filled. I think this will be done by Usermodel.
    }
}
