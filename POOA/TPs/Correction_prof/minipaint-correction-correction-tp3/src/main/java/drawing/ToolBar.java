package drawing.ui;

import drawing.ui.ToolBar;
import drawing.ui.StatutBar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PaintApplication extends Application {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Scene scene;
    private BorderPane root;
    private DrawingPane drawingPane;
    private StatutBar statutBar;

    @Override
    public void start(Stage primaryStage) throws Exception {

        root = new BorderPane();
        scene = new Scene(root, WIDTH, HEIGHT);

        // Chargement du CSS
        root.getStylesheets().add(
                PaintApplication.class.getClassLoader()
                        .getResource("style/Paint.css")
                        .toExternalForm()
        );

        // Zone de dessin
        drawingPane = new DrawingPane();
        drawingPane.getStyleClass().add("drawingPane");
        root.setCenter(drawingPane);

        // Barre d’outils (TOOLBAR)
        ToolBar toolBar = new ToolBar(drawingPane);
        root.setTop(toolBar);

        // Barre de Statut
        statutBar = new StatutBar(drawingPane);
        root.setBottom(statutBar);

        primaryStage.setTitle("Drawing");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public DrawingPane getDrawingPane() {
        return drawingPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
