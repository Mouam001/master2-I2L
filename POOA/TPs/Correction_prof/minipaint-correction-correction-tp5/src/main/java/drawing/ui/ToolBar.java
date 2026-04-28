package drawing.ui;

import drawing.handlers.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ToolBar extends HBox {

    private Button clearButton;
    private Button rectangleButton;
    private Button circleButton;
    private Button triangleButton;
    private Button deleteButton;
    private Button groupButton;
    private Button ungroupButton;
    private Button toFrontButton;

    public ToolBar(DrawingPane drawingPane) {

        ButtonFactory factory = new ButtonFactory(ButtonFactory.ICONS_ONLY);

        clearButton = factory.createButton(ButtonFactory.CLEAR);
        clearButton.addEventFilter(ActionEvent.ACTION, new ClearButtonHandler(drawingPane));
        rectangleButton = factory.createButton(ButtonFactory.RECTANGLE);
        rectangleButton.addEventFilter(ActionEvent.ACTION, new RectangleButtonHandler(drawingPane));
        circleButton = factory.createButton(ButtonFactory.CIRCLE);
        circleButton.addEventFilter(ActionEvent.ACTION, new EllipseButtonHandler(drawingPane));
        triangleButton = factory.createButton(ButtonFactory.TRIANGLE);
        triangleButton.addEventFilter(ActionEvent.ACTION, new TriangleButtonHandler(drawingPane));
        deleteButton = factory.createButton(ButtonFactory.DELETE);
        deleteButton.addEventFilter(ActionEvent.ACTION, new DeleteButtonHandler(drawingPane));

        groupButton = factory.createButton(ButtonFactory.GROUP);
        groupButton.addEventFilter(ActionEvent.ACTION, new GroupButtonHandler(drawingPane));
        ungroupButton = factory.createButton(ButtonFactory.UNGROUP);
        ungroupButton.addEventFilter(ActionEvent.ACTION, new UngroupButtonHandler(drawingPane));

        toFrontButton = factory.createButton(ButtonFactory.TOFRONT);
        toFrontButton.addEventFilter(ActionEvent.ACTION, new BringToFrontButtonHandler(drawingPane));

        this.getChildren().addAll(clearButton,
                rectangleButton, circleButton, triangleButton, deleteButton,
                groupButton, ungroupButton,
                toFrontButton);
        this.setPadding(new Insets(5));
        this.setSpacing(5.0);
    }
}
