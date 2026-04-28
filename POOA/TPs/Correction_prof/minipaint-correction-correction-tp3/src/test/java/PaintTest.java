import drawing.IShape;
import drawing.PaintApplication;
import drawing.ShapeAdapter;
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

    @Override
    public void start(Stage stage) {
        try {
            app = new PaintApplication();
            app.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Shape create_shape(String shapeType) {
        int x = 20 + get_nb_shapes() * 150;
        int y = 20 + (int) (200 * Math.random());
        int w = 50 + (int) (70 * Math.random());
        clickOn(shapeType);
        moveTo(app.getDrawingPane())
                .moveBy(-app.WIDTH/2 + x, -app.HEIGHT/2 + y)
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
        // given:
        clickOn("Circle");
        moveBy(60,60);

        // when:
        drag().dropBy(30,30);
        //press(MouseButton.PRIMARY); moveBy(30,30); release(MouseButton.PRIMARY);

        // then:
        Iterator it = app.getDrawingPane().getChildren().iterator();
        assertTrue(it.next() instanceof Ellipse);
        assertFalse(it.hasNext());
    }

    @Test
    public void should_draw_rectangle() {
        // given:
        clickOn("Rectangle");
        moveBy(0,60);

        // when:
        drag().dropBy(70,40);

        // then:
        Iterator it = app.getDrawingPane().getChildren().iterator();
        assertTrue(it.next() instanceof Rectangle);
        assertFalse(it.hasNext());
    }

    @Test
    public void should_draw_triangle() {
        // given:
        clickOn("Triangle");
        moveBy(0,60);

        // when:
        drag().dropBy(70,40);

        // then:
        Iterator it = app.getDrawingPane().getChildren().iterator();
        Object s = it.next();
        assertTrue(s instanceof Polygon);
        assertEquals(6, ((Polygon)s).getPoints().size());
        assertFalse(it.hasNext());
    }

    @Test
    public void should_clear() {
        // given:
        clickOn("Rectangle");
        moveBy(30,60).drag().dropBy(70,40);
        clickOn("Circle");
        moveBy(-30,160).drag().dropBy(70,40);

        // when:
        clickOn("Clear");

        // then:
        assertFalse(app.getDrawingPane().iterator().hasNext());
    }

    @Test
    public void test_statutbar() {
        // given:
        clickOn("Rectangle");
        moveBy(30,60).drag().dropBy(70,40);
        assertEquals("1", app.getStatutBar().getLabel().getText().split(" ")[0]);
        clickOn("Circle");
        moveBy(-30,160).drag().dropBy(70,40);
        assertEquals("2", app.getStatutBar().getLabel().getText().split(" ")[0]);
        clickOn("Clear");
        assertEquals("0", app.getStatutBar().getLabel().getText().split(" ")[0]);
    }

    @Test
    public void should_select() {
        Shape s1 = create_shape("Rectangle");
        Shape s2 = create_shape("Circle");
        assertEquals(2, get_nb_shapes());
        assertEquals(0, get_nb_selected());
        clickOn(s1);
        assertEquals(1, get_nb_selected());
        press(KeyCode.SHIFT).moveTo(s2).clickOn();
        release(KeyCode.SHIFT);
        assertEquals(2, get_nb_selected());
    }
}