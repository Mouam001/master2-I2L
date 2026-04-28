package drawing.handlers;

import drawing.ui.DrawingPane;
import drawing.ui.Observer;
import drawing.shapes.IShape;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class SelectionHandler implements EventHandler<MouseEvent>, Iterable<IShape> {
    private DrawingPane drawingPane;
    private ArrayList<IShape> selectedShapes;
    private ArrayList<Observer> observers;

    private double startX, startY;
    private double finalX, finalY;

    public SelectionHandler(DrawingPane drawingPane) {
        this.drawingPane = drawingPane;
        this.drawingPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        this.selectedShapes = new ArrayList<>();
        this.observers = new ArrayList<>();
    }
    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            startX = event.getX();
            startY = event.getY();

            IShape isOnShape = null;
            for (IShape s : drawingPane)
                if (s.isOn(event.getX(), event.getY())) {
                    isOnShape = s;
                    //break; // pas de break comme ça on prend le dernier (celui qui est "au dessus")
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
                this.drawingPane.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
            }
        }
        // Pour gérer la sélection de forme par clic-drag-release :
        if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            finalX = event.getX();
            finalY = event.getY();

            for(IShape s: drawingPane) {
                if (s.intersect(Math.min(startX, finalX),
                        Math.min(startY, finalY),
                        Math.abs(finalX-startX),
                        Math.abs(finalY-startY)))

                    addToSelection(s);
            }
            this.drawingPane.removeEventHandler(MouseEvent.MOUSE_RELEASED, this);
        }
    }

    private void addToSelection(IShape shape) {
        if (selectedShapes.contains(shape))
            return;
        shape.setSelected(true);
        selectedShapes.add(shape);
        notifyObservers();
    }

    private void removeFromSelection(IShape shape) {
        if (!selectedShapes.contains(shape))
            return;
        shape.setSelected(false);
        selectedShapes.remove(shape);
        notifyObservers();
    }

    public void clearSelection() {
        selectedShapes.forEach(iShape -> iShape.setSelected(false));
        selectedShapes.clear();
        notifyObservers();
    }

    @Override
    public Iterator<IShape> iterator() {
        return selectedShapes.iterator();
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    private void notifyObservers() {
        observers.forEach(observer -> observer.update());
    }
}
