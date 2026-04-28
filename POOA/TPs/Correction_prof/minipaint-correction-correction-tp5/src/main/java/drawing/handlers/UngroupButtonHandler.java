package drawing.handlers;

import drawing.shapes.GroupShape;
import drawing.shapes.IShape;
import drawing.ui.DrawingPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class UngroupButtonHandler implements EventHandler<ActionEvent> {

    private DrawingPane drawingPane;

    public UngroupButtonHandler(DrawingPane drawingPane) {
        this.drawingPane = drawingPane;
    }

    @Override
    public void handle(ActionEvent event) {
        for (IShape shape: drawingPane.getSelection()) {
            if (shape instanceof GroupShape) {
                drawingPane.removeShape(shape);
                for (IShape child : (GroupShape) shape)
                    drawingPane.addShape(child);
            }
        }
        drawingPane.getSelection().clearSelection();

    }
}
