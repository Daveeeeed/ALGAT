package algat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import java.net.URL;
import java.util.ResourceBundle;

public class Lesson2Simulation extends Simulation implements Initializable  {

    private final int DEFAULT_NODE_SPACING = 15;
    private int neededRows;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeS(anchorPane, nextButton);
    }

    @FXML
    public void add() {
        addS();
    }

    @FXML
    public void addThis() {
        addThisS(textField);
    }

    @FXML
    public void remove() {
        removeS();
    }

    @FXML
    public void randomize() {
        randomizeS();
    }

    @FXML
    void start() {
        if (tree.getFirst().getNodes().getFirst().getElements().size() > 0) {
            addButton.setDisable(true);
            addThisButton.setDisable(true);
            textField.setDisable(true);
            removeButton.setDisable(true);
            randomButton.setDisable(true);
            startButton.setDisable(true);
            nextButton.setDisable(false);

            neededRows = calculateRows(tree.getFirst().getFirstNode().getElements().size());
        } else {
            Alert.displayAlert("Impossibile avviare la simulazione", "Aggiungi qualche elemento per continuare.");
        }
    }

    @FXML
    void nextStep() {
        SortTreeRow currentRow = tree.getLast();
        SortTreeRow workingRow = new SortTreeRow();
        tree.addLast(workingRow);
        if (tree.indexOf(currentRow) < neededRows/2){
            divideNodes(currentRow, workingRow);
        } else if (tree.size() <= neededRows){
            mergeNodes(currentRow, workingRow);
        } else {
            nextButton.setDisable(true);
        }
        if (tree.size() > 2) drawLine(tree.get(tree.size() - 3));
    }

    /**
     * Calcola il numero di righe necessarie alla realizzazione dell'albero
     * contenente la risoluzione grafica della simulazione
     *
     * @param arraySize dimensione dell'array di partenza
     * @return numero di righe necessarie
     */
    private int calculateRows(int arraySize){
        double halfRowsDouble = Math.log(arraySize) / Math.log(2);
        int halfRowsInt = (int)halfRowsDouble;
        if (halfRowsDouble % 1 != 0) ++halfRowsInt;
        return (2 * halfRowsInt) + 1;
    }

    /**
     * Calcola l'offset ideale con il quale distanziare i nodi di una SortTreeRow per
     * visualizzarli correttamente
     *
     * @param row SortTreeRow della quale calcolare l'offset ideale
     * @return offset da applicare ai nodi appartenenti a row
     */
    private double nodeOffset(SortTreeRow row){
        return (DEFAULT_NODE_SPACING * neededRows)/Math.pow(2,tree.indexOf(row));
    }

    /**
     * Esegue la divisione dei nodi appertenenti a currentRow inserendoli in workingRow
     *
     * @param currentRow SortTreeRow di provenienza dei nodi
     * @param workingRow SortTreeRow di destinazione dei nodi
     */
    private void divideNodes(SortTreeRow currentRow, SortTreeRow workingRow) {

        for (SortTreeNode node : currentRow.getNodes()) {
            if (node.getElements().size() > 1) {
                int middle = node.getElements().size() / 2;

                SortTreeNode left = new SortTreeNode();
                node.setChildLeftNode(left);

                for (int i = 0; i < middle; ++i) {
                    left.addElement(node.getElements().get(i).getValue());
                }

                left.getHBox().setLayoutX(node.getXof(0) - nodeOffset(workingRow));
                left.getHBox().setLayoutY(node.getHBox().getLayoutY() + 120);
                workingRow.addNode(left);
                anchorPane.getChildren().add(left.getHBox());

                SortTreeNode right = new SortTreeNode();
                node.setChildRightNode(right);

                for (int i = middle; i < node.getElements().size(); ++i) {
                    right.addElement(node.getElements().get(i).getValue());
                }

                right.getHBox().setLayoutX(node.getXof(middle) + 2 + nodeOffset(workingRow));
                right.getHBox().setLayoutY(node.getHBox().getLayoutY() + 120);
                workingRow.addNode(right);
                anchorPane.getChildren().add(right.getHBox());

            } else if (tree.indexOf(currentRow) < neededRows/2) {
                System.out.println("ENTRATOO");
                SortTreeNode child = new SortTreeNode();
                node.setChildLeftNode(child);

                child.addElement(node.getElements().getFirst().getValue());

                child.getHBox().setLayoutX(node.getXof(0));
                child.getHBox().setLayoutY(node.getHBox().getLayoutY() + 120);
                workingRow.addNode(child);
                anchorPane.getChildren().add(child.getHBox());
            }
        }

    }

    /**
     * Esegue l'unione dei nodi appertenenti a currentRow inserendoli in workingRow.
     * Per ogni due nodi di currentRow, viene creato un terzo nodo ordinato inserito in workingRow
     *
     * @param currentRow SortTreeRow di provenienza dei nodi
     * @param workingRow SortTreeRow di destinazione dei nodi
     */
    private void mergeNodes(SortTreeRow currentRow, SortTreeRow workingRow) {
        for (int i = 0; i < currentRow.getNodes().size(); ++i) {
            SortTreeNode merged = new SortTreeNode();
            SortTreeNode first = currentRow.getNode(i);
            if (i + 1 < currentRow.getNodes().size()) {
                SortTreeNode second = currentRow.getNode(i + 1);
                ++i;
                first.setChildRightNode(merged);
                first.setChildLeftNode(merged);
                second.setChildRightNode(merged);
                second.setChildLeftNode(merged);
                mergeElements(first, second, merged);

                merged.getHBox().setLayoutX((first.getXof(0) + second.getXof(0) - 58*first.getElements().size())/2);
                merged.getHBox().setLayoutY(first.getHBox().getLayoutY() + 120);
                workingRow.addNode(merged);
                anchorPane.getChildren().add(merged.getHBox());
            } else {
                first.setChildRightNode(merged);
                first.setChildLeftNode(merged);

                for (Element element : first.getElements()) {
                    merged.addElement(element.getValue());
                }

                merged.getHBox().setLayoutX(first.getXof(0));
                merged.getHBox().setLayoutY(first.getHBox().getLayoutY() + 120);
                workingRow.addNode(merged);
                anchorPane.getChildren().add(merged.getHBox());
            }
        }

    }

    /**
     * Unisce due SortTreeNode riordinandone gli elementi
     *
     * @param left primo nodo sorgente
     * @param right secondo nodo sorgente
     * @param merged nodo destinatario dell'unione
     */
    private void mergeElements(SortTreeNode left, SortTreeNode right, SortTreeNode merged) {
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

    /**
     * Disegna tutte le linee che collegano i nodi di row con i loro figli
     * @param row riga della quale disegnare le linee che collegano i nodi con i figli
     */
    private void drawLine(SortTreeRow row) {
        for (SortTreeNode node : row.getNodes()){
            Line left_line, right_line;
            if (node.getChildLeftNode() != null) {
                left_line = createLine(node, node.getChildLeftNode());
                anchorPane.getChildren().add(left_line);
                left_line.toBack();
            }
            if (node.getChildRightNode() != null && node.getChildRightNode() != node.getChildLeftNode()) {
                right_line = createLine(node, node.getChildRightNode());
                anchorPane.getChildren().add(right_line);
                right_line.toBack();
            }
        }
    }

    /**
     * Crea una line singola che collega i due nodi parent e child
     * @param parent primo nodo
     * @param child secondo nodo
     * @return line che collega i due nodi
     */
    private Line createLine(SortTreeNode parent, SortTreeNode child){
        return new Line(parent.getCenterX(), parent.getHBox().getBoundsInParent().getMaxY(), child.getCenterX(), child.getHBox().getBoundsInParent().getMinY());
    }

}
