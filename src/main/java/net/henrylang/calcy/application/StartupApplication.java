package net.henrylang.calcy.application;

import net.henrylang.calcy.CalcySpec;
import net.henrylang.calcy.Screen;

public class StartupApplication implements Application {
    private static final int[] calcy = {'c', 'a', 'l', 'c', 'y'};
    private static final int[] author = {'b', 'y', ' ', 'h', 'e', 'n', 'r', 'y', 'l', 'a', 'n', 'g'};

    @Override
    public ApplicationUpdateResult update(long frameCount, Screen screen) {
        screen.fill(false);
        screen.drawGlyphs(calcy, 1, 1, 1);
        screen.drawGlyphs(author, CalcySpec.GLYPH_ROWS + 3, 1, 1);
        return frameCount > 30 ? ApplicationUpdateResult.QUIT : ApplicationUpdateResult.OK;
    }
}
