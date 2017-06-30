package se.fearless.salix.view;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import se.fearless.salix.EntryView;
import se.fearless.salix.Salix;

public class SalixRenderer<T> {
    private final Salix<T> salix;
    private final Xform group = new Xform();


    public SalixRenderer(Salix<T> salix) {
        this.salix = salix;

    }

    public void updateMesh() {
        group.getChildren().clear();

        salix.visitNodes(informationNode -> {
            se.fearless.salix.Box boundingBox = informationNode.getBoundingBox();
            Point3D size = boundingBox.getB().subtract(boundingBox.getA());
            final Box box = new Box(size.getX(), size.getY(), size.getZ());
            Point3D center = boundingBox.getCenter();
            translate(box, center);

            box.setDrawMode(DrawMode.LINE);

            System.out.println("Adding node " + informationNode);
            group.getChildren().add(box);
        });

        salix.visitEntries(entryView -> {
            Sphere sphere = new Sphere(entryView.getRadius(), 8);

            Point3D position = entryView.getPosition();
            translate(sphere, position);
            group.getChildren().add(sphere);
            System.out.println("Adding entry " + entryView);

        });
    }

    private void translate(Shape3D box, Point3D center) {
        box.setTranslateX(center.getX());
        box.setTranslateY(center.getY());
        box.setTranslateZ(center.getZ());
    }

    public Node getNode() {
        return group;
    }


}