package drawing;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class SelectionHandler implements EventHandler<MouseEvent>, Iterable<IShape> {
    private DrawingPane drawingPane;
    private ArrayList<IShape> selectedShapes;

    public SelectionHandler(DrawingPane drawingPane) {
        this.drawingPane = drawingPane;
        this.drawingPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        this.selectedShapes = new ArrayList<>();
    }
    @Override
    public void handle(MouseEvent event) {
        IShape isOnShape = null;
        for (IShape s: drawingPane)
            if (s.isOn(event.getX(), event.getY())) {
                isOnShape = s;
                break;
            }

        if (isOnShape != null) {
            if (selectedShapes.contains(isOnShape)) {
                if (event.isShiftDown() && selectedShapes.size() > 1)
                    removeFromSelection(isOnShape);
            } else {
                if (!event.isShiftDown()) {
                    clearSelection();
                }
                addToSelection(isOnShape);
            }
        } else {
            clearSelection();
        }
    }

    private void addToSelection(IShape shape) {
        if (selectedShapes.contains(shape))
            return;
        shape.setSelected(true);
        selectedShapes.add(shape);
    }

    private void removeFromSelection(IShape shape) {
        if (!selectedShapes.contains(shape))
            return;
        shape.setSelected(false);
        selectedShapes.remove(shape);
    }

    private void clearSelection() {
        selectedShapes.forEach(iShape -> iShape.setSelected(false));
        selectedShapes.clear();
    }

    @Override
    public Iterator<IShape> iterator() {
        return selectedShapes.iterator();
    }

}
