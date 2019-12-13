package sample;

import java.net.URL;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Lesson1Simulation implements Initializable {

    final int J_INDEX_Y = 125;
    final int I_INDEX_Y = 95;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SortTreeNode root = new SortTreeNode();
        tree = new LinkedList<>();
        tree.add(new SortTreeRow());
        tree.getFirst().addNode(root);
        anchorPane.getChildren().add(tree.getFirst().getFirstNode().gethBox());
        root.gethBox().setLayoutX(436);
        root.gethBox().setLayoutY(30);
        i_index_value.setText("---");
        j_index_value.setText("---");
        pin_index_value.setText("---");
        nextButton.setDisable(true);
        isDescriptionEmpty = true;
    }

    @FXML
    public void add() {
        SortTreeNode root = tree.getFirst().getFirstNode();
        if (root.getElements().size() < MAX_ARRAY_SIZE) {
            Random random = new Random();
            root.gethBox().setLayoutX(root.gethBox().getLayoutX() - 28);
            root.addElement(random.nextInt(MAX_ELEMENT_VALUE));
        } else {
            Alert.displayAlert("Capienza massima dell'array raggiunta", "Avvia la simulazione o rimuovi qualche elemento.");
        }

    }

    @FXML
    public void addThis() {
        SortTreeNode root = tree.getFirst().getFirstNode();
        if (root.getElements().size() < MAX_ARRAY_SIZE) {
            try {
                int value = Integer.parseInt(textField.getText());
                if (value >= 0 && value < MAX_ELEMENT_VALUE) {
                    root.gethBox().setLayoutX(root.gethBox().getLayoutX() - 28);
                    root.addElement(value);
                } else {
                    Alert.displayAlert("Formato numero errato", "Inserisci un numero compreso tra 0 e 99.");
                }
            } catch (Exception var2) {
                Alert.displayAlert("Formato numero errato", "Inserisci un numero nel formato valido.");
            }
        } else {
            Alert.displayAlert("Capienza massima dell'array raggiunta", "Avvia la simulazione o rimuovi qualche elemento.");
        }
        textField.clear();
    }

    @FXML
    public void remove() {
        SortTreeNode root = tree.getFirst().getFirstNode();
        if (root.getElements().size() > 0) {
            root.gethBox().setLayoutX(root.gethBox().getLayoutX() + 28);
            root.removeLastElement();
        } else {
            Alert.displayAlert("Impossibile rimuovere l'elemento", "Aggiungi qualche elemento per continuare.");
        }

    }

    @FXML
    public void randomize() {
        while(tree.getFirst().getFirstNode().getElements().size() > 0) {
            this.remove();
        }

        Random random = new Random();
        int rep = random.nextInt(MAX_ARRAY_SIZE);

        for(int i = 0; i <= rep; ++i) {
            this.add();
        }

    }

    @FXML
    public void start() {
        if (tree.getFirst().getNodes().getFirst().getElements().size() > 0) {
            addButton.setDisable(true);
            addThisButton.setDisable(true);
            textField.setDisable(true);
            removeButton.setDisable(true);
            randomButton.setDisable(true);
            startButton.setDisable(true);
            nextButton.setDisable(false);
            i_val = 0;
            j_val = 0;
            pin_val = 0;
            tree.getFirst().getFirstNode().pinPassive();
            i_pos = new Label("i");
            i_pos.setFont(Font.font("Avenir Medium", FontWeight.BOLD, 28.0D));
            i_pos.setAlignment(Pos.TOP_CENTER);
            i_pos.setLayoutY(I_INDEX_Y);
            j_pos = new Label("j");
            j_pos.setFont(Font.font("Avenir Medium", FontWeight.BOLD, 28.0D));
            j_pos.setAlignment(Pos.TOP_CENTER);
            j_pos.setLayoutY(J_INDEX_Y);
            description = new Label("SIMULAZIONE AVVIATA\nPREMI IL BOTTONE \"PROSSIMO STEP\" PER PROSEGUIRE\nPER OGNI PASSO AL PRIMO CLICK SARA' VISUALIZZATO IL CODICE,\nCHE VERRA' ESEGUITO AL SECONDO CLICK");
            description.setFont(Font.font("Avenir Medium", FontWeight.BOLD, 18.0D));
            description.setAlignment(Pos.CENTER);
            description.setLayoutY(TEXT_Y);
            anchorPane.getChildren().addAll(i_pos, j_pos, description);
            AnchorPane.setLeftAnchor(description, 0.0D);
            AnchorPane.setRightAnchor(description, 0.0D);
            updateIndex();
        } else {
            Alert.displayAlert("Elementi non sufficienti", "Aggiungi qualche elemento per continuare.");
        }

    }

    private boolean isILowerThanPin() {
        return tree.getFirst().getNodes().getFirst().getElements().get(i_val).getValue() < tree.getFirst().getNodes().getFirst().getElements().get(pin_val).getValue();
    }

    @FXML
    public void nextStep() {
        //Se pin_val != -1 la prima fase non è finita
        if (pin_val != -1) {
            //Se i_val! = -1 l'indice i non ha raggiunto la fine dell'array
            if (i_val != -1) {
                if (isILowerThanPin()) {
                    if (isDescriptionEmpty) {
                        description.setText("A[i] < A[perno] QUINDI:\nj ← j+1,\nA[i] ↔ A[j],\ni ← i+1");
                        isDescriptionEmpty = false;
                    } else {
                        description.setText("");
                        isDescriptionEmpty = true;
                        ++j_val;
                        tree.getFirst().getFirstNode().swapElements(this.j_val, this.i_val);
                        ++i_val;
                    }
                } else if (isDescriptionEmpty) {
                    description.setText("A[i] >= A[perno] QUINDI:\ni ← i+1");
                    isDescriptionEmpty = false;
                } else {
                    description.setText("");
                    isDescriptionEmpty = true;
                    ++i_val;
                }
                //Pone i_val = -1 se l'indice i ha raggiunto la fine dell'array
                if (i_val >= tree.getFirst().getNodes().getFirst().getElements().size()) {
                    i_val = -1;
                }
            } else if (isDescriptionEmpty) {
                description.setText("i > A[size] QUINDI:\nA[perno] ↔ A[j],\nperno ← j,\nQuickSort(A,primo,j-1),\nQuickSort(A,j+1,ultimo)");
                isDescriptionEmpty = false;
            } else {
                description.setText("");
                isDescriptionEmpty = true;
                tree.getFirst().getFirstNode().swapElements(j_val, pin_val);
                pin_val = -1;
                nextButton.setText("Risolvi Successivi");
            }
            updateIndex();
        } else {
            description.setText("");
            i_pos.setText("");
            j_pos.setText("");
            i_index_value.setText("---");
            j_index_value.setText("---");
            pin_index_value.setText("---");
            boolean thereIsMoreToSolve = false;
            for (SortTreeNode node : tree.getLast().getNodes()){
                thereIsMoreToSolve = thereIsMoreToSolve || (node.getElements().size() != 1);
            }
            if (thereIsMoreToSolve) solveLastRow();
            else completeSimulation();
        }

    }

    private void updateIndex() {
        if (this.i_val == -1) {
            this.i_index_value.setText(Integer.toString(this.tree.getFirst().getNodes().getFirst().getElements().size() - 1));
        } else {
            this.i_index_value.setText(Integer.toString(this.i_val));
            this.i_pos.setLayoutX(this.tree.getFirst().getNodes().getFirst().getElementX(this.i_val));
        }

        this.j_index_value.setText(Integer.toString(this.j_val));
        this.j_pos.setLayoutX(this.tree.getFirst().getNodes().getFirst().getElementX(this.j_val));

        int i;
        for(i = this.tree.getFirst().getNodes().getFirst().getElements().size() - 1; i > this.j_val; --i) {
            this.tree.getFirst().getNodes().getFirst().getElements().get(i).getRectangle().setFill(Color.LIGHTGRAY);
        }

        for(i = 0; i <= this.j_val; ++i) {
            this.tree.getFirst().getNodes().getFirst().getElements().get(i).getRectangle().setFill(Color.LIGHTPINK);
        }

        if (this.pin_val == -1) {
            this.tree.getFirst().getNodes().getFirst().getElements().get(this.j_val).getRectangle().setFill(Color.YELLOW);
            this.pin_index_value.setText(Integer.toString(this.j_val));
        } else {
            this.tree.getFirst().getNodes().getFirst().getElements().get(this.pin_val).getRectangle().setFill(Color.YELLOW);
            this.pin_index_value.setText(Integer.toString(this.pin_val));
        }

    }

    private void completeSimulation(){
        System.out.println("ENTRATO IN COMPLETE");
        nextButton.setDisable(true);
        SortTreeRow lastRow = new SortTreeRow();
        tree.addLast(lastRow);
        SortTreeNode solution = new SortTreeNode();

        for (Element element : tree.getFirst().getFirstNode().getElements()){
            solution.addSortedElement(element.getValue());
            System.out.println("FATTO SORTED");
        }

        for (Element element : solution.getElements()){
            solution.gethBox().getChildren().add(element.getStackPane());
            System.out.println("FATTO AGGIUNTO");
        }

        solution.gethBox().setLayoutX(tree.getFirst().getFirstNode().getXof(0));
        solution.gethBox().setLayoutY(tree.get(tree.size()-2).getFirstNode().gethBox().getLayoutY() + 90);
        tree.getLast().addNode(solution);
        anchorPane.getChildren().add(solution.gethBox());
    }

    private void solveLastRow() {
        SortTreeRow workingRow = new SortTreeRow();
        tree.addLast(workingRow);
        SortTreeRow previousRow = tree.get(this.tree.size() - 2);

        for (SortTreeNode node : previousRow.getNodes()){
            if (node != this.tree.getFirst().getFirstNode()) {
                node.pinActive();
            }
            if (node.getPinIndex() != 0){
                //LEFT
                SortTreeNode left = new SortTreeNode();
                left.setParent1Node(node);
                left.setParent2Node(node);
                node.setChildLeftNode(left);

                for(int i = 0; i < node.getPinIndex(); ++i) {
                    left.addElement(node.getElements().get(i).getValue());
                }
                left.gethBox().setLayoutX(node.getXof(0));
                left.gethBox().setLayoutY(node.gethBox().getLayoutY() + 90);
                workingRow.addNode(left);
                anchorPane.getChildren().add(left.gethBox());
            }

            if (node.getPinIndex() != node.getElements().size()-1){
                //RIGHT
                SortTreeNode right = new SortTreeNode();
                right.setParent1Node(node);
                right.setParent2Node(node);
                node.setChildRightNode(right);

                for(int i = node.getPinIndex() + 1; i < node.getElements().size(); ++i) {
                    right.addElement(node.getElements().get(i).getValue());
                }

                right.gethBox().setLayoutX(2 + node.getXof(node.getPinIndex() + 1));
                right.gethBox().setLayoutY(node.gethBox().getLayoutY() + 90);
                workingRow.addNode(right);
                anchorPane.getChildren().add(right.gethBox());
            }

        }
    }
}
