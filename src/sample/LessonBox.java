package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class LessonBox implements Initializable {
    private final int LESSON_NR = 3;
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
    private int currentLessonType;
    private int currentLessonNumber;
    private int currentPageNumber;

    public LessonBox() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.lessonsLists = new Lesson[3];
        this.lessonsLists[0] = new Lesson(new int[]{1, 1, 1, 1, 2, 1, 1, 4, 4, 4, 4, 4});
        this.lessonsLists[1] = new Lesson(new int[]{1, 1, 2, 2, 3, 4, 4, 4, 4, 4});
        this.lessonsLists[2] = new Lesson(new int[]{1, 2, 2, 3, 4});
        this.toggleGroup = new ToggleGroup();
        this.one.setToggleGroup(this.toggleGroup);
        this.two.setToggleGroup(this.toggleGroup);
        this.three.setToggleGroup(this.toggleGroup);
        this.four.setToggleGroup(this.toggleGroup);
        this.radioButtons = new RadioButton[]{this.one, this.two, this.three, this.four, new RadioButton()};
    }

    @FXML
    void goToLastPage() throws IOException {
        this.loadLesson(this.currentLessonNumber, this.currentPageNumber - 1);
    }

    @FXML
    void goToNextPage() throws IOException {
        this.loadLesson(this.currentLessonNumber, this.currentPageNumber + 1);
    }

    @FXML
    void startSimulation() throws IOException {
        if (this.simulationButton.getText().equals("Avvia simulazione")) {
            this.simulationButton.setText("Termina simulazione");
            this.prevButton.setDisable(true);
            this.nextButton.setDisable(true);
            this.lessonText.setVisible(false);
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Lesson" + this.currentLessonNumber + "Simulation.fxml"));
            Parent root = loader.load();
            SubScene subScene1 = new SubScene(root, 878.0D, 469.0D);
            this.anchorPane.getChildren().add(subScene1);
            AnchorPane.setRightAnchor(subScene1, 0.0D);
            AnchorPane.setLeftAnchor(subScene1, 0.0D);
            AnchorPane.setTopAnchor(subScene1, 0.0D);
            AnchorPane.setBottomAnchor(subScene1, 0.0D);
        } else {
            this.simulationButton.setText("Avvia simulazione");
            this.prevButton.setDisable(false);
            this.nextButton.setDisable(false);
            this.lessonText.setVisible(true);
            this.anchorPane.getChildren().remove(this.anchorPane.getChildren().size() - 1);
        }

    }

    @FXML
    void checkCorrectAnswer() throws IOException {
        ButtonType button = new ButtonType("OK");
        Alert alert = new Alert(AlertType.NONE);
        ImageView imageView;
        if (this.toggleGroup.getSelectedToggle() == null) {
            imageView = new ImageView("/immagini/alert2.png");
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(50.0D);
            alert.setTitle("Avviso");
            alert.setHeaderText("Nessuna risposta selezionata");
            alert.setContentText("Seleziona una risposta per continuare.");
            alert.getButtonTypes().setAll(button);
            alert.setGraphic(imageView);
            alert.showAndWait();
        } else {
            if (this.toggleGroup.getSelectedToggle() == this.radioButtons[4]) {
                this.lessonsLists[this.currentLessonNumber].getLesson().get(this.currentPageNumber - 1).setCorrectlyAnswered(true);
                imageView = new ImageView("/immagini/alert3.png");
                alert.setContentText("Risposta corretta.");
            } else {
                imageView = new ImageView("/immagini/alert1.png");
                alert.setContentText("Risposta sbagliata.");
            }

            imageView.setPreserveRatio(true);
            imageView.setFitHeight(50.0D);
            alert.setTitle("Avviso");
            alert.setHeaderText("Hai risposta alla domanda");
            alert.getButtonTypes().setAll(button);
            alert.setGraphic(imageView);
            alert.showAndWait();
            this.setSelectedButton();
            this.toggleGroup.getSelectedToggle().setSelected(false);
            this.lessonsLists[this.currentLessonNumber].getLesson().get(this.currentPageNumber - 1).setAlreadyAnswered(true);
            if (this.currentPageNumber < this.lessonsLists[this.currentLessonNumber].getLesson().size()) {
                this.loadLesson(this.currentLessonNumber, this.currentPageNumber + 1);
            } else {
                TabPane tabPane = (TabPane)this.anchorPaneRoot.getParent().getParent();
                boolean completed = true;
                int correct = 0;
                int total = 0;
                Iterator var8 = this.lessonsLists[this.currentLessonNumber].getLesson().iterator();

                while(true) {
                    LessonPage lessonPages;
                    do {
                        if (!var8.hasNext()) {
                            if (completed) {
                                ((CheckBox)((VBox) tabPane.getTabs().get(0).getContent()).getChildren().get(3)).setSelected(true);
                                ((CheckBox)((VBox) tabPane.getTabs().get(0).getContent()).getChildren().get(3)).setText("Lezione completata");
                                ((CheckBox)((VBox) tabPane.getTabs().get(0).getContent()).getChildren().get(8)).setSelected(true);
                                ((CheckBox)((VBox) tabPane.getTabs().get(0).getContent()).getChildren().get(8)).setText(Integer.toString(this.currentLessonNumber));
                            }

                            tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
                            tabPane.getSelectionModel().selectFirst();
                            alert.setHeaderText("Lezione terminata");
                            alert.setContentText("Hai risposto correttamente a " + correct + " domande su " + total);
                            alert.getButtonTypes().setAll(button);
                            alert.setGraphic(imageView);
                            alert.showAndWait();
                            return;
                        }

                        lessonPages = (LessonPage)var8.next();
                    } while(lessonPages.getType() != 4);

                    completed = completed && lessonPages.isCorrectlyAnswered();
                    if (lessonPages.isCorrectlyAnswered()) {
                        ++correct;
                    }

                    ++total;
                }
            }
        }

    }

    void loadLesson(int lessonNumber, int destinationPageNr) throws IOException {
        this.currentLessonNumber = lessonNumber;
        this.currentPageNumber = destinationPageNr;
        this.currentLessonType = this.lessonsLists[lessonNumber].getLesson().get(destinationPageNr - 1).getType();
        this.setLessonButtons();
        this.setLessonContent();
    }

    private void setLessonButtons() {
        if (this.currentLessonType == 4 && !this.lessonsLists[this.currentLessonNumber].getLesson().get(this.currentPageNumber - 1).isAlreadyAnswered()) {
            this.confirmButton.setDisable(false);
        } else {
            this.confirmButton.setDisable(true);
        }

        if (this.currentPageNumber > 1 && (this.currentLessonType != 4 || this.lessonsLists[this.currentLessonNumber].getLesson().get(this.currentPageNumber - 2).getType() == 4)) {
            this.prevButton.setDisable(false);
        } else {
            this.prevButton.setDisable(true);
        }

        if (this.currentPageNumber < this.lessonsLists[this.currentLessonNumber].getLesson().size() && this.confirmButton.isDisable()) {
            this.nextButton.setDisable(false);
        } else {
            this.nextButton.setDisable(true);
        }

        if (this.currentLessonType != 3) {
            this.simulationButton.setDisable(true);
        } else {
            this.simulationButton.setDisable(false);
        }

        if (this.currentLessonType < 4 && this.lessonsLists[this.currentLessonNumber].getLesson().get(this.currentPageNumber).getType() == 4) {
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

    private void setLessonContent() throws IOException {
        System.out.println(this.currentLessonNumber + "." + this.currentPageNumber);
        Iterator var1 = this.anchorPane.getChildren().iterator();

        while(var1.hasNext()) {
            Node node = (Node)var1.next();
            node.setVisible(false);
        }

        switch(this.currentLessonType) {
            case 1:
            case 3:
                this.setLessonText();
                this.lessonText.setVisible(true);
                AnchorPane.setRightAnchor(this.lessonText, 10.0D);
                return;
            case 2:
                this.setLessonText();
                this.lessonText.setVisible(true);
                AnchorPane.setRightAnchor(this.lessonText, 440.0D);
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
                throw new IllegalStateException("Unexpected value with lesson type: " + this.currentLessonType);
        }
    }

    private void setQuestionText() throws IOException {
        String var10002 = System.getProperty("user.dir");
        FileReader file = new FileReader(var10002 + "/src/lezioni/" + this.currentLessonNumber + "." + this.currentPageNumber + ".txt");
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

    private void setLessonText() throws IOException {
        String var10002 = System.getProperty("user.dir");
        FileReader fileReader = new FileReader(var10002 + "/src/lezioni/" + this.currentLessonNumber + "." + this.currentPageNumber + ".txt");
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

    private void setSelectedButton() {
        this.lessonsLists[this.currentLessonNumber].getLesson().get(this.currentPageNumber - 1).setSelectedButton(this.toggleGroup.getSelectedToggle());
    }
}
