package algat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public void start(Stage primaryStage) {
        FXMLLoader loader;
        Parent root = null;
        try {
            loader = new FXMLLoader(this.getClass().getResource("/layout/Intro.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Progetto AlgaT - Divide et Impera");
        primaryStage.setScene(new Scene(root, 900.0, 600.0));
        primaryStage.getIcons().add(new Image("/immagini/icon.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}