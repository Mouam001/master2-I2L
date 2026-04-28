package drawing.handlers;

import drawing.ui.DrawingPane;
import drawing.shapes.IShape;
import drawing.shapes.ShapeAdapter;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

/**
 * Created by lewandowski on 20/12/2020.
 */
public abstract class ShapeButtonHandler implements EventHandler<Event> {

    private DrawingPane drawingPane;
    protected double originX;
    protected double originY;
    protected double destinationX;
    protected double destinationY;

    protected IShape shape;

    public ShapeButtonHandler(DrawingPane drawingPane) {
        this.drawingPane = drawingPane;
    }

    @Override
    public void handle(Event event) {

        if (event instanceof ActionEvent) {
            drawingPane.addEventFilter(MouseEvent.MOUSE_PRESSED, this);
        }

        if (event instanceof MouseEvent) {

            if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                drawingPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, this);
                drawingPane.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
                originX = ((MouseEvent) event).getX();
                originY = ((MouseEvent) event).getY();
                event.consume();
            }

            if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
                event.consume();
            }

            if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                destinationX = ((MouseEvent) event).getX();
                destinationY = ((MouseEvent) event).getY();
                shape = new ShapeAdapter(createShape());
                drawingPane.addShape(shape);

                drawingPane.removeEventFilter(MouseEvent.MOUSE_PRESSED, this);
                drawingPane.removeEventFilter(MouseEvent.MOUSE_DRAGGED, this);
                drawingPane.removeEventHandler(MouseEvent.MOUSE_RELEASED, this);
            }
        }
    }

    protected abstract Shape createShape();

}
