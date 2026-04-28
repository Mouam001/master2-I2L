package drawing;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class TriangleButtonHandler extends ShapeButtonHandler {

    public TriangleButtonHandler(DrawingPane drawingPane) {
        super(drawingPane);
    }

    @Override
    protected Shape createShape() {
        double x1 = Math.min(originX, destinationX);
        double y1 = Math.min(originY, destinationY);
        double x2 = Math.max(originX, destinationX);
        double y2 = Math.min(originY, destinationY);
        double x3 = (x1 + x2) / 2;
        double y3 = Math.max(originY, destinationY);
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(x1, y1, x2, y2, x3, y3);
        polygon.getStyleClass().add("triangle");
        return polygon;
    }
}
