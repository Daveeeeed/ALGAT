package algat;

import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Alert {

    /**
     * Mostra un messaggio d'allerta.
     * @param header testo del titolo del messaggio
     * @param content testo del contenuto del messaggio
     */
    public static void displayAlert(String header, String content) {
        ButtonType button = new ButtonType("OK");
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.NONE);
        ImageView imageView = new ImageView(new Image("/immagini/alert2.png"));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50.0D);
        alert.setTitle("Avviso");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(button);
        alert.setGraphic(imageView);
        alert.showAndWait();
    }

}
