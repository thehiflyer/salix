package se.fearless.salix.view;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import se.fearless.salix.EntryView;
import se.fearless.salix.Salix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SalixRenderer<T> {
    private final Salix<T> salix;
    private final Xform group = new Xform();
    private HashMap<T, Sphere> entryMap = new HashMap<>();

    final PhongMaterial redMaterial = new PhongMaterial();
    {
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
    }

    final PhongMaterial greenMaterial = new PhongMaterial();
    {
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
    }

    final PhongMaterial highlightMaterial = new PhongMaterial();
    {
        greenMaterial.setDiffuseColor(Color.DARKORANGE);
        greenMaterial.setSpecularColor(Color.ORANGE);
    }

    private List<T> highlighted = new ArrayList<>();

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

            box.setDisable(true);

            box.setDrawMode(DrawMode.LINE);

            System.out.println("Adding node " + informationNode);
            group.getChildren().add(box);
        });



        entryMap.clear();
        salix.visitEntries(entryView -> {
            Sphere sphere = new Sphere(entryView.getRadius(), 16);

            sphere.setUserData(entryView);

            entryMap.put(entryView.getValue(), sphere);

            if (entryView.isIntersectsNodeBounds()) {
                sphere.setMaterial(redMaterial);
            } else {
                sphere.setMaterial(greenMaterial);
            }

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

    public void clearHighlighted() {
        for (T t : highlighted) {
            Sphere sphere = entryMap.get(t);
            sphere.setMaterial(greenMaterial);
        }
        highlighted.clear();
    }

    public void setHightlighted(Collection<T> intersecting) {
        for (T t : intersecting) {
            Sphere sphere = entryMap.get(t);
            sphere.setMaterial(highlightMaterial);
            highlighted.add(t);
        }
    }
}
