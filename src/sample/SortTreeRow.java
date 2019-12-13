package sample;

import java.util.LinkedList;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class SortTreeRow {

    private final LinkedList<SortTreeNode> nodes = new LinkedList<>();
    private final HBox rowBox = new HBox();

    public SortTreeRow() {
        this.rowBox.setAlignment(Pos.CENTER);
        this.rowBox.setPrefHeight(62.0);
        this.rowBox.setSpacing(40.0);
    }

    public void setAsQuickSortRow(){
        this.rowBox.setAlignment(Pos.CENTER_LEFT);
        this.rowBox.setSpacing(0);
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
