package sample;

import javafx.scene.control.Toggle;

public class LessonPage {
    private final int type;
    private Toggle selectedButton;
    private boolean alreadyAnswered;

    LessonPage(int type) {
        this.type = type;
        this.selectedButton = null;
        this.alreadyAnswered = false;
    }

    public int getType() {
        return this.type;
    }

    public Toggle getSelectedButton() {
        return this.selectedButton;
    }

    public void setSelectedButton(Toggle selectedButton) {
        this.selectedButton = selectedButton;
    }

    public boolean isAlreadyAnswered() {
        return this.alreadyAnswered;
    }

    public void setAlreadyAnswered(boolean alreadyAnswered) {
        this.alreadyAnswered = alreadyAnswered;
    }

}
