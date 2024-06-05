package net.henrylang.calcy.application;

import static net.henrylang.calcy.CalcySpec.*;
import static processing.core.PApplet.map;

import net.henrylang.calcy.Screen;
import net.henrylang.calcy.evaluate.Environment;
import net.henrylang.calcy.evaluate.Parser;
import net.henrylang.calcy.evaluate.Tokenizer;
import net.henrylang.calcy.evaluate.node.Node;

public class GrapherApplication extends InputApplication {
    int[] points;
    Environment env = Environment.defaultEnv();
    Node expression = null;

    public void compile() {
        try {
            var tokens = Tokenizer.tokenize(this.input);
            expression = new Parser(tokens).getExpression();
        } catch(Exception ignored) {}
    }

    public GrapherApplication() {
        this.points = new int[SCREEN_COLS - 2];
    }

    @Override
    public UpdateResult update(long frameCount, Screen screen) {
        super.update(frameCount, screen);

        screen.fill(false);
        screen.drawLine(2 + GLYPH_ROWS, 0, 2 + GLYPH_ROWS, SCREEN_COLS - 1);
        screen.drawLine(2 + GLYPH_ROWS, 0, SCREEN_ROWS - 1, 0);
        screen.drawLine(SCREEN_ROWS - 1, 0, SCREEN_ROWS - 1, SCREEN_COLS - 1);
        screen.drawLine(2 + GLYPH_ROWS, SCREEN_COLS - 1, SCREEN_ROWS - 1, SCREEN_COLS - 1);
        screen.drawLine((SCREEN_ROWS - 2 - GLYPH_ROWS) / 2 + 2 + GLYPH_ROWS, 0, (SCREEN_ROWS - 2 - GLYPH_ROWS) / 2 + 2 + GLYPH_ROWS, SCREEN_COLS - 1);

        if(input.length > 0 && expression != null) {
            for (int x = -1; x < SCREEN_COLS + 1; x++) {
                try {
                    double realX = map(x, -1, SCREEN_COLS + 1, -5, 5);
                    env.insertVar("x", realX);
                    double evalThis = expression.eval(env);
                    double screenY = (evalThis * (SCREEN_ROWS - 2 - GLYPH_ROWS)) / 2 + (double) (SCREEN_ROWS - 2 - GLYPH_ROWS) / 2;
                    double realXLast = map(x - 1, -1, SCREEN_COLS + 1, -5, 5);
                    env.insertVar("x", realXLast);
                    double evalLast = expression.eval(env);
                    double screenYLast = (evalLast * (SCREEN_ROWS - 2 - GLYPH_ROWS)) / 2 + (double) (SCREEN_ROWS - 2 - GLYPH_ROWS) / 2;
                    screen.drawLine((int) (SCREEN_ROWS - 2 - GLYPH_ROWS) - (int) screenYLast, x - 1, (int) (SCREEN_ROWS - 2 - GLYPH_ROWS) - (int) screenY, x);
                } catch(Exception ignored) {}
            }
        }

        screen.drawGlyphs(this.input, 1, 1, 1);
        if((this.cursorFrames / 30) % 2 == 0) {
            screen.drawGlyph(224, 1, 1 + GLYPH_COLS * this.input.length + this.input.length);
        }
        return UpdateResult.OK;
    }

    @Override
    protected boolean submitInput() {
        return false;
    }

    @Override public void handleInput(char key, int keyCode) {
        super.handleInput(key, keyCode);
        compile();
    }
}
