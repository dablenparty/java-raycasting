import processing.core.PApplet;
import processing.core.PVector;

import java.util.Objects;

public final class Line {
    private PVector start;
    private PVector end;

    public Line(PVector start, PVector end) {
        this.start = start;
        this.end = end;
    }

    public void draw(PApplet app) {
        app.line(start.x, start.y, end.x, end.y);
    }

    public PVector getStart() {
        return start;
    }

    public void setStart(PVector start) {
        this.start = start;
    }

    public PVector getEnd() {
        return end;
    }

    public void setEnd(PVector end) {
        this.end = end;
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
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "Line[" +
                "start=" + start + ", " +
                "end=" + end + ']';
    }

}
