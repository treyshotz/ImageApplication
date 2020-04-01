package NTNU.IDATT1002.controllers;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.service.UserService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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

    public Text signup_error;
    public UserService userService;
    
    public Button signup_btn;

    public SignUp() {
        EntityManager entityManager = App.ex.getEntityManager();
        userService = new UserService(entityManager);
    }

    /**
     * Method that changes scene to Login
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

        ArrayList<TextField> signupFields = new ArrayList<>();
        signupFields.add(signup_firstName);
        signupFields.add(signup_lastName);
        signupFields.add(signup_username);
        signupFields.add(signup_email);
        signupFields.add(signup_password);
        signupFields.add(signup_phoneCode);
        signupFields.add(signup_phoneNr);
        boolean blank = false;
        for (TextField signupField : signupFields) {
            if (signupField.getText().trim().isEmpty()){
                signupField.setPromptText("*");
                signupField.setStyle("-fx-prompt-text-fill: red");
                blank = true;
            }
        }

        if (signup_birthDate.getValue() == null){
            signup_birthDate.setPromptText("*");
            signup_error.setText("* fields required to sign up");
        }
        else if (blank) {
            signup_error.setText("* fields required to sign up");
        }
        else{
            LocalDate birthLocalDate = signup_birthDate.getValue();
            Instant instant = Instant.from(birthLocalDate.atStartOfDay(ZoneId.systemDefault()));
            Date birthDate = Date.from(instant);
            if(userService.createUser(email, username, firstName, lastName, phoneCode, phoneNr, birthDate, password).isPresent()) {
                //TODO: Return message to user to confirm that user has been succsessfully registered
                App.setRoot("login");
            }
        }
    }

    public void cancel(ActionEvent event) throws IOException {
        App.setRoot("login");
    }
}
