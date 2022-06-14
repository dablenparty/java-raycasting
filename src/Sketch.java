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
        size(700, 700);
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
        // create lines along the edge of the screen
        lines.add(new Line(new PVector(0, 0), new PVector(width, 0)));
        lines.add(new Line(new PVector(width, 0), new PVector(width, height)));
        lines.add(new Line(new PVector(width, height), new PVector(0, height)));
        lines.add(new Line(new PVector(0, height), new PVector(0, 0)));
        // Randomize lines
        randomizedLines = lines.toArray(new Line[0]);
    }

    @Override
    public void draw() {
        background(0);
        var mouse = new PVector(mouseX, mouseY);
        List<Line> rays = new ArrayList<>();
        // draw rays only to the vertices of each boundary line
        for (var line : randomizedLines) {
            var toStart = new Line(mouse, line.getStart());
            var toEnd = new Line(mouse, line.getEnd());
            rays.add(toStart);
            rays.add(toEnd);
            line.draw(this);
        }
        // check for intersections
        rays.forEach(ray -> {
            for (var boundary : randomizedLines) {
                var intersection = ray.intersection(boundary);
                if (intersection.isEmpty()) continue;
                // if the distance from start to intersection is less than the distance from start to end,
                // set the end of the ray to the intersection
                PVector intersectionPoint = intersection.get();
                PVector start = ray.getStart();
                var distanceToIntersect = start.dist(intersectionPoint);
                var distanceToEnd = start.dist(ray.getEnd());
                if (distanceToIntersect < distanceToEnd) {
                    ray.setEnd(intersectionPoint);
                }
            }
            ray.draw(this);
        });
    }

    /**
     * Runs the sketch in Processing.
     */
    public void run() {
        runSketch();
    }
}
