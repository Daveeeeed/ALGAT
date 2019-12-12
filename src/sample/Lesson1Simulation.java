package sample;

import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Lesson1Simulation implements Initializable {
    final int J_INDEX_Y = 115;
    final int I_INDEX_Y = 85;
    final int TEXT_Y = 180;
    final int MAX_ARRAY_SIZE = 8;
    final int MAX_ELEMENT_VALUE = 100;
    @FXML
    private VBox vBoxRoot;
    @FXML
    private ToolBar toolbar;
    @FXML
    private Button addButton;
    @FXML
    private Button addThisButton;
    @FXML
    private TextField textField;
    @FXML
    private Button removeButton;
    @FXML
    private Button randomButton;
    @FXML
    private Button startButton;
    @FXML
    private Button nextButton;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private VBox vBox;
    @FXML
    private Label i_index_value;
    @FXML
    private Label j_index_value;
    @FXML
    private Label pin_index_value;
    private LinkedList<SortTreeRow> tree;
    private Label i_pos;
    private Label j_pos;
    private Label description;
    private int i_val;
    private int j_val;
    private int pin_val;
    private boolean isDescriptionEmpty;

    public Lesson1Simulation() {
    }

    private void displayAlert(String header, String content) {
        ButtonType button = new ButtonType("OK");
        Alert alert = new Alert(AlertType.NONE);
        ImageView imageView = new ImageView(new Image("/immagini/alert2.png"));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50.0D);
        alert.setTitle("Avviso");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(new ButtonType[]{button});
        alert.setGraphic(imageView);
        alert.showAndWait();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("FATTO");
        this.tree = new LinkedList();
        this.tree.add(new SortTreeRow());
        this.vBox.getChildren().add(((SortTreeRow)this.tree.getFirst()).getRowBox());
        ((SortTreeRow)this.tree.getFirst()).addNode(new SortTreeNode());
        ((SortTreeRow)this.tree.getFirst()).getRowBox().getChildren().add(((SortTreeRow)this.tree.getFirst()).getFirstNode().gethBox());
        this.i_index_value.setText("---");
        this.j_index_value.setText("---");
        this.pin_index_value.setText("---");
        this.nextButton.setDisable(true);
        this.isDescriptionEmpty = true;
    }

    @FXML
    void add() {
        if (((SortTreeRow)this.tree.getFirst()).getFirstNode().getElements().size() < 8) {
            Random random = new Random();
            ((SortTreeRow)this.tree.getFirst()).getFirstNode().addElement(random.nextInt(100));
        } else {
            this.displayAlert("Capienza massima dell'array raggiunta", "Avvia la simulazione o rimuovi qualche elemento.");
        }

    }

    @FXML
    void addThis() {
        if (((SortTreeRow)this.tree.getFirst()).getFirstNode().getElements().size() < 8) {
            try {
                int value = Integer.parseInt(this.textField.getText());
                if (value >= 0 && value < 100) {
                    ((SortTreeRow)this.tree.getFirst()).getFirstNode().addElement(value);
                } else {
                    this.displayAlert("Formato numero errato", "Inserisci un numero compreso tra 0 e 99.");
                }
            } catch (Exception var2) {
                this.displayAlert("Formato numero errato", "Inserisci un numero nel formato valido.");
            }
        } else {
            this.displayAlert("Capienza massima dell'array raggiunta", "Avvia la simulazione o rimuovi qualche elemento.");
        }

        this.textField.clear();
    }

    @FXML
    void remove() {
        if (((SortTreeRow)this.tree.getFirst()).getFirstNode().getElements().size() > 0) {
            ((SortTreeRow)this.tree.getFirst()).getFirstNode().removeLastElement();
        } else {
            this.displayAlert("Impossibile rimuovere l'elemento", "Aggiungi qualche elemento per continuare.");
        }

    }

    @FXML
    void randomize() {
        while(((SortTreeRow)this.tree.getFirst()).getFirstNode().getElements().size() > 0) {
            this.remove();
        }

        Random random = new Random();
        int rep = random.nextInt(8);

        for(int i = 0; i <= rep; ++i) {
            this.add();
        }

    }

    @FXML
    void start() {
        if (((SortTreeNode)((SortTreeRow)this.tree.getFirst()).getNodes().getFirst()).getElements().size() > 0) {
            this.addButton.setDisable(true);
            this.addThisButton.setDisable(true);
            this.textField.setDisable(true);
            this.removeButton.setDisable(true);
            this.randomButton.setDisable(true);
            this.startButton.setDisable(true);
            this.nextButton.setDisable(false);
            this.i_val = 0;
            this.j_val = 0;
            this.pin_val = 0;
            ((SortTreeRow)this.tree.getFirst()).getFirstNode().pinPassive();
            this.i_pos = new Label("i");
            this.i_pos.setFont(Font.font("Droid Sans Mono", FontWeight.BOLD, 28.0D));
            this.i_pos.setAlignment(Pos.TOP_CENTER);
            this.j_pos = new Label("j");
            this.j_pos.setFont(Font.font("Droid Sans Mono", FontWeight.BOLD, 28.0D));
            this.j_pos.setAlignment(Pos.TOP_CENTER);
            this.description = new Label("SIMULAZIONE AVVIATA\nPREMI IL BOTTONE \"PROSSIMO STEP\" PER PROSEGUIRE\nPER OGNI PASSO AL PRIMO CLICK SARA' VISUALIZZATO IL CODICE,\nCHE VERRA' ESEGUITO AL SECONDO CLICK");
            this.description.setFont(Font.font("Droid Sans Mono", FontWeight.BOLD, 18.0D));
            this.description.setAlignment(Pos.CENTER);
            this.anchorPane.getChildren().addAll(new Node[]{this.i_pos, this.j_pos, this.description});
            this.i_pos.setLayoutY(85.0D);
            this.j_pos.setLayoutY(115.0D);
            this.description.setLayoutY(180.0D);
            AnchorPane.setLeftAnchor(this.description, 0.0D);
            AnchorPane.setRightAnchor(this.description, 0.0D);
            this.updateIndex();
        } else {
            this.displayAlert("Elementi non sufficienti", "Aggiungi qualche elemento per continuare.");
        }

    }

    private boolean isILowerThanPin() {
        return ((Element)((SortTreeNode)((SortTreeRow)this.tree.getFirst()).getNodes().getFirst()).getElements().get(this.i_val)).getValue() < ((Element)((SortTreeNode)((SortTreeRow)this.tree.getFirst()).getNodes().getFirst()).getElements().get(this.pin_val)).getValue();
    }

    @FXML
    void nextStep() {
        if (this.pin_val != -1) {
            if (this.i_val != -1) {
                if (this.isILowerThanPin()) {
                    if (!this.isDescriptionEmpty) {
                        this.description.setText("");
                        this.isDescriptionEmpty = true;
                        ++this.j_val;
                        ((SortTreeRow)this.tree.getFirst()).getFirstNode().swapElements(this.j_val, this.i_val);
                        ++this.i_val;
                    } else {
                        this.description.setText("A[i] < A[perno] QUINDI:\nj ← j+1,\nA[i] ↔ A[j],\ni ← i+1");
                        this.isDescriptionEmpty = false;
                    }
                } else if (!this.isDescriptionEmpty) {
                    this.description.setText("");
                    this.isDescriptionEmpty = true;
                    ++this.i_val;
                } else {
                    this.description.setText("A[i] >= A[perno] QUINDI:\ni ← i+1");
                    this.isDescriptionEmpty = false;
                }

                if (this.i_val >= ((SortTreeNode)((SortTreeRow)this.tree.getFirst()).getNodes().getFirst()).getElements().size()) {
                    this.i_val = -1;
                }
            } else if (!this.isDescriptionEmpty) {
                this.description.setText("");
                this.isDescriptionEmpty = true;
                ((SortTreeRow)this.tree.getFirst()).getFirstNode().swapElements(this.j_val, this.pin_val);
                this.pin_val = -1;
                this.nextButton.setText("Risolvi Successivi");
            } else {
                this.description.setText("i > A[size] QUINDI:\nA[perno] ↔ A[j],\nperno ← j,\nQuickSort(A,primo,j-1),\nQuickSort(A,j+1,ultimo)");
                this.isDescriptionEmpty = false;
            }

            this.updateIndex();
        } else {
            this.description.setText("");
            this.i_pos.setText("");
            this.j_pos.setText("");
            this.i_index_value.setText("---");
            this.j_index_value.setText("---");
            this.pin_index_value.setText("---");
            this.solveLastRow();
        }

    }

    private void updateIndex() {
        if (this.i_val == -1) {
            this.i_index_value.setText(Integer.toString(((SortTreeNode)((SortTreeRow)this.tree.getFirst()).getNodes().getFirst()).getElements().size() - 1));
        } else {
            this.i_index_value.setText(Integer.toString(this.i_val));
            this.i_pos.setLayoutX(((SortTreeNode)((SortTreeRow)this.tree.getFirst()).getNodes().getFirst()).getElementX(this.i_val));
        }

        this.j_index_value.setText(Integer.toString(this.j_val));
        this.j_pos.setLayoutX(((SortTreeNode)((SortTreeRow)this.tree.getFirst()).getNodes().getFirst()).getElementX(this.j_val));

        int i;
        for(i = ((SortTreeNode)((SortTreeRow)this.tree.getFirst()).getNodes().getFirst()).getElements().size() - 1; i > this.j_val; --i) {
            ((Element)((SortTreeNode)((SortTreeRow)this.tree.getFirst()).getNodes().getFirst()).getElements().get(i)).getRectangle().setFill(Color.LIGHTGRAY);
        }

        for(i = 0; i <= this.j_val; ++i) {
            ((Element)((SortTreeNode)((SortTreeRow)this.tree.getFirst()).getNodes().getFirst()).getElements().get(i)).getRectangle().setFill(Color.LIGHTPINK);
        }

        if (this.pin_val == -1) {
            ((Element)((SortTreeNode)((SortTreeRow)this.tree.getFirst()).getNodes().getFirst()).getElements().get(this.j_val)).getRectangle().setFill(Color.YELLOW);
            this.pin_index_value.setText(Integer.toString(this.j_val));
        } else {
            ((Element)((SortTreeNode)((SortTreeRow)this.tree.getFirst()).getNodes().getFirst()).getElements().get(this.pin_val)).getRectangle().setFill(Color.YELLOW);
            this.pin_index_value.setText(Integer.toString(this.pin_val));
        }

    }

    private void solveLastRow() {
        this.tree.addLast(new SortTreeRow());
        this.vBox.getChildren().add(((SortTreeRow)this.tree.getLast()).getRowBox());
        Iterator var1 = ((SortTreeRow)this.tree.get(this.tree.size() - 2)).getNodes().iterator();

        while(true) {
            SortTreeNode node;
            do {
                if (!var1.hasNext()) {
                    return;
                }

                node = (SortTreeNode)var1.next();
            } while(node.getElements().size() <= 1);

            if (node != ((SortTreeRow)this.tree.getFirst()).getFirstNode()) {
                node.pinActive();
            }

            SortTreeNode left = new SortTreeNode();
            left.setParent1Node(node);
            left.setParent2Node(node);
            node.setChildLeftNode(left);

            for(int i = 0; i < node.getPinIndex(); ++i) {
                System.out.println("DONE2");
                left.addElement(((Element)node.getElements().get(i)).getValue());
            }

            ((SortTreeRow)this.tree.getLast()).addNode(left);
            ((SortTreeRow)this.tree.getLast()).getRowBox().getChildren().add(left.gethBox());
            SortTreeNode right = new SortTreeNode();
            right.setParent1Node(node);
            right.setParent2Node(node);
            node.setChildRightNode(right);

            for(int i = node.getPinIndex() + 1; i < node.getElements().size(); ++i) {
                System.out.println("DONE3");
                right.addElement(((Element)node.getElements().get(i)).getValue());
            }

            ((SortTreeRow)this.tree.getLast()).addNode(right);
            ((SortTreeRow)this.tree.getLast()).getRowBox().getChildren().add(right.gethBox());
        }
    }
}
