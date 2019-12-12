package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;

public class Lesson2Simulation implements Initializable {

    final int MAX_ARRAY_SIZE = 8;
    final int MAX_ELEMENT_VALUE = 100;

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
    private AnchorPane anchorPane;
    @FXML
    private VBox vBox;

    private LinkedList<SortTreeRow> tree;
    private int row_to_draw;
    private int totalRows;

    public Lesson2Simulation() {
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
        alert.getButtonTypes().setAll(button);
        alert.setGraphic(imageView);
        alert.showAndWait();

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        tree = new LinkedList<>();
        tree.add(new SortTreeRow());
        vBox.getChildren().add(tree.getFirst().getRowBox());
        tree.getFirst().addNode(new SortTreeNode());
        tree.getFirst().getRowBox().getChildren().add(tree.getFirst().getFirstNode().gethBox());
        row_to_draw = 0;
        totalRows = 0;
        nextButton.setDisable(true);

    }

    @FXML
    void add() {
        if (tree.getFirst().getFirstNode().getElements().size() < MAX_ARRAY_SIZE) {
            Random random = new Random();
            tree.getFirst().getFirstNode().addElement(random.nextInt(MAX_ELEMENT_VALUE));
        } else {
            displayAlert("Capienza massima dell'array raggiunta", "Avvia la simulazione o rimuovi qualche elemento.");
        }

    }

    @FXML
    void addThis() {
        if (tree.getFirst().getFirstNode().getElements().size() < MAX_ARRAY_SIZE) {
            try {
                int value = Integer.parseInt(textField.getText());
                if (value >= 0 && value < MAX_ELEMENT_VALUE) {
                    tree.getFirst().getFirstNode().addElement(value);
                } else {
                    displayAlert("Formato numero errato", "Inserisci un numero compreso tra 0 e 99.");
                }
            } catch (Exception var2) {
                displayAlert("Formato numero errato", "Inserisci un numero nel formato valido.");
            }
        } else {
            displayAlert("Capienza massima dell'array raggiunta", "Avvia la simulazione o rimuovi qualche elemento.");
        }

        textField.clear();
    }

    @FXML
    void remove() {
        if (tree.getFirst().getFirstNode().getElements().size() > 0) {
            tree.getFirst().getFirstNode().removeLastElement();
        } else {
            displayAlert("Impossibile rimuovere l'elemento", "Aggiungi qualche elemento per continuare.");
        }

    }

    @FXML
    void randomize() {
        while (tree.getFirst().getFirstNode().getElements().size() > 0) {
            remove();
        }

        Random random = new Random();
        int rep = random.nextInt(MAX_ARRAY_SIZE);

        for (int i = 0; i <= rep; ++i) {
            add();
        }

    }

    @FXML
    void start() {
        if (tree.getFirst().getFirstNode().getElements().size() < 2) {
            displayAlert("Impossibile avviare la simulazione", "Aggiungi qualche elemento per continuare.");
        } else {
            addButton.setDisable(true);
            addThisButton.setDisable(true);
            textField.setDisable(true);
            removeButton.setDisable(true);
            randomButton.setDisable(true);
            startButton.setDisable(true);
            nextButton.setDisable(false);

            //Calculation rows needed for the graph
            double halfRowsDouble = Math.log(tree.getFirst().getFirstNode().getElements().size()) / Math.log(2);
            totalRows = (int) halfRowsDouble;
            if (halfRowsDouble % 1 != 0) {
                ++totalRows;
            }
            totalRows = 2 * totalRows + 1;

            int i;
            //Adding rows to tree and vBox
            for (i = 1; i < totalRows; ++i) {
                tree.addLast(new SortTreeRow());
                vBox.getChildren().add(tree.getLast().getRowBox());
            }

            for (i = 0; i < totalRows / 2; ++i) {
                divide(i);
            }

            for (i = totalRows / 2; i < totalRows - 1; ++i) {
                merge(i);
            }
        }

    }

    private void divide(int rowIndex) {

        for (SortTreeNode node : tree.get(rowIndex).getNodes()) {
            if (node.getElements().size() > 1) {
                int middle = node.getElements().size() / 2;

                SortTreeNode left = new SortTreeNode();
                left.setParent1Node(node);
                left.setParent2Node(node);
                node.setChildLeftNode(left);

                for (int i = 0; i < middle; ++i) {
                    left.addElement(node.getElements().get(i).getValue());
                }

                tree.get(rowIndex + 1).addNode(left);

                SortTreeNode right = new SortTreeNode();
                right.setParent1Node(node);
                right.setParent2Node(node);
                node.setChildRightNode(right);

                for (int i = middle; i < node.getElements().size(); ++i) {
                    right.addElement(node.getElements().get(i).getValue());
                }

                tree.get(rowIndex + 1).addNode(right);

                //if node size == 1 and the row isn't the last one, the node is cloned into the row below
            } else if (rowIndex == tree.size() / 2 - 1) {
                SortTreeNode child = new SortTreeNode();
                child.setParent1Node(node);
                child.setParent2Node(node);
                node.setChildLeftNode(child);

                for (int i = 0; i < node.getElements().size(); ++i) {
                    child.addElement(node.getElements().get(i).getValue());
                }

                tree.get(rowIndex + 1).addNode(child);
            }
        }

    }

