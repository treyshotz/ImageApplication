package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

/**
 * Controls the buttons and changeable elements on main.fxml,
 * a page where you explore albums
 *
 * @version 1.0 22.03.2020
 */
public class Main extends NavBarController {

    public Button uploadBtn;

    /**
     * Tells {@link DataExchange} that main page is visited.
     */
    public Main(){
        App.ex.newPage("main");
    }

}