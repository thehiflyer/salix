package se.fearless.salix.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import se.fearless.salix.Salix;

import java.util.Random;

public class ViewApplication extends Application {
	private static final double AXIS_LENGTH = 250.0;
	private static final double CAMERA_INITIAL_DISTANCE = -450;
	private static final double CAMERA_INITIAL_X_ANGLE = 70.0;
	private static final double CAMERA_INITIAL_Y_ANGLE = 320.0;
	private static final double CAMERA_NEAR_CLIP = 0.1;
	private static final double CAMERA_FAR_CLIP = 10000.0;
	private final Group root = new Group();
	private final Xform world = new Xform();
	private final PerspectiveCamera camera = new PerspectiveCamera(true);
	private final Xform cameraXform = new Xform();
	private final Xform cameraXform2 = new Xform();
	private final Xform cameraXform3 = new Xform();
	private final Xform axisGroup = new Xform();
	private final BorderPane pane = new BorderPane();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {


		Scene scene = new Scene(root, 1024, 768, true);
		scene.setFill(Color.GREY);

		stage.setTitle("Salix View");
		stage.setScene(scene);

		buildCamera();
		buildAxes();


		scene.setCamera(camera);
		root.getChildren().addAll(world);

		Salix<String> stringSalix = new Salix<>(-1000, -1000, -1000, 1000, 1000, 1000, 3);
		SalixRenderer<String> salixRenderer = new SalixRenderer<>(stringSalix);
		populateTree(stringSalix, 600, -1000, 1000);

		salixRenderer.updateMesh();

		root.getChildren().add(salixRenderer.getNode());

		MouseHandler mouseHandler = new MouseHandler(camera, cameraXform, cameraXform2);
		mouseHandler.handleMouse(scene);

		KeyHandler keyHandler = new KeyHandler(salixRenderer);
		keyHandler.handleKeyPresses(scene);
		stage.show();
	}

	private void buildCamera() {
		root.getChildren().add(cameraXform);
		cameraXform.getChildren().add(cameraXform2);
		cameraXform2.getChildren().add(cameraXform3);
		cameraXform3.getChildren().add(camera);
		cameraXform3.setRotateZ(180.0);

		camera.setNearClip(CAMERA_NEAR_CLIP);
		camera.setFarClip(CAMERA_FAR_CLIP);
		camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
		cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
		cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
	}

	private void buildAxes() {
		final PhongMaterial redMaterial = new PhongMaterial();
		redMaterial.setDiffuseColor(Color.DARKRED);
		redMaterial.setSpecularColor(Color.RED);

		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseColor(Color.DARKGREEN);
		greenMaterial.setSpecularColor(Color.GREEN);

		final PhongMaterial blueMaterial = new PhongMaterial();
		blueMaterial.setDiffuseColor(Color.DARKBLUE);
		blueMaterial.setSpecularColor(Color.BLUE);

		final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
		final Box yAxis = new Box(1, AXIS_LENGTH, 1);
		final Box zAxis = new Box(1, 1, AXIS_LENGTH);

		xAxis.setMaterial(redMaterial);
		yAxis.setMaterial(greenMaterial);
		zAxis.setMaterial(blueMaterial);

		axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
		axisGroup.setVisible(true);
		world.getChildren().addAll(axisGroup);
	}

	private void populateTree(Salix<String> salix, int qty, int minXtent, int maxXtent) throws Exception {
		Random random = new Random(1234);
		for (int i = 0; i < qty; i++) {
			salix.add("" + i, getRandom(random, minXtent, maxXtent), getRandom(random, minXtent, maxXtent), getRandom(random, minXtent, maxXtent), random.nextInt(20));
		}

	}

	private int getRandom(Random random, int minXtent, int maxXtent) {
		return random.nextInt(2 * maxXtent) + minXtent;
	}
}