    private void merge(int rowIndex) {
        for (int i = 0; i < tree.get(rowIndex).getNodes().size(); ++i) {
            SortTreeNode merged = new SortTreeNode();
            if (i + 1 < tree.get(rowIndex).getNodes().size()) {
                merged.setParent1Node(tree.get(rowIndex).getNode(i));
                merged.setParent2Node(tree.get(rowIndex).getNode(i + 1));
                tree.get(rowIndex).getNode(i).setChildRightNode(merged);
                tree.get(rowIndex).getNode(i).setChildLeftNode(merged);
                tree.get(rowIndex).getNode(i + 1).setChildRightNode(merged);
                tree.get(rowIndex).getNode(i + 1).setChildLeftNode(merged);
                merge(tree.get(rowIndex).getNode(i), tree.get(rowIndex).getNode(i + 1), merged);
                ++i;
            } else {
                merged.setParent1Node(tree.get(rowIndex).getNode(i));
                merged.setParent2Node(tree.get(rowIndex).getNode(i));
                tree.get(rowIndex).getNode(i).setChildRightNode(merged);
                tree.get(rowIndex).getNode(i).setChildLeftNode(merged);

                for (int k = 0; k < tree.get(rowIndex).getNode(i).getElements().size(); ++k) {
                    merged.addElement(tree.get(rowIndex).getNode(i).getElements().get(k).getValue());
                }
            }

            tree.get(rowIndex + 1).addNode(merged);
        }

    }

    @FXML
    void nextStep() {
        int i;
        i = 0;
        while (tree.get(i).getRowBox().getChildren().size() != 0) {
            ++i;
        }

        for (SortTreeNode node : tree.get(i - 1).getNodes()) {
            drawLine(node);
        }

        for (SortTreeNode node : tree.get(i).getNodes()) {
            tree.get(i).getRowBox().getChildren().add(node.gethBox());
        }

        if (i + 1 >= tree.size()) {
            nextButton.setDisable(true);
        }

    }

    private void merge(SortTreeNode left, SortTreeNode right, SortTreeNode merged) {
        int left_index = 0;
        int right_index = 0;

        while (left_index < left.getElements().size() && right_index < right.getElements().size()) {
            if (left_index < left.getElements().size() && right_index < right.getElements().size()) {
                if (left.getElements().get(left_index).getValue() < right.getElements().get(right_index).getValue()) {
                    merged.addElement(left.getElements().get(left_index).getValue());
                    ++left_index;
                } else {
                    merged.addElement(right.getElements().get(right_index).getValue());
                    ++right_index;
                }
            }

            if (left_index >= left.getElements().size()) {
                while (right_index < right.getElements().size()) {
                    merged.addElement(right.getElements().get(right_index).getValue());
                    ++right_index;
                }
            }

            if (right_index >= right.getElements().size()) {
                while (left_index < left.getElements().size()) {
                    merged.addElement(left.getElements().get(left_index).getValue());
                    ++left_index;
                }
            }
        }

    }

    private void drawLine(SortTreeNode node) {
        if (node != null) {
            int right_depth;
            Line right_line;
            if (node.getChildLeftNode() != null) {
                right_depth = node.getNodeDepth(node.getChildLeftNode());
                if (right_depth == row_to_draw) {
                    right_line = new Line(node.getCenterX(), node.getCenterY() + 30, node.getChildLeftNode().getCenterX(), node.getChildLeftNode().getCenterY() - 30);
                    anchorPane.getChildren().add(right_line);
                    right_line.toBack();
                } else if (right_depth < row_to_draw) {
                    drawLine(node.getChildLeftNode());
                }
            }

            if (node.getChildRightNode() != null) {
                right_depth = node.getNodeDepth(node.getChildRightNode());
                if (right_depth == row_to_draw) {
                    right_line = new Line(node.getCenterX(), node.getCenterY() + 30, node.getChildRightNode().getCenterX(), node.getChildRightNode().getCenterY() - 30);
                    anchorPane.getChildren().add(right_line);
                    right_line.toBack();
                } else if (right_depth < row_to_draw) {
                    drawLine(node.getChildRightNode());
                }
            }
        }

    }
}
