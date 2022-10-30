package net.henrylang.calcy.application;

import static net.henrylang.calcy.CalcySpec.*;
import static processing.core.PApplet.map;

import net.henrylang.calcy.Screen;

public class GrapherApplication extends InputApplication {
    int[] points;

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

        for(int x = -1; x < SCREEN_COLS + 1; x++) {
            double realX = map(x, -1, SCREEN_COLS + 1, -5, 5);
            double sq = realX * realX;
            double screenY = (sq * (SCREEN_ROWS - 2 - GLYPH_ROWS)) / 2 + (SCREEN_ROWS - 2 - GLYPH_ROWS) / 2;
            double realXLast = map(x - 1, -1, SCREEN_COLS + 1, -5, 5);
            double sqLast = realXLast * realXLast;
            double screenYLast = (sqLast * (SCREEN_ROWS - 2 - GLYPH_ROWS)) / 2 + (SCREEN_ROWS - 2 - GLYPH_ROWS) / 2;
            screen.drawLine((int) (SCREEN_ROWS - 2 - GLYPH_ROWS) - (int) screenYLast, x - 1, (int) (SCREEN_ROWS - 2 - GLYPH_ROWS) - (int) screenY, x);
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
}
