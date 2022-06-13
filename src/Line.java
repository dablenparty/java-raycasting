import processing.core.PApplet;

public record Line(Point start, Point end) {
    public void draw(PApplet app) {
        app.line(start.x(), start.y(), end.x(), end.y());
    }
}
