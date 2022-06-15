import processing.core.PApplet;
import processing.core.PVector;

import java.util.Objects;
import java.util.Optional;

public final class Line {
    private final PVector start;
    private PVector end;

    public Line(PVector start, PVector end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Draw the line.
     *
     * @param app The applet to draw on.
     */
    public void draw(PApplet app) {
        app.line(start.x, start.y, end.x, end.y);
    }

    /**
     * Returns the intersection point of this line and the given line, if it exists.
     * <p>
     * More information can be found on the Wikipedia page for
     * <a href="https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection">Line-line intersection</a>
     *
     * @param other the other line
     * @return the intersection point, if it exists
     */
    public Optional<PVector> intersection(Line other) {
        PVector p1 = start;
        PVector p2 = end;
        PVector p3 = other.start;
        PVector p4 = other.end;
        float denom = (p4.y - p3.y) * (p2.x - p1.x) - (p4.x - p3.x) * (p2.y - p1.y);
        // parallel lines
        if (denom == 0) return Optional.empty();
        float t = ((p2.x - p1.x) * (p1.y - p3.y) - (p2.y - p1.y) * (p1.x - p3.x)) / denom;
        float u = ((p4.x - p3.x) * (p1.y - p3.y) - (p4.y - p3.y) * (p1.x - p3.x)) / denom;
        // usually, u is bound between 0 and 1. by removing the upper 1 bound, we can also check for intersections
        // outside the line segments in the positive direction. this covers the case where one line does not reach the
        // other, but would intersect the other line if it did.
        if (u < 0 || t < 0 || t > 1) return Optional.empty();
        return Optional.of(new PVector(p1.x + u * (p2.x - p1.x), p1.y + u * (p2.y - p1.y)));
    }

    /**
     * Rotates the line about its start point by the given angle (in radians).
     * <p>
     * This line object will remain unchanged, but a new line will be returned.
     *
     * @param angle the angle to rotate the line by
     * @return the rotated line
     */
    public Line rotate(float angle) {
        PVector start = this.start.copy();
        // translate to origin, rotate, translate back
        PVector end = this.end.copy().sub(start).rotate(angle).add(start);
        return new Line(start, end);
    }

    /**
     * Returns the heading of this line (in radians).
     *
     * @return the heading
     */
    public float heading() {
        return PVector.sub(end, start).heading();
    }

    public PVector getStart() {
        return start;
    }

    public PVector getEnd() {
        return end;
    }

    public void setEnd(PVector end) {
        this.end = end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Line) obj;
        return Objects.equals(this.start, that.start) &&
                Objects.equals(this.end, that.end);
    }

    @Override
    public String toString() {
        return "Line[" +
                "start=" + start + ", " +
                "end=" + end + ']';
    }

}
