package algat;

import java.util.LinkedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class SortTreeNode {

    private int pinIndex;
    private HBox hBox;
    private SortTreeNode childLeftNode;
    private SortTreeNode childRightNode;
    private LinkedList<Element> elements;

    public SortTreeNode() {
        hBox = new HBox();
        this.hBox.setAlignment(Pos.CENTER);
        this.hBox.setPrefHeight(62.0D);
        this.hBox.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(2.0D))));
        this.hBox.setMaxHeight(62.0D);
        this.hBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        elements = new LinkedList<>();
        childRightNode = null;
        childLeftNode = null;
    }

    public void pinActive() {
        int j = 0;

        for(int i = 0; i < this.elements.size(); ++i) {
            if (this.elements.get(i).getValue() < this.elements.getFirst().getValue()) {
                ++j;
                this.swapElements(i, j);
            }
        }

        this.swapElements(this.pinIndex, j);
        this.pinIndex = j;
        for (Element element : elements){
            if (element.getValue() <= elements.get(pinIndex).getValue()){
                element.getRectangle().setFill(Color.LIGHTPINK);
            } else if (element.getValue() > elements.get(pinIndex).getValue()){
                element.getRectangle().setFill(Color.LIGHTGRAY);
            }
            elements.get(pinIndex).getRectangle().setFill(Color.YELLOW);
        }
    }

    public void pinPassive() {
        int j = 0;

        for(int i = 0; i < this.elements.size(); ++i) {
            if (this.elements.get(i).getValue() < this.elements.getFirst().getValue()) {
                ++j;
            }
        }

        this.pinIndex = j;
    }

    public void swapElements(int first, int second) {
        int tmp = this.elements.get(first).getValue();
        this.elements.get(first).setValue(this.elements.get(second).getValue());
        this.elements.get(second).setValue(tmp);
    }

    public void addElement(int value) {
        this.elements.add(new Element(value));
        this.hBox.getChildren().add(this.elements.getLast().getStackPane());
    }

    public void addSortedElement(int value) {
        boolean added = false;
        int i = 0;
        while (!added && i < elements.size()){
            if (value < elements.get(i).getValue()) {
                elements.add(i, new Element(value));
                added = true;
            }
            ++i;
        }
        if (!added) elements.addLast(new Element(value));
    }

    public void removeLastElement() {
        this.hBox.getChildren().remove(this.elements.getLast().getStackPane());
        this.elements.remove(this.elements.size() - 1);
    }

    public double getXof(int element){
        return hBox.getLayoutX() + 56 * element;
    }

    public double getCenterX() {
        return (getHBox().getBoundsInParent().getMaxX() + getHBox().getBoundsInParent().getMinX())/2;
    }

    public double getElementCenterX(int element) {
        return this.elements.get(element).getStackPane().getBoundsInParent().getMinX() + this.hBox.getBoundsInParent().getMinX() + 25.0D;
    }

    public LinkedList<Element> getElements() {
        return this.elements;
    }

    public HBox getHBox() {
        return this.hBox;
    }

    public SortTreeNode getChildLeftNode() {
        return this.childLeftNode;
    }

    public void setChildLeftNode(SortTreeNode childLeftNode) {
        this.childLeftNode = childLeftNode;
    }

    public SortTreeNode getChildRightNode() {
        return this.childRightNode;
    }

    public void setChildRightNode(SortTreeNode childRightNode) {
        this.childRightNode = childRightNode;
    }

    public int getPinIndex() {
        return this.pinIndex;
    }
}
