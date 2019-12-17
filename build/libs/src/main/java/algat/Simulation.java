package algat;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedList;
import java.util.Random;

public class Simulation {

    protected final int MAX_ARRAY_SIZE = 8;
    protected final int MAX_ELEMENT_VALUE = 100;
    protected LinkedList<SortTreeRow> tree;

    protected void initializeS(AnchorPane anchorPane, Button nextButton) {
        SortTreeNode rootNode = new SortTreeNode();
        SortTreeRow rootRow = new SortTreeRow();

        rootNode.getHBox().setLayoutX(436);
        rootNode.getHBox().setLayoutY(30);
        rootRow.addNode(rootNode);
        tree = new LinkedList<>();
        tree.add(rootRow);
        anchorPane.getChildren().add(rootNode.getHBox());
        nextButton.setDisable(true);
    }

    protected void addS() {
        SortTreeNode rootNode = tree.getFirst().getFirstNode();
        if (rootNode.getElements().size() < MAX_ARRAY_SIZE) {
            Random random = new Random();
            rootNode.getHBox().setLayoutX(rootNode.getHBox().getLayoutX() - 28);
            rootNode.addElement(random.nextInt(MAX_ELEMENT_VALUE));
        } else {
            Alert.displayAlert("Capienza massima dell'array raggiunta", "Avvia la simulazione o rimuovi qualche elemento.");
        }

    }

    protected void addThisS(TextField textField) {
        SortTreeNode rootNode = tree.getFirst().getFirstNode();
        if (rootNode.getElements().size() < MAX_ARRAY_SIZE) {
            try {
                int value = Integer.parseInt(textField.getText());
                if (value >= 0 && value < MAX_ELEMENT_VALUE) {
                    rootNode.getHBox().setLayoutX(rootNode.getHBox().getLayoutX() - 28);
                    rootNode.addElement(value);
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

    protected void removeS() {
        SortTreeNode rootNode = tree.getFirst().getFirstNode();
        if (rootNode.getElements().size() > 0) {
            rootNode.getHBox().setLayoutX(rootNode.getHBox().getLayoutX() + 28);
            rootNode.removeLastElement();
        } else {
            Alert.displayAlert("Impossibile rimuovere l'elemento", "Aggiungi qualche elemento per continuare.");
        }

    }

    protected void randomizeS() {
        while(tree.getFirst().getFirstNode().getElements().size() > 0) {
            this.removeS();
        }

        Random random = new Random();
        int rep = random.nextInt(MAX_ARRAY_SIZE);

        for(int i = 0; i <= rep; ++i) {
            this.addS();
        }

    }
}
