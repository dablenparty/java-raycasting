import processing.core.PApplet;
import processing.core.PVector;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        for (var line : randomizedLines) line.draw(this);
        var mouse = new PVector(mouseX, mouseY);
        // draw evenly spaced lines from the mouse to the edge of the screen
        var limit = 50;
        float slice = (float) (Constants.TWO_PI / limit);
        List<Line> rays = new ArrayList<>();
        for (var i = 0; i < limit; i++) {
            var start = new PVector(mouse.x, mouse.y);
            // end of the line should extend to the edge of the screen
            var end = new PVector(mouse.x + cos(i * slice) * width * width,
                    mouse.y + sin(i * slice) * height * height);
            var line = new Line(start, end);
            for (var boundaryLine : randomizedLines) {
                Optional<PVector> intersection = line.intersection(boundaryLine);
                if (intersection.isEmpty()) continue;
                PVector intersectionPoint = intersection.get();
                // check if the distance from the line start to the intersection point is less than the distance from the line start to the line end
                if (start.dist(intersectionPoint) < start.dist(end)) {
                    end = intersectionPoint;
                }
            }
            line.setEnd(end);
            rays.add(line);
        }
        // group into pairs of lines
        Map<Integer, List<Line>> pairs = new HashMap<>();
        int size = rays.size();
        for (int i = 0; i < size; i++) {
            int before = i - 1 < 0 ? size - 1 : i - 1;
            pairs.put(i, Arrays.asList(rays.get(before), rays.get(i)));
        }
        // draw pairs of lines
        for (var pair : pairs.values()) {
            var line1 = pair.get(0);
            var line2 = pair.get(1);
            PVector start = line1.getStart();
            PVector end1 = line1.getEnd();
            PVector end2 = line2.getEnd();
            triangle(start.x, start.y, end1.x, end1.y, end2.x, end2.y);
        }
    }

    /**
     * Runs the sketch in Processing.
     */
    public void run() {
        runSketch();
    }
}
