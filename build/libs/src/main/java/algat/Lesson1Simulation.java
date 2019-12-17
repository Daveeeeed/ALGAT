package algat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.net.URL;
import java.util.ResourceBundle;

public class Lesson1Simulation extends Simulation implements Initializable{

    private final int J_INDEX_Y = 125;
    private final int I_INDEX_Y = 95;
    private final int TEXT_Y = 180;
    private Label iPos;
    private Label jPos;
    private Label description;
    private int iVal;
    private int jVal;
    private int pinVal;
    private boolean isDescriptionEmpty;

    @FXML
    protected Button addButton;
    @FXML
    protected Button addThisButton;
    @FXML
    protected TextField textField;
    @FXML
    protected Button removeButton;
    @FXML
    protected Button randomButton;
    @FXML
    protected Button startButton;
    @FXML
    protected Button nextButton;
    @FXML
    protected AnchorPane anchorPane;
    @FXML
    protected Label iIndexValue;
    @FXML
    private Label jIndexValue;
    @FXML
    private Label pinIndexValue;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeS(anchorPane, nextButton);
        iIndexValue.setText("---");
        jIndexValue.setText("---");
        pinIndexValue.setText("---");
        isDescriptionEmpty = true;
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
    public void start() {
        if (tree.getFirst().getNodes().getFirst().getElements().size() > 0) {
            addButton.setDisable(true);
            addThisButton.setDisable(true);
            textField.setDisable(true);
            removeButton.setDisable(true);
            randomButton.setDisable(true);
            startButton.setDisable(true);
            nextButton.setDisable(false);

            tree.getFirst().getFirstNode().pinPassive();

            iVal = 0;
            iPos = new Label("i");
            iPos.setFont(Font.font("Avenir Medium", FontWeight.BOLD, 28.0D));
            iPos.setAlignment(Pos.TOP_CENTER);
            iPos.setLayoutY(I_INDEX_Y);

            jVal = 0;
            jPos = new Label("j");
            jPos.setFont(Font.font("Avenir Medium", FontWeight.BOLD, 28.0D));
            jPos.setAlignment(Pos.TOP_CENTER);
            jPos.setLayoutY(J_INDEX_Y);

            pinVal = 0;
            description = new Label("SIMULAZIONE AVVIATA\nPREMI IL BOTTONE \"PROSSIMO STEP\" PER PROSEGUIRE.\nPER OGNI PASSO, AL PRIMO CLICK SARA' VISUALIZZATO IL CODICE,\nCHE VERRA' ESEGUITO AL SECONDO CLICK");
            description.setFont(Font.font("Avenir Medium", FontWeight.BOLD, 18.0D));
            description.setWrapText(true);
            description.setAlignment(Pos.CENTER);
            description.setLayoutY(TEXT_Y);

            anchorPane.getChildren().addAll(iPos, jPos, description);
            AnchorPane.setLeftAnchor(description, 20.0D);
            AnchorPane.setRightAnchor(description, 20.0D);
            updateIndex();
        } else {
            Alert.displayAlert("Elementi non sufficienti", "Aggiungi qualche elemento per continuare.");
        }

    }

