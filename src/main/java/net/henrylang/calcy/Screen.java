package net.henrylang.calcy;

import processing.core.PGraphics;

import static net.henrylang.calcy.CalcySpec.PIXEL_SIZE;

public class Screen {
    public final int fadeTime;

    private final int[] palette;
    private final int[] strokePalette;
    private final GlyphSet glyphSet;

    private final boolean[][] vram = new boolean[CalcySpec.SCREEN_ROWS][CalcySpec.SCREEN_COLS];
    private final float[][] amount = new float[CalcySpec.SCREEN_ROWS][CalcySpec.SCREEN_COLS]; // Amount of darkness at each cell (LCD fade effect)

    public Screen(GlyphSet glyphSet, int fadeTime, int[] palette, int[] strokePalette) {
        this.glyphSet = glyphSet;
        this.fadeTime = fadeTime;

        this.palette = palette;
        this.strokePalette = strokePalette;
    }

    public void setPixel(int row, int col, boolean val) {
        if(row < 0 || row >= CalcySpec.SCREEN_ROWS || col < 0 || col >= CalcySpec.SCREEN_COLS) return;
        this.vram[row][col] = val;
    }

    public void fill(boolean val) {
        for(var j = 0; j < CalcySpec.SCREEN_ROWS; j++) {
            for(var i = 0; i < CalcySpec.SCREEN_COLS; i++) {
                this.setPixel(j, i, val);
            }
        }
    }

    public void drawGlyph(int id, int row, int col) {
        boolean[][] glyph = this.glyphSet.get(id);

        if(glyph == null) {
            return;
        }

        for(var j = 0; j < CalcySpec.GLYPH_ROWS; j++) {
            for(var i = 0; i < CalcySpec.GLYPH_COLS; i++) {
                this.setPixel(row + j, col + i, glyph[j][i]);
            }
        }
    }

    public void drawGlyphs(int[] glyphs, int row, int col, int spacing) {
        for(var i = 0; i < glyphs.length; i++) {
            this.drawGlyph(glyphs[i], row, col + CalcySpec.GLYPH_COLS * i + spacing * i);
        }
    }

    public void drawLine(int y0, int x0, int y1, int x1) {
        int dx = Math.abs(x1 - x0);
        int sx = x0 < x1 ? 1 : -1;
        int dy = -Math.abs(y1 - y0);
        int sy = y0 < y1 ? 1 : -1;
        int error = dx + dy;

        while(true) {
            this.setPixel(y0, x0, true);
            if(x0 == x1 && y0 == y1) break;
            int e2 = 2 * error;
            if(e2 >= dy) {
                if(x0 == x1) break;
                error += dy;
                x0 += sx;
            }
            if(e2 <= dx) {
                if(y0 == y1) break;
                error += dx;
                y0 += sy;
            }
        }
    }

    public void drawRect(int y, int x, int h, int w, boolean val) {
        for(int row = y; row < y + h; row++) {
            for(int col = x; col < x + w; col++) {
                this.setPixel(row, col, val);
            }
        }
    }

    public void blit(PGraphics graphics) {
        for(var j = 0; j < CalcySpec.SCREEN_ROWS; j++) {
            for(var i = 0; i < CalcySpec.SCREEN_COLS; i++) {
                if(this.vram[j][i]) {
                    this.amount[j][i] = Utils.clamp(this.amount[j][i] + (1.0f / this.fadeTime), 0.0f, 1.0f);
                } else {
                    this.amount[j][i] = Utils.clamp(this.amount[j][i] - (1.0f / this.fadeTime), 0.0f, 1.0f);
                }

                graphics.fill(graphics.lerpColor(palette[0], palette[1], amount[j][i]));
                graphics.stroke(graphics.lerpColor(strokePalette[0], strokePalette[1], amount[j][i]));
                graphics.rect(i * PIXEL_SIZE, j * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }
}