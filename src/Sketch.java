import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sketch extends PApplet {
    private static final Random random = new Random();
    private Line[] randomizedLines = new Line[0];

    @Override
    public void settings() {
        size(400, 400);
    }

    @Override
    public void setup() {
        stroke(255);
        int numLines = random.nextInt(1, 5);
        // Generate random lines
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < numLines; i++) {
            PVector start = new PVector(random.nextInt(width), random.nextInt(height));
            PVector end = new PVector(random.nextInt(width), random.nextInt(height));
            lines.add(new Line(start, end));
        }
        // Randomize lines
        randomizedLines = lines.toArray(new Line[0]);
    }

    @Override
    public void draw() {
        background(0);
        for (var line : randomizedLines) line.draw(this);
        var mouse = new PVector(mouseX, mouseY);
        // draw evenly spaced lines from the mouse to the edge of the screen
        var limit = 100;
        float slice = (float) (Constants.TWO_PI / limit);
        for (var i = 0; i < limit; i++) {
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
