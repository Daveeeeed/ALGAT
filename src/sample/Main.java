package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public Main() {
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Intro.fxml"));
        Parent root = (Parent)loader.load();
        primaryStage.setTitle("Progetto AlgaT - Divide et Impera");
        primaryStage.setScene(new Scene(root, 900.0D, 600.0D));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}