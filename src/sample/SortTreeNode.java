package sample;

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
    private SortTreeNode childLeftNode = null;
    private SortTreeNode childRightNode = null;
    private SortTreeNode parent1Node = null;
    private SortTreeNode parent2Node = null;
    private LinkedList<Element> elements = new LinkedList();
    private HBox hBox = new HBox();
    private int pinIndex;

    public SortTreeNode() {
        this.hBox.setAlignment(Pos.CENTER);
        this.hBox.setPrefHeight(62.0D);
        this.hBox.setBorder(new Border(new BorderStroke[]{new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, (CornerRadii)null, new BorderWidths(2.0D))}));
        this.hBox.setMaxHeight(62.0D);
        this.hBox.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)}));
    }

    public void pinActive() {
        int j = 0;

        for(int i = 0; i < this.elements.size(); ++i) {
            if (((Element)this.elements.get(i)).getValue() < ((Element)this.elements.getFirst()).getValue()) {
                ++j;
                this.swapElements(i, j);
            }
        }

        this.swapElements(this.pinIndex, j);
        this.pinIndex = j;
        System.out.println(this.pinIndex);
    }

    public void pinPassive() {
        int j = 0;

        for(int i = 0; i < this.elements.size(); ++i) {
            if (((Element)this.elements.get(i)).getValue() < ((Element)this.elements.getFirst()).getValue()) {
                ++j;
            }
        }

        this.pinIndex = j;
        System.out.println(this.pinIndex);
    }

    public void swapElements(int first, int second) {
        int tmp = ((Element)this.elements.get(first)).getValue();
        ((Element)this.elements.get(first)).setValue(((Element)this.elements.get(second)).getValue());
        ((Element)this.elements.get(second)).setValue(tmp);
    }

    public void addElement(int value) {
        this.elements.add(new Element(value));
        this.hBox.getChildren().add(((Element)this.elements.getLast()).getStackPane());
    }

    public void removeLastElement() {
        this.hBox.getChildren().remove(((Element)this.elements.getLast()).getStackPane());
        this.elements.remove(this.elements.size() - 1);
    }

    public void removeFirstElement() {
        this.hBox.getChildren().remove(((Element)this.elements.getFirst()).getStackPane());
        this.elements.remove(0);
    }

    public int getNodeDepth(SortTreeNode node) {
        return node.getParent1Node() == null && node.getParent2Node() == null ? 0 : 1 + Math.max(this.getNodeDepth(node.getParent1Node()), this.getNodeDepth(node.getParent2Node()));
    }

    public double getCenterX() {
        double a = this.hBox.getBoundsInParent().getMinX() + this.hBox.getParent().getBoundsInParent().getMinX() + this.hBox.getParent().getParent().getBoundsInParent().getMinX();
        double b = this.hBox.getBoundsInParent().getMaxX() + this.hBox.getParent().getBoundsInParent().getMinX() + this.hBox.getParent().getParent().getBoundsInParent().getMinX();
        return (a + b) / 2.0D;
    }

    public double getElementY() {
        double a = this.hBox.getBoundsInParent().getMinX();
        double b = this.hBox.getBoundsInParent().getMaxY();
        return (a + b) / 2.0D;
    }

    public double getElementX(int element) {
        return ((Element)this.elements.get(element)).getStackPane().getBoundsInParent().getMinX() + this.hBox.getBoundsInParent().getMinX() + 25.0D;
    }

    public double getCenterY() {
        double a = this.hBox.getBoundsInParent().getMinY() + this.hBox.getParent().getBoundsInParent().getMinY() + this.hBox.getParent().getParent().getBoundsInParent().getMinY() + this.hBox.getParent().getParent().getParent().getBoundsInParent().getMinY();
        double b = this.hBox.getBoundsInParent().getMaxY() + this.hBox.getParent().getBoundsInParent().getMinY() + this.hBox.getParent().getParent().getBoundsInParent().getMinY() + this.hBox.getParent().getParent().getParent().getBoundsInParent().getMinY();
        return (a + b) / 2.0D;
    }

    public LinkedList<Element> getElements() {
        return this.elements;
    }

    public HBox gethBox() {
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

    public SortTreeNode getParent1Node() {
        return this.parent1Node;
    }

    public void setParent1Node(SortTreeNode parent1Node) {
        this.parent1Node = parent1Node;
    }

    public SortTreeNode getParent2Node() {
        return this.parent2Node;
    }

    public void setParent2Node(SortTreeNode parent2Node) {
        this.parent2Node = parent2Node;
    }

    public int getPinIndex() {
        return this.pinIndex;
    }
}
