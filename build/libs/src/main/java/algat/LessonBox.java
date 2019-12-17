package algat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LessonBox implements Initializable {

    @FXML
    private AnchorPane anchorPaneRoot;
    @FXML
    private Label lessonTitle;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label lessonText;
    @FXML
    private ImageView imageView;
    @FXML
    private RadioButton one;
    @FXML
    private RadioButton two;
    @FXML
    private RadioButton three;
    @FXML
    private RadioButton four;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button simulationButton;
    @FXML
    private Button confirmButton;

    private ToggleGroup toggleGroup;
    private RadioButton[] radioButtons;
    private Lesson[] lessonsLists;
    private int currentPageType;
    private int currentLessonNumber;
    private int currentPageNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.lessonsLists = new Lesson[3];
        this.lessonsLists[0] = new Lesson(new int[]{1, 1, 1, 1, 2, 1, 1, 4, 4, 4, 4, 4});
        this.lessonsLists[1] = new Lesson(new int[]{1, 1, 2, 2, 3, 4, 4, 4, 4, 4});
        this.lessonsLists[2] = new Lesson(new int[]{1, 2, 2, 3, 4, 4, 4, 4, 4});
        this.toggleGroup = new ToggleGroup();
        this.one.setToggleGroup(this.toggleGroup);
        this.two.setToggleGroup(this.toggleGroup);
        this.three.setToggleGroup(this.toggleGroup);
        this.four.setToggleGroup(this.toggleGroup);
        this.radioButtons = new RadioButton[]{this.one, this.two, this.three, this.four, new RadioButton()};
    }

    @FXML
    public void goToLastPage() throws IOException {
        this.loadLesson(this.currentLessonNumber, this.currentPageNumber - 1);
    }

    @FXML
    public void goToNextPage() throws IOException {
        this.loadLesson(this.currentLessonNumber, this.currentPageNumber + 1);
    }

    @FXML
    public void startSimulation() throws IOException {
        if (this.simulationButton.getText().equals("Avvia simulazione")) {
            this.simulationButton.setText("Termina simulazione");
            this.prevButton.setDisable(true);
            this.nextButton.setDisable(true);
            this.lessonText.setVisible(false);
            this.lessonTitle.setText("Simulazione interattiva");
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/layout/Lesson" + this.currentLessonNumber + "Simulation.fxml"));
            Parent root = loader.load();
            SubScene subScene1 = new SubScene(root, 878.0, 469.0);
            this.anchorPane.getChildren().add(subScene1);
            AnchorPane.setRightAnchor(subScene1, 0.0);
            AnchorPane.setLeftAnchor(subScene1, 0.0);
            AnchorPane.setTopAnchor(subScene1, 0.0);
            AnchorPane.setBottomAnchor(subScene1, 0.0);
        } else {
            this.simulationButton.setText("Avvia simulazione");
            this.prevButton.setDisable(false);
            this.nextButton.setDisable(false);
            this.lessonText.setVisible(true);
            this.anchorPane.getChildren().remove(this.anchorPane.getChildren().size() - 1);
            this.loadLesson(currentLessonNumber, currentPageNumber);
        }

    }

    @FXML
    public void checkCorrectAnswer() throws IOException {
        //Risposta non selezionata
        if (toggleGroup.getSelectedToggle() == null) {
            algat.Alert.displayAlert("Nessuna risposta selezionata", "Seleziona una risposta per continuare.");
            //Risposta esatta
        } else if (toggleGroup.getSelectedToggle() == radioButtons[4]) {
            setSelectedButton();
            lessonsLists[currentLessonNumber].getLesson().get(currentPageNumber - 1).setAlreadyAnswered(true);
            algat.Alert.displayAlert("Hai risposto alla domanda", "Risposta corretta!");
            //Non ultima pagina
            if (currentPageNumber < lessonsLists[currentLessonNumber].getLesson().size()) {
                loadLesson(currentLessonNumber, currentPageNumber + 1);
            //Ultima pagina
            } else {
                closeLesson();
            }
            //Risposta sbagliata
        } else {
            algat.Alert.displayAlert("Hai risposto alla domanda", "Risposta sbagliata.");
            closeLesson();
        }

    }

    /**
     * Imposta l'impaginazione della pagina (destinationPageNr) della lezione (lessonNumber)
     *
     * @param lessonNumber      indice della lezione da caricare
     * @param destinationPageNr indice della pagine della lezione da caricare
     * @throws IOException      necessario per setLessonContent()
     */
    public void loadLesson(int lessonNumber, int destinationPageNr) throws IOException {
        this.currentLessonNumber = lessonNumber;
        this.currentPageNumber = destinationPageNr;
        this.currentPageType = this.lessonsLists[lessonNumber].getLesson().get(destinationPageNr - 1).getType();
        this.setLessonButtons();
        this.setLessonContent();
    }

    /**
     * Chiude la Tab della lezione corrente riportando alla Home.
     * Visualizza un Alert indicando quante risposte sono state date correttamente.
     */
    private void closeLesson(){
        TabPane tabPane = (TabPane)this.anchorPaneRoot.getParent().getParent();
        int correct = 0;
        int total = 0;
        for (LessonPage page : lessonsLists[currentLessonNumber].getLesson()){
            if (page.getType() == 4){
                ++total;
                if (page.isAlreadyAnswered()) ++correct;
            }
        }
        algat.Alert.displayAlert("Lezione terminata", "Hai risposto correttamente a " + correct + " domande su " + total);
        tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
        tabPane.getSelectionModel().selectFirst();
        if (correct == total) {
            ((CheckBox)((VBox) tabPane.getTabs().get(0).getContent()).getChildren().get(3)).setSelected(true);
            ((CheckBox)((VBox) tabPane.getTabs().get(0).getContent()).getChildren().get(3)).setText("Lezione completata");
            ((CheckBox)((VBox) tabPane.getTabs().get(0).getContent()).getChildren().get(8)).setSelected(true);
            ((CheckBox)((VBox) tabPane.getTabs().get(0).getContent()).getChildren().get(8)).setText(Integer.toString(this.currentLessonNumber));
        }
    }

    /**
     * Imposta i bottoni nella toolbar della lezione in base al tipo di pagina
     */
    private void setLessonButtons() {
        if (this.currentPageType == 4 && !this.lessonsLists[this.currentLessonNumber].getLesson().get(this.currentPageNumber - 1).isAlreadyAnswered()) {
            this.confirmButton.setDisable(false);
        } else {
            this.confirmButton.setDisable(true);
        }

        if (this.currentPageNumber > 1 && (this.currentPageType != 4 || this.lessonsLists[this.currentLessonNumber].getLesson().get(this.currentPageNumber - 2).getType() == 4)) {
            this.prevButton.setDisable(false);
        } else {
            this.prevButton.setDisable(true);
        }

        if (this.currentPageNumber < this.lessonsLists[this.currentLessonNumber].getLesson().size() && this.confirmButton.isDisable()) {
            this.nextButton.setDisable(false);
        } else {
            this.nextButton.setDisable(true);
        }

        if (this.currentPageType != 3) {
            this.simulationButton.setDisable(true);
        } else {
            this.simulationButton.setDisable(false);
        }

        if (this.currentPageType < 4 && this.lessonsLists[this.currentLessonNumber].getLesson().get(this.currentPageNumber).getType() == 4) {
            this.nextButton.setText("Passa ai quiz");
        } else {
            this.nextButton.setText("Successivo");
        }

        if (this.currentPageNumber >= this.lessonsLists[this.currentLessonNumber].getLesson().size()) {
            this.confirmButton.setText("Conferma e termina lezione");
        } else {
            this.confirmButton.setText("Conferma la risposta");
        }

    }

    /**
     * Switchando tra il tipo di pagina prepara il layout da visualizzare
     *
     * @throws IOException  necessario per setLessonText() e setQuestionText()
     */
    private void setLessonContent() throws IOException {

        for (Node node : this.anchorPane.getChildren()) {
            node.setVisible(false);
        }

        switch(this.currentPageType) {
            case 1:
            case 3:
                this.setLessonText();
                this.lessonText.setVisible(true);
                AnchorPane.setRightAnchor(this.lessonText, 10.0);
                return;
            case 2:
                this.setLessonText();
                this.lessonText.setVisible(true);
                AnchorPane.setRightAnchor(this.lessonText, 440.0);
                this.imageView.setImage(new Image("/immagini/" + this.currentLessonNumber + "." + this.currentPageNumber + ".png"));
                this.imageView.setVisible(true);
                return;
            case 4:
                this.setQuestionText();
                this.lessonText.setVisible(true);
                this.one.setVisible(true);
                this.two.setVisible(true);
                this.three.setVisible(true);
                this.four.setVisible(true);
                return;
            default:
                throw new IllegalStateException("Unexpected value with lesson type: " + this.currentPageType);
        }
    }

    /**
     * Imposta il testo della domanda e delle risposte della pagina, leggendo dal rispettivo file in /src/lezioni/
     * Un file di testo della domanda deve seguire la seguente formattazione:
     * - La prima riga contiene il testo della domanda
     * - Le righe 3,5,7,9 contengono le 4 diverse risposte
     * - Le righe 2,4,6,8 sono righe vuote fatta eccezione per la riga sovrastante la risposta corretta che dovrà contenere il carattere 'T'
     *
     * @throws IOException  necessario per utilizzare la classe FileReader
     */
    private void setQuestionText() throws IOException {
        String var10002 = System.getProperty("user.dir");
        FileReader file = new FileReader(var10002 + "/src/main/resources/lezioni/" + this.currentLessonNumber + "." + this.currentPageNumber + ".txt");
        BufferedReader bufferedReader = new BufferedReader(file);
        String line = bufferedReader.readLine();
        line = line.replace("\\n", "\n");
        this.lessonText.setText(line);

        for(int i = 0; i < 4; ++i) {
            line = bufferedReader.readLine();
            if (line.equals("T")) {
                line = bufferedReader.readLine();
                this.radioButtons[i].setText(line);
                this.radioButtons[4] = this.radioButtons[i];
            } else {
                line = bufferedReader.readLine();
                this.radioButtons[i].setText(line);
            }

            if (this.lessonsLists[this.currentLessonNumber].getLesson().get(this.currentPageNumber - 1).isAlreadyAnswered()) {
                this.radioButtons[i].setDisable(true);
            } else {
                this.radioButtons[i].setDisable(false);
            }
        }

        this.toggleGroup.selectToggle(this.lessonsLists[this.currentLessonNumber].getLesson().get(this.currentPageNumber - 1).getSelectedButton());
        this.lessonTitle.setText("Domande");
    }

    /**
     * Imposta il testo della lezione della pagina, leggendo dal rispettivo file in /src/lezioni/
     * Un file di testo della lezione deve seguire la seguente formattazione:
     * - La prima riga contiene il titolo della pagina della lezione
     * - Dalla seconda riga in poi il testo deve essere intervallato dalla stringa "\n" per andare a capo
     *
     * @throws IOException  necessario per utilizzare la classe FileReader
     */
    private void setLessonText() throws IOException {
        String var10002 = System.getProperty("user.dir");
        FileReader fileReader = new FileReader(var10002 + "/src/main/resources/lezioni/" + this.currentLessonNumber + "." + this.currentPageNumber + ".txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder text = new StringBuilder();
        String line = bufferedReader.readLine();
        this.lessonTitle.setText(line);

        for(line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            line = line.replace("\\n", "\n");
            text.append(line);
        }

        this.lessonText.setText(text.toString());
    }

    /**
     * Salva la risposta data nella pagina corrente
     */
    private void setSelectedButton() {
        this.lessonsLists[this.currentLessonNumber].getLesson().get(this.currentPageNumber - 1).setSelectedButton(this.toggleGroup.getSelectedToggle());
    }
}
