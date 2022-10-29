package net.henrylang.calcy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GlyphSet {
    private boolean[][][] glyphs = new boolean[CalcySpec.NUM_GLYPHS][CalcySpec.GLYPH_ROWS][CalcySpec.GLYPH_COLS];

    public void load(String resourceName) {
        Charset charset = StandardCharsets.UTF_8;
        try {
            List<String> lines = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getResource(resourceName)).getFile()));
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
