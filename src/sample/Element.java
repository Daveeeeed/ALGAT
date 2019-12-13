package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Element {

    private int value;
    private final StackPane stackPane;
    private final Label label;
    private final Rectangle rectangle;

    Element(int value) {
        this.value = value;
        this.label = new Label(Integer.toString(this.value));
        this.label.setFont(Font.font("Avenir Medium", FontWeight.BOLD, 35.0D));
        this.rectangle = new Rectangle(50.0D, 50.0D);
        this.rectangle.setFill(Color.WHITE);
        this.rectangle.setStrokeWidth(1.0D);
        this.rectangle.setStroke(Color.BLACK);
        this.stackPane = new StackPane();
        this.stackPane.getChildren().addAll(this.rectangle, this.label);
        this.stackPane.setPadding(new Insets(0.0D, 3.0D, 0.0D, 3.0D));
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
        this.label.setText(Integer.toString(value));
    }

    public StackPane getStackPane() {
        return this.stackPane;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }
}
