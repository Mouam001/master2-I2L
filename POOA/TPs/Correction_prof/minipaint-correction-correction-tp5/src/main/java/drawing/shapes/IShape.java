package drawing.shapes;

import javafx.scene.layout.Pane;

public interface IShape {
    boolean isSelected();
    void setSelected(boolean selected);
    boolean isOn(double x, double y);
    boolean intersect(double x, double y, double width, double height);
    void offset(double x, double y);
    void addShapeToPane(Pane pane);
    void removeShapeFromPane(Pane pane);
}
