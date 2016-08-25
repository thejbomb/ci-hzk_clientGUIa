package tool;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

import java.util.LinkedList;

/**
 * Created by quang on 08/24/16.
 */
public class DrawingPad extends Pane {
    private LinkedList<Polyline> lines;

    public void startDrawing() {
        lines = new LinkedList<>();
        Polyline line = new Polyline();
        this.setOnMouseDragged(e -> {
            if (isInside(e.getX(), e.getY())) {
                line.getPoints().addAll(e.getX(), e.getY());
                this.getChildren().remove(line);
                this.getChildren().add(line);
            }
        });

        this.setOnMouseReleased(e -> {
            Polyline l = new Polyline();
            l.getPoints().addAll(line.getPoints());
            lines.add(l);
        });

        this.setOnMousePressed(e -> {
            if (!line.getPoints().isEmpty()) {

                line.getPoints().clear();
            }
            for (Polyline l : lines) {
                this.getChildren().remove(l);
                this.getChildren().add(l);
            }
        });
    }

    private boolean isInside(double x, double y) {
        boolean isInside = false;
        Point2D points = localToScene(x, y);
        x = points.getX();
        y = points.getY();
        if (x > localToScene(getBoundsInLocal()).getMinX() && x < localToScene(getBoundsInLocal()).getMaxX() && y > localToScene(getBoundsInLocal()).getMinY() && y < localToScene(
                getBoundsInLocal()
        ).getMaxY())
            isInside = true;
        return isInside;
    }

    public LinkedList<Polyline> getLines() {
        return lines;
    }

    public LinkedList<Polyline> getSmaller(double scale) {
        LinkedList<Polyline> result = new LinkedList<>();
        for (Polyline pl : lines) {
            Polyline line = new Polyline();
            for (Double db : pl.getPoints()) {
                line.getPoints().add(db / scale);
            }
            result.add(line);
        }

        return result;
    }
}
