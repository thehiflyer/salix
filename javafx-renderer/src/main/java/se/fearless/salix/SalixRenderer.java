package se.fearless.salix;

import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class SalixRenderer {
    private final Group root3D = new Group();
    private SubScene subScene;



    private final double paneW;
    private final double paneH;
    private final double dimModel;



    public SalixRenderer(double paneW, double paneH, double dimModel) {
        this.paneW = paneW;
        this.paneH = paneH;
        this.dimModel = dimModel;


        subScene = new SubScene(root3D,paneW,paneH,true,javafx.scene.SceneAntialiasing.BALANCED);

        subScene.setFill(Color.CADETBLUE);

    }


    public Node getNode() {
        return subScene;
    }

    private void buildAxes() {
        double length = 2d*dimModel;
        double width = dimModel/100d;
        double radius = 2d*dimModel/100d;
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        Sphere xSphere = new Sphere(radius);
        Sphere ySphere = new Sphere(radius);
        Sphere zSphere = new Sphere(radius);
        xSphere.setMaterial(redMaterial);
        ySphere.setMaterial(greenMaterial);
        zSphere.setMaterial(blueMaterial);

        xSphere.setTranslateX(dimModel);
        ySphere.setTranslateY(dimModel);
        zSphere.setTranslateZ(dimModel);

        Box xAxis = new Box(length, width, width);
        Box yAxis = new Box(width, length, width);
        Box zAxis = new Box(width, width, length);
        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        root3D.getChildren().addAll(xAxis, yAxis, zAxis);
        root3D.getChildren().addAll(xSphere, ySphere, zSphere);
    }

}
