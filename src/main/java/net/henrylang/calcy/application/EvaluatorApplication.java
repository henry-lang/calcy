package net.henrylang.calcy.application;

import net.henrylang.calcy.Calcy;
import net.henrylang.calcy.Screen;
import net.henrylang.calcy.Utils;
import net.henrylang.calcy.evaluate.*;

import static net.henrylang.calcy.CalcySpec.*;

import java.util.ArrayList;
import java.util.Arrays;

public class EvaluatorApplication extends InputApplication {
    int[] response = new int[] {};

    @Override
    public UpdateResult update(long frameCount, Screen screen) {
        super.update(frameCount, screen);

        screen.fill(false);
        screen.drawGlyphs(this.input, 1, 1, 1);
        screen.drawGlyphs(this.response, 2 + GLYPH_ROWS, 1, 1);
        if((this.cursorFrames / 30) % 2 == 0) {
            screen.drawGlyph(224, 1, 1 + GLYPH_COLS * this.input.length + this.input.length);
        }
        return UpdateResult.OK;
    }

    @Override
    protected boolean submitInput() {
        if(this.input.length == 0) {
            return false;
        }

        if(Arrays.equals(this.input, Utils.stringToGlyphs("graph"))) { // I know this is "inefficient" but idc
            Calcy.getInstance().runApplication(new GrapherApplication());
            return true;
        }

        try {
            var tokens = Tokenizer.tokenize(this.input);
            var node = new Parser(tokens).getExpression();
            this.response = Utils.doubleToGlyphs(node.eval(Environment.DEFAULT));

        } catch (EvaluateException e) {
            this.response = e.getGlyphs();
        }

        return true;
    }
}