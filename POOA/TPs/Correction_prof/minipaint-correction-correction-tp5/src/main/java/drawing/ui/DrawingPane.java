package drawing.ui;

import drawing.handlers.MouseMoveHandler;
import drawing.handlers.SelectionHandler;
import drawing.shapes.IShape;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by lewandowski on 20/12/2020.
 */
public class DrawingPane extends Pane implements Iterable<IShape> {

    private MouseMoveHandler mouseMoveHandler;
    private SelectionHandler selectionHandler;

    private ArrayList<IShape> shapes;
    private ArrayList<Observer> observers;

    public DrawingPane() {
        clipChildren();
        shapes = new ArrayList<>();
        observers = new ArrayList<>();
        mouseMoveHandler = new MouseMoveHandler(this);
        selectionHandler = new SelectionHandler(this);
    }


    /**
     * Clips the children of this {@link Region} to its current size.
     * This requires attaching a change listener to the region’s layout bounds,
     * as JavaFX does not currently provide any built-in way to clip children.
     */
    void clipChildren() {
        final Rectangle outputClip = new Rectangle();
        this.setClip(outputClip);

        this.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });
    }

    public void addShape(IShape shape) {
        shapes.add(shape);
        shape.addShapeToPane(this);
        notifyAllObservers();
    }

    public void removeShape(IShape shape) {
        shapes.remove(shape);
        shape.removeShapeFromPane(this);
        notifyAllObservers();
    }

    public void clear() {
        for(IShape shape: shapes)
            shape.removeShapeFromPane(this);
        shapes.clear();
        selectionHandler.clearSelection();
        notifyAllObservers();
    }

    @Override
    public Iterator<IShape> iterator() {
        return shapes.iterator();
    }

    public int getNbShapes() {
        return shapes.size();
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    private void notifyAllObservers() {
        for (Observer o: observers)
            o.update();
    }

    public SelectionHandler getSelection() {
        return selectionHandler;
    }

}
