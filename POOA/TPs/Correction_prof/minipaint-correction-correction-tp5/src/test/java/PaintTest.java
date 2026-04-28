import drawing.shapes.GroupShape;
import drawing.shapes.IShape;
import drawing.ui.ButtonFactory;
import drawing.ui.PaintApplication;
import drawing.shapes.ShapeAdapter;
import drawing.ui.StatutBar;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Iterator;

import static org.junit.Assert.*;

public class PaintTest extends ApplicationTest {

    PaintApplication app;
    Stage stage;

    @Override
    public void start(Stage stage) {
        try {
            this.stage = stage;
            app = new PaintApplication();
            app.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Shape create_shape(String shapeType) {
//        int x = 20 + get_nb_shapes() * 150;
        int w = 50 + (int) (70 * Math.random());
        int x = 20 + (int) ((stage.getWidth()-w) * Math.random());
        int y = 50 + (int) ((stage.getHeight()-w) * Math.random());
        clickOn("#"+shapeType);
        moveTo(stage.getX()+x, stage.getY()+y)
                .drag().dropBy(w,w);
        return (Shape) app.getDrawingPane().getChildren().get(app.getDrawingPane().getChildren().size()-1);
    }

    private int get_nb_shapes() {
        int nb = 0;
        for (IShape s: app.getDrawingPane())
            nb++;
        return nb;
    }

    private int get_nb_selected() {
        int nb = 0;
        for (IShape s: app.getDrawingPane().getSelection())
            nb++;
        return nb;
    }

    private String[] get_statutbar_vals() {
        StatutBar sbar = null;
        for (Node node : stage.getScene().getRoot().getChildrenUnmodifiable()) {
            if (node instanceof StatutBar) {
                sbar = (StatutBar) node;
                break;
            }
        }

        // Replacing every non-digit number
        // with a space(" ")
        String str = sbar.getLabel().getText()
                .replaceAll("[^0-9]", " ")
                .trim()
                .replaceAll(" +", " ");
        return str.split(" ");
    }

    @Test
    public void should_draw_circle_programmatically() {
        interact(() -> {
                    app.getDrawingPane().addShape(new ShapeAdapter(new Ellipse(20, 20, 30, 30)));
                });
        Iterator it = app.getDrawingPane().getChildren().iterator();
        assertTrue(it.next() instanceof Ellipse);
        assertFalse(it.hasNext());
    }

    @Test
    public void should_draw_circle() {
        Shape s = create_shape(ButtonFactory.CIRCLE);

        Iterator it = app.getDrawingPane().getChildren().iterator();
        assertTrue(it.next() instanceof Ellipse);
        assertFalse(it.hasNext());
    }

    @Test
    public void should_draw_rectangle() {
        Shape s = create_shape(ButtonFactory.RECTANGLE);

        Iterator it = app.getDrawingPane().getChildren().iterator();
        assertTrue(it.next() instanceof Rectangle);
        assertFalse(it.hasNext());
    }

    @Test
    public void should_draw_triangle() {
        Shape sh = create_shape(ButtonFactory.TRIANGLE);

        Iterator it = app.getDrawingPane().getChildren().iterator();
        Object s = it.next();
        assertTrue(s instanceof Polygon);
        assertEquals(6, ((Polygon)s).getPoints().size());
        assertFalse(it.hasNext());
    }

    @Test
    public void should_clear() {
        Shape s1 = create_shape(ButtonFactory.CIRCLE);
        Shape s2 = create_shape(ButtonFactory.RECTANGLE);

        clickOn("#"+ButtonFactory.CLEAR);

        assertFalse(app.getDrawingPane().iterator().hasNext());
    }

    @Test
    public void test_statutbar() {
        // given:
        Shape s1 = create_shape(ButtonFactory.RECTANGLE);
        assertEquals("1", get_statutbar_vals()[0]);
        Shape s2 = create_shape(ButtonFactory.TRIANGLE);
        assertEquals("2", get_statutbar_vals()[0]);
        clickOn("#"+ButtonFactory.CLEAR);
        assertEquals("0", get_statutbar_vals()[0]);
    }

    @Test
    public void should_select() {
        Shape s1 = create_shape(ButtonFactory.RECTANGLE);
        Shape s2 = create_shape(ButtonFactory.TRIANGLE);
        assertEquals(2, get_nb_shapes());
        assertEquals(0, get_nb_selected());
        assertEquals("0", get_statutbar_vals()[1]);
        clickOn(s1);
        assertEquals(1, get_nb_selected());
        assertEquals("1", get_statutbar_vals()[1]);
        press(KeyCode.SHIFT).moveTo(s2).clickOn();
        release(KeyCode.SHIFT);
        assertEquals(2, get_nb_selected());
        assertEquals("2", get_statutbar_vals()[1]);
    }

    @Test
    public void should_delete_selection(){
        Shape s1 = create_shape(ButtonFactory.CIRCLE);
        Shape s2 = create_shape(ButtonFactory.TRIANGLE);
        clickOn(s1);
        press(KeyCode.SHIFT).moveTo(s2).clickOn();
        release(KeyCode.SHIFT);
        assertEquals(2, get_nb_shapes());
        assertEquals(2, get_nb_selected());
        clickOn("#"+ButtonFactory.DELETE);
        assertEquals(0, get_nb_shapes());
        assertEquals(0, get_nb_selected());
    }

    @Test
    public void should_group(){
        Shape s1 = create_shape(ButtonFactory.RECTANGLE);
        Shape s2 = create_shape(ButtonFactory.CIRCLE);
        clickOn(s1);
        press(KeyCode.SHIFT).moveTo(s2).clickOn();
        release(KeyCode.SHIFT);
        clickOn("#"+ButtonFactory.GROUP);
        Iterator<IShape> it = app.getDrawingPane().iterator();
        assertTrue(it.next() instanceof GroupShape);
        assertFalse(it.hasNext());
    }

    @Test
    public void should_ungroup(){
        Shape s1 = create_shape(ButtonFactory.RECTANGLE);
        Shape s2 = create_shape(ButtonFactory.CIRCLE);
        assertEquals(2, get_nb_shapes());
        clickOn(s1);
        press(KeyCode.SHIFT).moveTo(s2).clickOn();
        release(KeyCode.SHIFT);
        clickOn("#"+ButtonFactory.GROUP);
        assertEquals(1, get_nb_shapes());
        clickOn(s1);
        clickOn("#"+ButtonFactory.UNGROUP);
        assertEquals(2, get_nb_shapes());
    }

    @Test
    public void shoud_bring_to_front() {
        Shape s1 = create_shape(ButtonFactory.RECTANGLE);
        Shape s2 = create_shape(ButtonFactory.CIRCLE);
        clickOn(s1);
        clickOn("#"+ButtonFactory.TOFRONT);
        assertEquals(s1, app.getDrawingPane().getChildren().get(1));
        clickOn(s2);
        clickOn("#"+ButtonFactory.TOFRONT);
        assertEquals(s2, app.getDrawingPane().getChildren().get(1));
    }
}