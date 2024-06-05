package net.henrylang.calcy.application;

import net.henrylang.calcy.CalcySpec;
import net.henrylang.calcy.Screen;

public class StartupApplication implements Application {
    private static final int[] calcy = {'c', 'a', 'l', 'c', 'y'};
    private static final int[] author = {'b', 'y', ' ', 'H', 'e', 'n', 'r', 'y', ' ', 'L'};

    @Override
    public UpdateResult update(long frameCount, Screen screen) {
        screen.fill(false);
        screen.drawGlyphs(calcy, 1, 1, 1);
        screen.drawGlyphs(author, CalcySpec.GLYPH_ROWS + 3, 1, 1);
        return frameCount > 30 ? UpdateResult.QUIT : UpdateResult.OK;
    }
}
