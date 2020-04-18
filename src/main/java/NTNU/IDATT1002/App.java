package NTNU.IDATT1002;

import NTNU.IDATT1002.controllers.DataExchange;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    public static DataExchange ex;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        ex = new DataExchange();
        ex.setHostServices(getHostServices());

        scene = new Scene(loadFXML("login"));
        scene.getStylesheets().add("resources/NTNU.IDATT1002/style.css");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Shut down GeoApiContext on application stop to gracefully close the connection.
     */
    @Override
    public void stop(){
       ex.getGeoApiContext().shutdown();
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