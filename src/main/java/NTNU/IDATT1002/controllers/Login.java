package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.service.UserService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.persistence.EntityManager;
import java.io.IOException;

/**
 * Controls the buttons and changeable elements on login.fxml,
 * the page where you log into the application
 * @version 1.0 22.03.2020
 */
public class Login {


    public TextField Username;
    public PasswordField Password;
    public Button signup;
    public Button login;
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
     * Method that changes scene to Main page
     * @param actionEvent
     * @throws IOException
     */
    public void login(ActionEvent actionEvent) throws IOException {
        String username = Username.getText();
        String password = Password.getText();
        if(userService.logIn(username, password)) {
            App.setRoot("main");
        }
        else {
            //TODO: Make popup window to inform the user that wrong details was entered
        }
        //TODO: Else raise warning maybe?
    }
}
