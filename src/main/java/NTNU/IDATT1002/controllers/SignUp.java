package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.service.UserService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Controls the buttons and changeable elements on signup.fxml,
 * a page where you create a new user for the application
 *
 * @author madslun, Simon Jensen
 * @version 1.0 22.03.2020
 */
public class SignUp {

    public GridPane signup_form;
    public TextField signup_firstName;
    public TextField signup_lastName;
    public TextField signup_username;
    public TextField signup_email;
    public PasswordField signup_password;
    public PasswordField confirm_password;
    public TextField signup_phoneCode;
    public TextField signup_phoneNr;
    public DatePicker signup_birthDate;

    public UserService userService;
    public Button signup_btn;
    private boolean check;

    public SignUp() {
        EntityManager entityManager = App.ex.getEntityManager();
        userService = new UserService(entityManager);
    }

    /**
     * Method that changes scene if signup scheme is filled correctly to Login
     * @param actionEvent
     * @throws IOException
     */
    public void signup(ActionEvent actionEvent) throws IOException {
        String username = signup_username.getText();
        String firstName = signup_firstName.getText();
        String lastName = signup_lastName.getText();
        String email = signup_email.getText();
        String password = signup_password.getText();
        String confirmedPassword = confirm_password.getText();
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
        LocalDate birthLocalDate = signup_birthDate.getValue();



        if (validateInfo(username, firstName, lastName, email, password, confirmedPassword, phoneCode, phoneNr, birthLocalDate)) {
          {
              Instant instant = Instant.from(birthLocalDate.atStartOfDay(ZoneId.systemDefault()));
              Date birthDate = Date.from(instant);


              if (userService.createUser(email, username, firstName, lastName, phoneCode, phoneNr, birthDate, password).isPresent()) {
                    //TODO: Return message to user to confirm that user has been succsessfully registered
                    App.setRoot("login");
                }
            }}
        }

        public void cancel (ActionEvent event) throws IOException {
            App.setRoot("login");
        }


    /**
     * Checks both that the user put info in the necessary text fields and that the username and/or email isn't in use.
     *
     * @param username username
     * @param firstName first name
     * @param lastName last name
     * @param email email address
     * @param password password
     * @param phoneCode phone code
     * @param phoneNR phone number
     * @param birthdate birth date
     *
     * @return whether the parameters is valid
     */
        private boolean validateInfo (String username, String firstName, String lastName, String email, String password, String confirmedPassword, String phoneCode, String phoneNR, LocalDate birthdate){
            check = true;

            userService.getUsers().stream().forEach(x -> {
                if (x.getUsername().equals(username)) {
                    signup_username.clear();
                    signup_username.setStyle("-fx-prompt-text-fill: red");
                    signup_username.setPromptText("Username already taken");
                    check = false;
                }
            });

            userService.getUsers().stream().forEach(x -> {
                if (x.getEmail().equals(email)) {
                    signup_username.clear();
                    signup_username.setStyle("-fx-prompt-text-fill: red");
                    signup_username.setPromptText("Email is already in use");
                    check = false;
                }
            });

            if (username.isEmpty() || username.isBlank()){
                signup_username.setStyle("-fx-prompt-text-fill: red");
                signup_username.setPromptText("Please enter a username");
                check = false;
            }if (firstName.isEmpty() || firstName.isBlank()){
                signup_firstName.setStyle("-fx-prompt-text-fill: red");
                signup_firstName.setPromptText("Please enter your firstname");
                check = false;
            }if (lastName.isEmpty() || lastName.isBlank()){
                signup_lastName.setStyle("-fx-prompt-text-fill: red");
                signup_lastName.setPromptText("Please enter your surname");
                check = false;
            }if (email.isEmpty() || email.isBlank()){
                signup_email.setStyle("-fx-prompt-text-fill: red");
                signup_email.setPromptText("Please enter your email");
                check = false;
            }if (password.isEmpty() || password.isBlank()){
                signup_password.setStyle("-fx-prompt-text-fill: red");
                signup_password.setPromptText("Please enter a password");
                check = false;
            }if (phoneCode.isEmpty() || phoneCode.isBlank() || !(Pattern.matches("[0-9]+", phoneCode))){
                signup_phoneCode.clear();
                signup_phoneCode.setStyle("-fx-prompt-text-fill: red");
                signup_phoneCode.setPromptText("Please enter phnoe code");
                check = false;
            }if (phoneNR.isEmpty() || phoneNR.isBlank() || !(Pattern.matches("[0-9]+", phoneNR))){
                signup_phoneNr.clear();
                signup_phoneNr.setStyle("-fx-prompt-text-fill: red");
                signup_phoneNr.setPromptText("Please enter your phone number");
                check = false;
            }if (birthdate == null){
                signup_birthDate.setStyle("-fx-prompt-text-fill: red");
                signup_birthDate.setPromptText("Please enter your birthdate");
                check = false;
            }if (!(password.equals(confirmedPassword))) {
                signup_password.clear();
                confirm_password.clear();
                signup_password.setStyle("-fx-prompt-text-fill: red");
                signup_password.setPromptText("Your passwords did not match");
                check = false;
            }
            return check;
        }

}
