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
        LocalDate birthLocalDate = signup_birthDate.getValue();



        if (validateInfo(username, firstName, lastName, email, password, phoneCode, phoneNr, birthLocalDate)) {
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
     * Checks both that the user put info in the necessary textfields and that the username and/or email isnt in use.
     *
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param phoneCode
     * @param phoneNR
     * @param birthdate
     *
     */
        private boolean validateInfo (String username, String firstName, String lastName, String email, String password, String phoneCode, String phoneNR, LocalDate birthdate){
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

            if (username.isEmpty()){
                signup_username.setStyle("-fx-prompt-text-fill: red");
                signup_username.setPromptText("Please enter a username");
                check = false;
            }if (firstName.isEmpty()){
                signup_firstName.setStyle("-fx-prompt-text-fill: red");
                signup_firstName.setPromptText("Please enter your firstname");
                check = false;
            }if (lastName.isEmpty()){
                signup_lastName.setStyle("-fx-prompt-text-fill: red");
                signup_lastName.setPromptText("Please enter your surname");
                check = false;
            }if (email.isEmpty()){
                signup_email.setStyle("-fx-prompt-text-fill: red");
                signup_email.setPromptText("Please enter your email");
                check = false;
            }if (password.isEmpty()){
                signup_password.setStyle("-fx-prompt-text-fill: red");
                signup_password.setPromptText("Please enter a password");
                check = false;
            }if (phoneCode.isEmpty() || !(Pattern.matches("[0-9]+", phoneCode))){
                signup_phoneCode.clear();
                signup_phoneCode.setStyle("-fx-prompt-text-fill: red");
                signup_phoneCode.setPromptText("Please enter phnoe code");
                check = false;
            }if (phoneNR.isEmpty() || !(Pattern.matches("[0-9]+", phoneNR))){
                signup_phoneNr.clear();
                signup_phoneNr.setStyle("-fx-prompt-text-fill: red");
                signup_phoneNr.setPromptText("Please enter your phone number");
                check = false;
            }if (birthdate == null){
                signup_birthDate.setStyle("-fx-prompt-text-fill: red");
                signup_birthDate.setPromptText("Please enter your birthdate");
                check = false;
            }
            return check;
        }

}
