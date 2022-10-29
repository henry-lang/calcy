package net.henrylang.calcy.application;

import net.henrylang.calcy.Screen;
import net.henrylang.calcy.evaluator.Environment;
import net.henrylang.calcy.evaluator.Evaluator;
import static net.henrylang.calcy.CalcySpec.*;

import java.util.Arrays;

public class EvaluatorApplication extends InputApplication {
    int[] response = new int[] {};

    @Override
    public ApplicationUpdateResult update(long frameCount, Screen screen) {
        super.update(frameCount, screen);

        screen.fill(false);
        screen.drawGlyphs(this.input, 1, 1, 1);
        screen.drawGlyphs(this.response, 2 + GLYPH_ROWS, 1, 1);
        if((this.cursorFrames / 30) % 2 == 0) {
            screen.drawGlyph(224, 1, 1 + GLYPH_COLS * this.input.length + this.input.length);
        }
        return ApplicationUpdateResult.OK;
    }

    @Override
    protected boolean submitInput() {
        if(this.input.length == 0) {
            return false;
        }

        if(Arrays.equals(this.input, new int[] {'g', 'r', 'a', 'p', 'h'})) { // I know this is "inefficient" but idc
            //applications.add(new GrapherApplication());
            return true;
        }

        this.response = new Evaluator(Environment.DEFAULT).evaluate(this.input);

        return true;
    }
}