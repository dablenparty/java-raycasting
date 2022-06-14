import processing.core.PApplet;
import processing.core.PVector;

public record Line(PVector start, PVector end) {
    public void draw(PApplet app) {
        app.line(start.x, start.y, end.x, end.y);
    }
}
