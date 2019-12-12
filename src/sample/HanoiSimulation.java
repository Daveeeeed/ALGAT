package sample;

import java.util.LinkedList;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Cylinder;
import javafx.scene.text.Font;

public class HanoiSimulation {

    @FXML
    private Button add;
    @FXML
    private Button remove;
    @FXML
    private Button random;
    @FXML
    private Button generate;
    @FXML
    private Button next;
    @FXML
    private Cylinder cylinder0;
    @FXML
    private Cylinder cylinder1;
    @FXML
    private Cylinder cylinder2;
    @FXML
    private Cylinder cylinderBase;
    @FXML
    private GridPane gridPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBox;

    private LinkedList<DiscNode> discNodeLinkedList = new LinkedList();
    private LinkedList<Label> labelLinkedList = new LinkedList();
    private Integer labelIndex = 0;
    private Integer indexCylinder0 = 8;
    private Integer indexCylinder1 = 8;
    private Integer indexCylinder2 = 8;

    public HanoiSimulation() {
    }

    private void TorreHanoi(int pioloOrigine, int pioloAppoggio, int pioloDestinazione, int firstIndex, int lastIndex, HBox[] hBoxes, int hBoxesIndex) {
        Label label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font(9.0D));
        label.setWrapText(true);
        label.setMinWidth(120.0D);
        label.setMaxWidth(120.0D);
        label.setPrefWidth(120.0D);
        label.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, new BorderWidths(1.0D))));
        if (firstIndex == lastIndex && hBoxesIndex < hBoxes.length) {
            label.setText("Sposta il disco numero " + (firstIndex + 1) + " dal piolo " + pioloOrigine + " al piolo " + pioloDestinazione);
            hBoxes[hBoxesIndex].getChildren().add(label);
            if (hBoxesIndex + 1 >= hBoxes.length) {
                this.labelLinkedList.add(label);
            }

            if (!this.vBox.getChildren().contains(hBoxes[hBoxesIndex])) {
                this.vBox.getChildren().add(hBoxes[hBoxesIndex]);
            }

            this.TorreHanoi(pioloOrigine, pioloAppoggio, pioloDestinazione, firstIndex, firstIndex, hBoxes, hBoxesIndex + 1);
        } else if (hBoxesIndex < hBoxes.length) {
            label.setText("Sposta " + (lastIndex - firstIndex + 1) + " dischi dal piolo " + pioloOrigine + " al piolo " + pioloDestinazione);
            hBoxes[hBoxesIndex].getChildren().add(label);
            if (!this.vBox.getChildren().contains(hBoxes[hBoxesIndex])) {
                this.vBox.getChildren().add(hBoxes[hBoxesIndex]);
            }

            this.TorreHanoi(pioloOrigine, pioloDestinazione, pioloAppoggio, firstIndex + 1, lastIndex, hBoxes, hBoxesIndex + 1);
            this.TorreHanoi(pioloOrigine, pioloAppoggio, pioloDestinazione, firstIndex, firstIndex, hBoxes, hBoxesIndex + 1);
            this.TorreHanoi(pioloAppoggio, pioloOrigine, pioloDestinazione, firstIndex + 1, lastIndex, hBoxes, hBoxesIndex + 1);
        }

    }

    private Integer correctCylinderIndex(int cylinderNumber) throws IllegalStateException {
        switch(cylinderNumber) {
            case 0:
                return this.indexCylinder0;
            case 1:
                return this.indexCylinder1;
            case 2:
                return this.indexCylinder2;
            default:
                throw new IllegalStateException("Unexpected value: " + cylinderNumber);
        }
    }

    private void increaseCylinderIndex(int cylinderNumber) throws IllegalStateException {
        Integer var2;
        Integer var3;
        switch(cylinderNumber) {
            case 0:
                var2 = this.indexCylinder0;
                var3 = this.indexCylinder0 = this.indexCylinder0 + 1;
                return;
            case 1:
                var2 = this.indexCylinder1;
                var3 = this.indexCylinder1 = this.indexCylinder1 + 1;
                return;
            case 2:
                var2 = this.indexCylinder2;
                var3 = this.indexCylinder2 = this.indexCylinder2 + 1;
                return;
            default:
                throw new IllegalStateException("Unexpected value: " + cylinderNumber);
        }
    }

    private void decreaseCylinderIndex(int cylinderNumber) throws IllegalStateException {
        Integer var2;
        Integer var3;
        switch(cylinderNumber) {
            case 0:
                var2 = this.indexCylinder0;
                var3 = this.indexCylinder0 = this.indexCylinder0 - 1;
                return;
            case 1:
                var2 = this.indexCylinder1;
                var3 = this.indexCylinder1 = this.indexCylinder1 - 1;
                return;
            case 2:
                var2 = this.indexCylinder2;
                var3 = this.indexCylinder2 = this.indexCylinder2 - 1;
                return;
            default:
                throw new IllegalStateException("Unexpected value: " + cylinderNumber);
        }
    }

    @FXML
    void addCylinder() {
        if (this.discNodeLinkedList.size() < 8) {
            DiscNode discNode = new DiscNode(this.discNodeLinkedList.size());
            this.gridPane.add(discNode.getCylinder(), 0, this.indexCylinder0);
            this.discNodeLinkedList.add(discNode);
            GridPane.setHalignment(discNode.getCylinder(), HPos.CENTER);
            Integer var2 = this.indexCylinder0;
            Integer var3 = this.indexCylinder0 = this.indexCylinder0 - 1;
        } else {
            ButtonType button = new ButtonType("OK");
            Alert alert = new Alert(AlertType.NONE);
            ImageView imageView = new ImageView(new Image("/immagini/alert2.png"));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(50.0D);
            alert.setTitle("Avviso");
            alert.setHeaderText("Quantità massima dischi raggiunta");
            alert.setContentText("Avvia la simulazione o rimuovi qualche disco.");
            alert.getButtonTypes().setAll(button);
            alert.setGraphic(imageView);
            alert.showAndWait();
        }

    }

    @FXML
    void removeCylinder() {
        if (this.discNodeLinkedList.size() > 0) {
            this.gridPane.getChildren().remove(this.discNodeLinkedList.size() - 1);
            this.discNodeLinkedList.removeLast();
            Integer var1 = this.indexCylinder0;
            Integer var2 = this.indexCylinder0 = this.indexCylinder0 + 1;
        } else {
            ButtonType button = new ButtonType("OK");
            Alert list_full = new Alert(AlertType.NONE);
            ImageView imageView = new ImageView(new Image("/immagini/alert2.png"));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(50.0D);
            list_full.setTitle("Avviso");
            list_full.setHeaderText("Impossibile rimuovere il disco");
            list_full.setContentText("Aggiungi qualche disco.");
            list_full.getButtonTypes().setAll(button);
            list_full.setGraphic(imageView);
            list_full.showAndWait();
        }

    }

    @FXML
    void randomizeCylinder() {
        for(int i = this.discNodeLinkedList.size(); i > 0; --i) {
            System.out.println("Rimosso");
            this.removeCylinder();
        }

        Random random = new Random();

        for(int i = random.nextInt(8); i >= 0; --i) {
            System.out.println("Aggiunto");
            this.addCylinder();
        }

    }

    @FXML
    void generateGraph() {
        if (this.discNodeLinkedList.size() > 1) {
            this.add.setDisable(true);
            this.remove.setDisable(true);
            this.random.setDisable(true);
            this.generate.setDisable(true);
            this.next.setDisable(false);
            HBox[] hBoxes = new HBox[this.discNodeLinkedList.size()];

            for(int i = 0; i < hBoxes.length; ++i) {
                hBoxes[i] = new HBox();
                hBoxes[i].setAlignment(Pos.CENTER);
            }

            this.TorreHanoi(0, 1, 2, this.discNodeLinkedList.indexOf(this.discNodeLinkedList.getFirst()), this.discNodeLinkedList.indexOf(this.discNodeLinkedList.getLast()), hBoxes, 0);
        }

    }

    @FXML
    void nextStep() {
        if (this.labelIndex < this.labelLinkedList.size()) {
            String string = this.labelLinkedList.get(this.labelIndex).getText();
            GridPane.setColumnIndex(this.gridPane.getChildren().get(Character.getNumericValue(string.charAt(23)) - 1), Character.getNumericValue(string.charAt(46)));
            GridPane.setRowIndex(this.gridPane.getChildren().get(Character.getNumericValue(string.charAt(23)) - 1), this.correctCylinderIndex(Character.getNumericValue(string.charAt(46))));
            this.increaseCylinderIndex(Character.getNumericValue(string.charAt(35)));
            this.decreaseCylinderIndex(Character.getNumericValue(string.charAt(46)));
            Integer var2 = this.labelIndex;
            Integer var3 = this.labelIndex = this.labelIndex + 1;
        } else {
            ButtonType button = new ButtonType("OK");
            Alert alert = new Alert(AlertType.NONE);
            ImageView imageView = new ImageView(new Image("/immagini/alert2.png"));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(50.0D);
            alert.setTitle("Avviso");
            alert.setHeaderText("Simulazione Terminata");
            alert.setContentText("Hai terminato la simulazionecon successo.");
            alert.getButtonTypes().setAll(button);
            alert.setGraphic(imageView);
            alert.showAndWait();
            this.next.setDisable(true);
        }

    }
}
