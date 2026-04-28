package drawing.ui;

import drawing.handlers.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by lewandowski on 20/12/2020.
 */
public class PaintApplication extends Application {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Scene scene;
    private BorderPane root;
    private DrawingPane drawingPane;

    private ToolBar toolBar;
    private StatutBar sbar;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new BorderPane();
        scene = new Scene(root, WIDTH, HEIGHT);

        root.getStylesheets().add(
                PaintApplication.class.getClassLoader().getResource("style/Paint.css").toExternalForm());

        drawingPane = new DrawingPane();
        drawingPane.getStyleClass().add("drawingPane");
        root.setCenter(drawingPane);

        toolBar = new ToolBar(drawingPane);
        toolBar.getStyleClass().add("toolbar");
        root.setTop(toolBar);

        sbar = new StatutBar(drawingPane);
        drawingPane.addObserver(sbar);
        drawingPane.getSelection().addObserver(sbar);
        sbar.getStyleClass().add("statutbar");
        root.setBottom(sbar);

        primaryStage.setTitle("Drawing");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public DrawingPane getDrawingPane() {
        return drawingPane;
    }

    public StatutBar getStatutBar() {
        return sbar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
