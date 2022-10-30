package net.henrylang.calcy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class GlyphSet {
    private final boolean[][][] glyphs = new boolean[CalcySpec.NUM_GLYPHS][CalcySpec.GLYPH_ROWS][CalcySpec.GLYPH_COLS];

    public void load(String resourceName) {
        try {
            var path = new File(Objects.requireNonNull(this.getClass().getResource(resourceName)).getFile()).getPath();
            var lines = Files.readAllLines(Path.of(path));
            for(var i = 0; i < CalcySpec.NUM_GLYPHS; i++) {
                for(var j = 0; j < CalcySpec.GLYPH_ROWS; j++) {
                    for(var k = 0; k < CalcySpec.GLYPH_COLS; k++) {
                        this.glyphs[i][j][k] = lines.get(i * (CalcySpec.GLYPH_ROWS + 1) + j).charAt(k) == 'x';
                    }
                }
            }
        } catch(IOException e) {
            System.exit(1);
        }
    }

    public boolean[][] get(int id) {
        return this.glyphs[id];
    }
}
