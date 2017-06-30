package se.fearless.salix;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ViewApplication extends Application {
    private final BorderPane pane=new BorderPane();


    @Override
    public void start(Stage stage) throws Exception {
        SalixMouseEventHandler salixMouseEventHandler = new SalixMouseEventHandler();
        final Scene scene = new Scene(pane, 880, 680, true);
        scene.addEventHandler(MouseEvent.ANY, salixMouseEventHandler);
        //scene.cursorProperty().bind(rubik.getCursor());
        scene.setFill(Color.ALICEBLUE);
        stage.setTitle("Salix View");
        stage.setScene(scene);
        stage.show();
    }
}
