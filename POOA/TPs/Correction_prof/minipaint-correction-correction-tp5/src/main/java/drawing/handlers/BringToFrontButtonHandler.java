package drawing.handlers;

import drawing.shapes.IShape;
import drawing.ui.DrawingPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class BringToFrontButtonHandler implements EventHandler<ActionEvent> {

    private DrawingPane drawingPane;

    public BringToFrontButtonHandler(DrawingPane drawingPane) {
        this.drawingPane = drawingPane;
    }

    @Override
    public void handle(ActionEvent event) {
        for(IShape shape: drawingPane.getSelection()) {
            drawingPane.removeShape(shape);
            drawingPane.addShape(shape);
        }
        drawingPane.getSelection().clearSelection();
    }
}
