package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.service.UserService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import javax.persistence.EntityManager;
import java.io.IOException;

/**
 * Controls the buttons and changeable elements on login.fxml,
 * the page where you log into the application
 *
 * @author madslun
 * @version 1.0 22.03.2020
 */
public class Login {


    public TextField Username;
    public PasswordField Password;
    public Button signup;
    public Button login;
    public Text error_msg;
    private UserService userService;

    public Login() {
        EntityManager entityManager = App.ex.getEntityManager();
        userService = new UserService(entityManager);
    }

    /**
     * Method that changes scene to Sign Up page
     * @param actionEvent
     * @throws IOException
     */
    public void switchToSignup(ActionEvent actionEvent) throws IOException {
        App.setRoot("signup");
    }


    /**
     * Method that tries to log in to the page with the entered
     * credentials. If successful, scene changes to Main page. If not
     * it displays an error.
     * @throws IOException
     */
    public void login() throws IOException {
        String username = Username.getText();
        String password = Password.getText();
        if(userService.logIn(username, password)) {
            App.setRoot("main");
        }
        else {
            //Combination of username and password was not correct
            Password.setText(null);
            Password.setPromptText("*");
            Password.setStyle("-fx-prompt-text-fill: red");
            error_msg.setText("Incorrect username or password");
        }
    }

    /**
     * Method that registers a keyevent and tries to login if keyevent is entered
     * @param keyEvent
     * @throws IOException
     */
    public void enterLogin(KeyEvent keyEvent) throws IOException {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            login();
        }
    }

    /**
     * Method trying to login if login button is pressed
     * @param event
     * @throws IOException
     */
    public void buttonLogin(ActionEvent event) throws IOException {
        login();
    }
}