    @FXML
    public void nextStep() {
        if (isFirstRowSolvingEnded()) {
            if (isDescriptionEmpty) {
                description.setText("LA RISOLUZIONE DELLA FUNZIONE PERNO APPLICATA ALL'ARRAY DI PARTENZA E' TERMINATA. AD OGNI PRESSIONE DEL BOTTONE \"RISOLVI SUCCESSIVI\", PER OGNI ELEMENTO DELL'ULTIMA RIGA DELL'ALBERO, VERRA' ORA APPLICATA LA FUNZIONE QUICKSORT SUI DUE SOTTO-ARRAY DIVISI DALL'ELEMENTO PERNO, ESEGUENDO SUCCESSIVAMENTE SU DI ESSI LA FUNZIONE PERNO. QUANDO L'ALGORITMO TERMINA VIENE VISUALIZZATO L'ARRAY ORDINATO.");
                isDescriptionEmpty = false;
                iPos.setText("");
                jPos.setText("");
                iIndexValue.setText("---");
                jIndexValue.setText("---");
                pinIndexValue.setText("---");
            } else {
                boolean isThereMoreToSolve;
                isThereMoreToSolve = false;
                description.setText("");
                for (SortTreeNode node : tree.getLast().getNodes()) {
                    if (node.getElements().size() != 1) isThereMoreToSolve = true;
                    else node.getElements().getFirst().getRectangle().setFill(Color.YELLOW);
                }
                if (isThereMoreToSolve) solveLastRow();
                else completeSimulation();
            }
        } else {
            if (isIAtRowEnd()) {
                if (isDescriptionEmpty) {
                    description.setText("i > A[size] QUINDI:\nA[perno] ↔ A[j],\nperno ← j,\nQuickSort(A,primo,j-1),\nQuickSort(A,j+1,ultimo)");
                    isDescriptionEmpty = false;
                } else {
                    description.setText("");
                    isDescriptionEmpty = true;
                    tree.getFirst().getFirstNode().swapElements(jVal, pinVal);
                    //Pone pinVal = -1 se è terminata l'esecuzione di perno sull'array iniziale
                    pinVal = -1;
                    nextButton.setText("Risolvi Successivi");
                }
            } else {
                if (isILowerThanPin()) {
                    if (isDescriptionEmpty) {
                        description.setText("A[i] < A[perno] QUINDI:\nj ← j+1,\nA[i] ↔ A[j],\ni ← i+1");
                        isDescriptionEmpty = false;
                    } else {
                        description.setText("");
                        isDescriptionEmpty = true;
                        ++jVal;
                        tree.getFirst().getFirstNode().swapElements(this.jVal, this.iVal);
                        ++iVal;
                    }
                } else if (isDescriptionEmpty) {
                    description.setText("A[i] >= A[perno] QUINDI:\ni ← i+1");
                    isDescriptionEmpty = false;
                } else {
                    description.setText("");
                    isDescriptionEmpty = true;
                    ++iVal;
                }
                if (iVal >= tree.getFirst().getNodes().getFirst().getElements().size()) {
                    //Pone iVal = -1 se l'indice i ha raggiunto la fine dell'array
                    iVal = -1;
                }
            }
            updateIndex();
        }
    }

    /**
     * Cambia il colore del Rectangle degli Element dell'array root e
     * aggiorna i testi dei Label basandosi sul valore degli indici i,j e pin.
     */
    private void updateIndex() {
        SortTreeNode firstNode = tree.getFirst().getFirstNode();
        if (isIAtRowEnd()) {
            iIndexValue.setText("OUT OF BOUND");
            iPos.setText("");
        } else {
            iIndexValue.setText(Integer.toString(iVal));
            iPos.setLayoutX(firstNode.getElementCenterX(iVal));
        }

        jIndexValue.setText(Integer.toString(jVal));
        jPos.setLayoutX(firstNode.getElementCenterX(jVal));

        int i;
        for (i = firstNode.getElements().size() - 1; i > jVal; --i) {
            firstNode.getElements().get(i).getRectangle().setFill(Color.LIGHTGRAY);
        }

        for (i = 0; i <= jVal; ++i) {
            firstNode.getElements().get(i).getRectangle().setFill(Color.LIGHTPINK);
        }

        if (isFirstRowSolvingEnded()) {
            firstNode.getElements().get(jVal).getRectangle().setFill(Color.YELLOW);
            pinIndexValue.setText(Integer.toString(jVal));
        } else {
            firstNode.getElements().get(pinVal).getRectangle().setFill(Color.YELLOW);
            pinIndexValue.setText(Integer.toString(pinVal));
        }

    }

