package sample;

import java.util.LinkedList;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class SortTreeRow {
    final boolean option = true;
    private LinkedList<SortTreeNode> nodes = new LinkedList();
    private HBox rowBox = new HBox();

    public SortTreeRow() {
        this.rowBox.setAlignment(Pos.CENTER);
        this.rowBox.setPrefHeight(62.0D);
        this.rowBox.setSpacing(40.0D);
    }

    public LinkedList<SortTreeNode> getNodes() {
        return this.nodes;
    }

    public void addNode(SortTreeNode node) {
        this.nodes.add(node);
    }

    public SortTreeNode getNode(int i) {
        return this.nodes.get(i);
    }

    public SortTreeNode getFirstNode() {
        return this.nodes.getFirst();
    }

    public HBox getRowBox() {
        return this.rowBox;
    }
}
