package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public Main() {
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Intro.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Progetto AlgaT - Divide et Impera");
        primaryStage.setScene(new Scene(root, 900.0, 600.0));
        primaryStage.getIcons().add(new Image("/immagini/icon.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}