package sample;

import javafx.scene.shape.Cylinder;

class DiscNode {
    private Cylinder cylinder = new Cylinder();

    DiscNode(int cylinderNumber) {
        this.cylinder.setHeight(23.0D);
        this.cylinder.setRadius((double)(100 - cylinderNumber * 10));
    }

    Cylinder getCylinder() {
        return this.cylinder;
    }
}
