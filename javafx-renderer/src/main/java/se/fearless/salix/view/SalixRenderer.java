package se.fearless.salix.view;

import javafx.geometry.Point3D;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import se.fearless.salix.EntryView;
import se.fearless.salix.Salix;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SalixRenderer<T> {
    private final Salix<T> salix;
    private final Xform group = new Xform();
    private int currentDepth = 0;


    public SalixRenderer(Salix<T> salix) {
        this.salix = salix;

    }

    public void updateMesh() {
        group.getChildren().clear();

        createEntryMeshes();
        createNodesMeshes();

    }

    private void createEntryMeshes() {

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);


        salix.visitEntries(entryView -> {
            Sphere sphere = new Sphere(entryView.getRadius(), 8);

            sphere.setUserData(entryView);



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

    private void createNodesMeshes() {
        final PhongMaterial boxMaterial = new PhongMaterial();
        boxMaterial.setDiffuseColor(new Color(0.1, 0.2, 0.9, 0.005));
        //boxMaterial.setSpecularColor(Color.WHITE);


        class Oct {
            int depth;
            Box box;

            public Oct(int depth, Box box) {
                this.depth = depth;
                this.box = box;
            }
        }

        List<Oct> boxes = new ArrayList<>();

        AtomicInteger maxDepth = new AtomicInteger();
        salix.visitNodes(informationNode -> {
            int depth = informationNode.getDepth();

	        if (depth >= currentDepth) {
		        maxDepth.set(Math.max(maxDepth.get(), depth));
		        se.fearless.salix.Box boundingBox = informationNode.getBoundingBox();
		        Point3D size = boundingBox.getB().subtract(boundingBox.getA());
		        final Box box = new Box(size.getX()-1*0.9, size.getY()*0.9, size.getZ()*0.9);
		        Point3D center = boundingBox.getCenter();
		        translate(box, center);

		        box.setDisable(true);

		        //box.setMaterial(boxMaterial);
		        //box.setDrawMode(DrawMode.LINE);

		        boxes.add(new Oct(depth, box));
		        //group.getChildren().add(box);
	        }
        });

        boxes.sort(Comparator.comparingInt(o -> o.depth));

        double part =  1d / (double)maxDepth.get();

        for (Oct box : boxes) {
            double opacity = part * box.depth;
            final PhongMaterial material = new PhongMaterial();

            material.setDiffuseColor(new Color(0.1d, 0.1d, 1d - opacity, opacity/ 2d));
            box.box.setMaterial(material);
            group.getChildren().add(box.box);
        }

    }

    private void translate(Shape3D box, Point3D center) {
        box.setTranslateX(center.getX());
        box.setTranslateY(center.getY());
        box.setTranslateZ(center.getZ());
    }

    public Node getNode() {
        return group;
    }


	public void increaseDepth() {
		currentDepth++;
		updateMesh();
	}


	public void decreaseDepth() {
		currentDepth = Math.max(0, currentDepth - 1);
		updateMesh();
	}
}