    /**
     * Funzione di controllo
     *
     * @return true se il valore del'elemento all'indice i dell'array root è inferiore dell'elemento all'indice pin
     * false altrimenti
     */
    private boolean isILowerThanPin() {
        return tree.getFirst().getNodes().getFirst().getElements().get(iVal).getValue() < tree.getFirst().getNodes().getFirst().getElements().get(pinVal).getValue();
    }

    /**
     * Funzione di controllo
     *
     * @return true se pinVal = -1 ovvero se è terminata la risoluzione della funzione perno sull'array root
     * false altrimenti
     */
    private boolean isFirstRowSolvingEnded() {
        return pinVal == -1;
    }

    /**
     * Funzione di controllo
     *
     * @return true se iVal = -1 ovvero se l'indice i ha raggiunto il termine dell'array root
     * false altrimenti
     */
    private boolean isIAtRowEnd() {
        return iVal == -1;
    }

    /**
     * Per ogni SortTreeNode appartenente all'ultima SortTreeRow di tree, applica prima la funzione perno,
     * calcolando il valore del perno e colorando gli elementi del nodo grazie a pinActive(), successivamente
     * divide il nodo in due sottonodi (left e right) divisi dall'elemento di perno, aggiunge una SortTreeRow
     * in coda a tree, elemento nel quale verranno contenuti i sottonodi realizzati al passo precedente.
     */
    private void solveLastRow() {
        SortTreeRow workingRow = new SortTreeRow();
        tree.addLast(workingRow);
        SortTreeRow previousRow = tree.get(this.tree.size() - 2);

        for (SortTreeNode node : previousRow.getNodes()) {
            if (node != this.tree.getFirst().getFirstNode()) {
                node.pinActive();
            }
            if (node.getPinIndex() != 0) {
                //LEFT
                SortTreeNode left = new SortTreeNode();
                node.setChildLeftNode(left);

                for (int i = 0; i < node.getPinIndex(); ++i) {
                    left.addElement(node.getElements().get(i).getValue());
                }
                left.getHBox().setLayoutX(node.getXof(0));
                left.getHBox().setLayoutY(node.getHBox().getLayoutY() + 90);
                workingRow.addNode(left);
                anchorPane.getChildren().add(left.getHBox());
            } else {
                node.getElements().getFirst().getRectangle().setFill(Color.YELLOW);
            }

            if (node.getPinIndex() != node.getElements().size()-1){
                //RIGHT
                SortTreeNode right = new SortTreeNode();
                node.setChildRightNode(right);

                for(int i = node.getPinIndex() + 1; i < node.getElements().size(); ++i) {
                    right.addElement(node.getElements().get(i).getValue());
                }

                right.getHBox().setLayoutX(2 + node.getXof(node.getPinIndex() + 1));
                right.getHBox().setLayoutY(node.getHBox().getLayoutY() + 90);
                workingRow.addNode(right);
                anchorPane.getChildren().add(right.getHBox());
            }

        }
    }

    /**
     * Termina la simulazione aggiungendo un ulteriore SortTreeRow in coda a tree, la quale contiene l'array
     * ordinato (solution), realizzato con la funzione addSortedElement() applicata ad ogni Element dell'array root.
     */
    private void completeSimulation() {
        nextButton.setDisable(true);
        SortTreeRow lastRow = new SortTreeRow();
        tree.addLast(lastRow);
        SortTreeNode solution = new SortTreeNode();

        for (Element element : tree.getFirst().getFirstNode().getElements()) {
            solution.addSortedElement(element.getValue());
        }

        for (Element element : solution.getElements()) {
            solution.getHBox().getChildren().add(element.getStackPane());
        }

        solution.getHBox().setLayoutX(tree.getFirst().getFirstNode().getXof(0));
        solution.getHBox().setLayoutY(tree.get(tree.size() - 2).getFirstNode().getHBox().getLayoutY() + 90);
        tree.getLast().addNode(solution);
        anchorPane.getChildren().add(solution.getHBox());
    }

}
