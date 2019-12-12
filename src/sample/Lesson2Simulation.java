package sample;

import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

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
        alert.getButtonTypes().setAll(new ButtonType[]{button});
        alert.setGraphic(imageView);
        alert.showAndWait();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tree = new LinkedList();
        this.tree.add(new SortTreeRow());
        this.vBox.getChildren().add(((SortTreeRow)this.tree.getFirst()).getRowBox());
        ((SortTreeRow)this.tree.getFirst()).addNode(new SortTreeNode());
        ((SortTreeRow)this.tree.getFirst()).getRowBox().getChildren().add(((SortTreeRow)this.tree.getFirst()).getFirstNode().gethBox());
        this.row_to_draw = 0;
        this.totalRows = 0;
        this.nextButton.setDisable(true);
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
        if (((SortTreeRow)this.tree.getFirst()).getFirstNode().getElements().size() < 2) {
            this.displayAlert("Impossibile avviare la simulazione", "Aggiungi qualche elemento per continuare.");
        } else {
            this.addButton.setDisable(true);
            this.addThisButton.setDisable(true);
            this.textField.setDisable(true);
            this.removeButton.setDisable(true);
            this.randomButton.setDisable(true);
            this.startButton.setDisable(true);
            this.nextButton.setDisable(false);
            double halfRowsDouble = Math.log((double)((SortTreeRow)this.tree.getFirst()).getFirstNode().getElements().size()) / Math.log(2.0D);
            this.totalRows = (int)halfRowsDouble;
            if (halfRowsDouble % 1.0D != 0.0D) {
                ++this.totalRows;
            }

            this.totalRows = 2 * this.totalRows + 1;

            int i;
            for(i = 1; i < this.totalRows; ++i) {
                this.tree.addLast(new SortTreeRow());
                this.vBox.getChildren().add(((SortTreeRow)this.tree.getLast()).getRowBox());
            }

            for(i = 0; i < this.totalRows / 2; ++i) {
                this.divide(i);
            }

            for(i = this.totalRows / 2; i < this.totalRows - 1; ++i) {
                this.merge(i);
            }
        }

    }

    private void divide(int rowIndex) {
        Iterator var2 = ((SortTreeRow)this.tree.get(rowIndex)).getNodes().iterator();

        while(true) {
            while(var2.hasNext()) {
                SortTreeNode node = (SortTreeNode)var2.next();
                if (node.getElements().size() > 1) {
                    int middle = node.getElements().size() / 2;
                    SortTreeNode left = new SortTreeNode();
                    left.setParent1Node(node);
                    left.setParent2Node(node);
                    node.setChildLeftNode(left);

                    for(int i = 0; i < middle; ++i) {
                        left.addElement(((Element)node.getElements().get(i)).getValue());
                    }

                    ((SortTreeRow)this.tree.get(rowIndex + 1)).addNode(left);
                    SortTreeNode right = new SortTreeNode();
                    right.setParent1Node(node);
                    right.setParent2Node(node);
                    node.setChildRightNode(right);

                    for(int i = middle; i < node.getElements().size(); ++i) {
                        right.addElement(((Element)node.getElements().get(i)).getValue());
                    }

                    ((SortTreeRow)this.tree.get(rowIndex + 1)).addNode(right);
                } else if (rowIndex == this.tree.size() / 2 - 1) {
                    SortTreeNode child = new SortTreeNode();
                    child.setParent1Node(node);
                    child.setParent2Node(node);
                    node.setChildLeftNode(child);

                    for(int i = 0; i < node.getElements().size(); ++i) {
                        child.addElement(((Element)node.getElements().get(i)).getValue());
                    }

                    ((SortTreeRow)this.tree.get(rowIndex + 1)).addNode(child);
                }
            }

            return;
        }
    }

    private void merge(int rowIndex) {
        for(int i = 0; i < ((SortTreeRow)this.tree.get(rowIndex)).getNodes().size(); ++i) {
            SortTreeNode merged = new SortTreeNode();
            if (i + 1 < ((SortTreeRow)this.tree.get(rowIndex)).getNodes().size()) {
                merged.setParent1Node(((SortTreeRow)this.tree.get(rowIndex)).getNode(i));
                merged.setParent2Node(((SortTreeRow)this.tree.get(rowIndex)).getNode(i + 1));
                ((SortTreeRow)this.tree.get(rowIndex)).getNode(i).setChildRightNode(merged);
                ((SortTreeRow)this.tree.get(rowIndex)).getNode(i).setChildLeftNode(merged);
                ((SortTreeRow)this.tree.get(rowIndex)).getNode(i + 1).setChildRightNode(merged);
                ((SortTreeRow)this.tree.get(rowIndex)).getNode(i + 1).setChildLeftNode(merged);
                this.merge(((SortTreeRow)this.tree.get(rowIndex)).getNode(i), ((SortTreeRow)this.tree.get(rowIndex)).getNode(i + 1), merged);
                ++i;
            } else {
                merged.setParent1Node(((SortTreeRow)this.tree.get(rowIndex)).getNode(i));
                merged.setParent2Node(((SortTreeRow)this.tree.get(rowIndex)).getNode(i));
                ((SortTreeRow)this.tree.get(rowIndex)).getNode(i).setChildRightNode(merged);
                ((SortTreeRow)this.tree.get(rowIndex)).getNode(i).setChildLeftNode(merged);

                for(int k = 0; k < ((SortTreeRow)this.tree.get(rowIndex)).getNode(i).getElements().size(); ++k) {
                    merged.addElement(((Element)((SortTreeRow)this.tree.get(rowIndex)).getNode(i).getElements().get(k)).getValue());
                }
            }

            ((SortTreeRow)this.tree.get(rowIndex + 1)).addNode(merged);
        }

    }

    @FXML
    void nextStep() {
        int i;
        for(i = 0; ((SortTreeRow)this.tree.get(i)).getRowBox().getChildren().size() != 0; ++i) {
        }

        Iterator var2 = ((SortTreeRow)this.tree.get(i - 1)).getNodes().iterator();

        SortTreeNode node;
        while(var2.hasNext()) {
            node = (SortTreeNode)var2.next();
            this.drawLine(node);
        }

        var2 = ((SortTreeRow)this.tree.get(i)).getNodes().iterator();

        while(var2.hasNext()) {
            node = (SortTreeNode)var2.next();
            ((SortTreeRow)this.tree.get(i)).getRowBox().getChildren().add(node.gethBox());
        }

        if (i + 1 >= this.tree.size()) {
            this.nextButton.setDisable(true);
        }

    }

    private void merge(SortTreeNode left, SortTreeNode right, SortTreeNode merged) {
        int left_index = 0;
        int right_index = 0;

        while(left_index < left.getElements().size() && right_index < right.getElements().size()) {
            if (left_index < left.getElements().size() && right_index < right.getElements().size()) {
                if (((Element)left.getElements().get(left_index)).getValue() < ((Element)right.getElements().get(right_index)).getValue()) {
                    merged.addElement(((Element)left.getElements().get(left_index)).getValue());
                    ++left_index;
                } else {
                    merged.addElement(((Element)right.getElements().get(right_index)).getValue());
                    ++right_index;
                }
            }

            if (left_index >= left.getElements().size()) {
                while(right_index < right.getElements().size()) {
                    merged.addElement(((Element)right.getElements().get(right_index)).getValue());
                    ++right_index;
                }
            }

            if (right_index >= right.getElements().size()) {
                while(left_index < left.getElements().size()) {
                    merged.addElement(((Element)left.getElements().get(left_index)).getValue());
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
                if (right_depth == this.row_to_draw) {
                    right_line = new Line(node.getCenterX(), node.getCenterY() + 30.0D, node.getChildLeftNode().getCenterX(), node.getChildLeftNode().getCenterY() - 30.0D);
                    this.anchorPane.getChildren().add(right_line);
                    right_line.toBack();
                } else if (right_depth < this.row_to_draw) {
                    this.drawLine(node.getChildLeftNode());
                }
            }

            if (node.getChildRightNode() != null) {
                right_depth = node.getNodeDepth(node.getChildRightNode());
                if (right_depth == this.row_to_draw) {
                    right_line = new Line(node.getCenterX(), node.getCenterY() + 30.0D, node.getChildRightNode().getCenterX(), node.getChildRightNode().getCenterY() - 30.0D);
                    this.anchorPane.getChildren().add(right_line);
                    right_line.toBack();
                } else if (right_depth < this.row_to_draw) {
                    this.drawLine(node.getChildRightNode());
                }
            }
        }

    }
}
