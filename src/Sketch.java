import processing.core.PApplet;

public class Sketch extends PApplet {
    @Override
    public void settings() {
        size(400, 400);
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
