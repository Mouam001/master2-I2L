package drawing.handlers;

import drawing.ui.DrawingPane;
import drawing.shapes.IShape;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class DeleteButtonHandler implements EventHandler<ActionEvent> {

    private DrawingPane drawingPane;

    public DeleteButtonHandler(DrawingPane drawingPane) {
        this.drawingPane = drawingPane;
    }

    @Override
    public void handle(ActionEvent event) {
        for(IShape shape: drawingPane.getSelection())
            drawingPane.removeShape(shape);
        drawingPane.getSelection().clearSelection();
    }
}
