package NTNU.IDATT1002;

import NTNU.IDATT1002.controllers.DataExchange;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    public static DataExchange ex;
    public static HostServices hostServices;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        ex = new DataExchange();
        ex.setHostServices(getHostServices());
        scene = new Scene(loadFXML("login"));
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}