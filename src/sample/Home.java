package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Home implements Initializable {
    private final int LESSON_NR = 3;
    @FXML
    private TabPane tabPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Pagination pagination;
    @FXML
    private CheckBox lessonStatus;
    @FXML
    private CheckBox verify;
    private boolean[] lessonCompleted;
    private Image[] images;
    private int currentImageDisplayed = 0;

    public Home() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.pagination.setMaxPageIndicatorCount(3);
        this.images = new Image[3];
        IntStream.range(0, this.images.length).forEach((i) -> {
            this.images[i] = new Image("/immagini/" + i + ".png");
        });
        this.lessonCompleted = new boolean[3];
        IntStream.range(0, this.lessonCompleted.length).forEach((i) -> {
            this.lessonCompleted[i] = false;
        });
    }

    @FXML
    void goToLesson() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("LessonBox.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        LessonBox lessonBox = (LessonBox)fxmlLoader.getController();
        lessonBox.loadLesson(this.pagination.getCurrentPageIndex(), 1);
        Tab tab = new Tab("Lezione " + (this.pagination.getCurrentPageIndex() + 1), root);
        tab.setClosable(true);
        this.tabPane.getTabs().add(tab);
    }

    @FXML
    void updateImage() {
        if (this.verify.isSelected()) {
            this.verify.setSelected(false);
            this.lessonCompleted[Integer.parseInt(this.verify.getText())] = true;
        }

        if (this.pagination.getCurrentPageIndex() != this.currentImageDisplayed) {
            this.imageView.setImage(this.images[this.pagination.getCurrentPageIndex()]);
            if (this.lessonCompleted[this.pagination.getCurrentPageIndex()]) {
                this.lessonStatus.setText("Lezione completata");
                this.lessonStatus.setSelected(true);
            } else {
                this.lessonStatus.setText("Lezione da completare");
                this.lessonStatus.setSelected(false);
            }

            this.currentImageDisplayed = this.pagination.getCurrentPageIndex();
        }

    }
}
