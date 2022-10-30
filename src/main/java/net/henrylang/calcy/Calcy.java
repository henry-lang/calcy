package net.henrylang.calcy;

import net.henrylang.calcy.application.*;
import processing.core.PApplet;

import java.util.Stack;

import static net.henrylang.calcy.CalcySpec.*;

public class Calcy extends PApplet {
    private static Calcy instance;
    private final GlyphSet glyphSet = new GlyphSet();

    private final Screen screen = new Screen(
            this.glyphSet,
            10,
            new int[] {color(208, 238, 209), color(77, 76, 49)},
            new int[] {color(188, 218, 189), color(57, 56, 29)}
    );

    private final Stack<Application> applications = new Stack<>();

    public static Calcy getInstance() {
         return instance;
    }

    //
    // public Screen getScreen() {
    //     return this.screen;
    // }
    //
    // public GlyphSet getGlyphSet() {
    //     return this.glyphSet;
    // }

    public static void main(String[] args) {
        PApplet.main(Calcy.class.getName());
    }


    public Application getRunningApplication() {
        return this.applications.peek();
    }

    public void quitApplication() {
        if(!this.applications.empty()) {
            this.applications.pop();
        }
    }

    public void runApplication(Application app) {
        this.applications.push(app);
    }

    public Calcy() {
        this.glyphSet.load("/glyphs.txt");

        this.runApplication(new BlankApplication());
        this.runApplication(new EvaluatorApplication());
        this.runApplication(new StartupApplication());
    }

    @Override
    public void settings() {
        instance = this;
        this.size(SCREEN_COLS * PIXEL_SIZE, SCREEN_ROWS * PIXEL_SIZE);
    }

    @Override
    public void draw() {
        while(this.getRunningApplication().update(this.frameCount, this.screen) == UpdateResult.QUIT) {
            this.quitApplication();
        }

        screen.blit(this.g); // Blit the screen onto the window surface (this.g)
    }

    @Override
    public void keyPressed() {
        this.getRunningApplication().handleInput(key, keyCode);
    }
}