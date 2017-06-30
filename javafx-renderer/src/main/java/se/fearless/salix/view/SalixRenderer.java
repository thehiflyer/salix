package se.fearless.salix.view;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import se.fearless.salix.Salix;

public class SalixRenderer<T> {
    private final Salix<T> salix;
    private final Xform group = new Xform();


    public SalixRenderer(Salix<T> salix) {
        this.salix = salix;

    }

    public void updateMesh() {
        group.getChildren().clear();

        salix.executeInformationVisitor(informationNode -> {
            se.fearless.salix.Box boundingBox = informationNode.getBoundingBox();
            Point3D size = boundingBox.getB().subtract(boundingBox.getA());
            final Box box = new Box(size.getX(), size.getY(), size.getZ());
            box.setTranslateX(boundingBox.getCenter().getX());
            box.setTranslateY(boundingBox.getCenter().getY());
            box.setTranslateZ(boundingBox.getCenter().getZ());

            box.setDrawMode(DrawMode.LINE);

            System.out.println("Adding " + informationNode);
            group.getChildren().add(box);
        });
    }

    public Node getNode() {
        return group;
    }
}
