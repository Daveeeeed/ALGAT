package algat;

import java.util.LinkedList;

public class SortTreeRow {

    private final LinkedList<SortTreeNode> nodes;

    public SortTreeRow() {
        nodes = new LinkedList<>();
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
}
