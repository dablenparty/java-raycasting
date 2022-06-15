import processing.core.PApplet;
import processing.core.PVector;

import java.util.*;

public class Sketch extends PApplet {
    private static final Random random = new Random();
    private static final float ANGLE_OFFSET = 0.00001f;
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
            var startRotatedClockwise = toStart.rotate(ANGLE_OFFSET);
            var startRotatedCounterClockwise = toStart.rotate(-ANGLE_OFFSET);
            var toEnd = new Line(mouse, line.getEnd());
            var endRotatedClockwise = toEnd.rotate(ANGLE_OFFSET);
            var endRotatedCounterClockwise = toEnd.rotate(-ANGLE_OFFSET);
            rays.add(toStart);
            rays.add(startRotatedClockwise);
            rays.add(startRotatedCounterClockwise);
            rays.add(toEnd);
            rays.add(endRotatedClockwise);
            rays.add(endRotatedCounterClockwise);
            line.draw(this, false);
        }
        // check for intersections
        rays.forEach(ray -> {
            Set<PVector> intersections = new HashSet<>();
            for (var boundary : randomizedLines) {
                var intersection = ray.intersection(boundary);
                intersection.ifPresent(intersections::add);
            }
            // get the closest intersection
            intersections.stream()
                    .min(Comparator.comparingDouble(intersection -> PVector.dist(mouse, intersection)))
                    .ifPresent(ray::setEnd);
        });
        // sort rays by angle
        rays.sort(Comparator.comparingDouble(line -> PVector.sub(line.getEnd(), line.getStart()).heading()));
        // group the rays into pairs, matching the start and end points
        Set<List<Line>> pairs = new HashSet<>();
        int size = rays.size();
        for (int i = 0; i < size; i++) {
            int before = i - 1 < 0 ? size - 1 : i - 1;
            pairs.add(Arrays.asList(rays.get(i), rays.get(before)));
        }
        // draw a triangle between each pair of rays
        pairs.forEach(pair -> {
            var line1 = pair.get(0);
            var line2 = pair.get(1);
            PVector start = line1.getStart();
            PVector end1 = line1.getEnd();
            PVector end2 = line2.getEnd();
            triangle(start.x, start.y, end1.x, end1.y, end2.x, end2.y);
        });
    }

    /**
     * Runs the sketch in Processing.
     */
    public void run() {
        runSketch();
    }
}
