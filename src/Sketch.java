import processing.core.PApplet;
import processing.core.PVector;

public class Sketch extends PApplet {
    @Override
    public void settings() {
        size(400, 400);
    }

    @Override
    public void draw() {
        background(0);
        stroke(255);
        var mouse = new PVector(mouseX, mouseY);
        // draw evenly spaced lines from the mouse to the edge of the screen
        int limit = 100;
        float slice = (float) (Constants.TWO_PI / limit);
        for (int i = 0; i < limit; i++) {
            var start = new PVector(mouse.x, mouse.y);
            // end of the line should extend to the edge of the screen
            var end = new PVector(mouse.x + cos(i * slice) * width * width,
                                  mouse.y + sin(i * slice) * height * height);
            var line = new Line(start, end);
            line.draw(this);
        }
    }

    /**
     * Runs the sketch in Processing.
     *
     * @return itself for chaining
     */
    public Sketch run() {
        runSketch();
        return this;
    }
}
