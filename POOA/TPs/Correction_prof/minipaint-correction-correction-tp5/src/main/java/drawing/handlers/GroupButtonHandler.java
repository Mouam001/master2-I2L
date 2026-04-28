package drawing.handlers;

import drawing.shapes.GroupShape;
import drawing.shapes.IShape;
import drawing.ui.DrawingPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class GroupButtonHandler implements EventHandler<ActionEvent> {

    private DrawingPane drawingPane;

    public GroupButtonHandler(DrawingPane drawingPane) {
        this.drawingPane = drawingPane;
    }

    @Override
    public void handle(ActionEvent event) {
        if (!drawingPane.getSelection().iterator().hasNext())
            return;
        GroupShape group = new GroupShape();
        for(IShape shape: drawingPane.getSelection()) {
            drawingPane.removeShape(shape);
            group.addShape(shape);
        }
        drawingPane.addShape(group);
        drawingPane.getSelection().clearSelection();
    }

}
