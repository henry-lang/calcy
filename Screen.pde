class Screen {
  public final int rows;
  public final int cols;
  public final int numGlyphs;
  public final int glyphRows;
  public final int glyphCols;
  
  private boolean[][][] glyphs;
  private boolean[][] vram;
  private float[][] amount; // Amount of darkness at each cell (LCD fade effect)

  public Screen(int rows, int cols, int numGlyphs, int glyphRows, int glyphCols) {
    this.rows = rows;
    this.cols = cols;
    this.numGlyphs = numGlyphs;
    this.glyphRows = glyphRows;
    this.glyphCols = glyphCols;
    
    this.glyphs = new boolean[numGlyphs][glyphRows][glyphCols];
    this.vram = new boolean[rows][cols];
    this.amount = new float[rows][cols];
  }
  
  public void setPixel(int row, int col, boolean val) {
    if(row < 0 || row >= this.rows || col < 0 || col >= this.cols) return;
    this.vram[row][col] = val;
  }
  
  public void loadGlyphs(String fileName) {
    var lines = loadStrings(fileName);
    for(var i = 0; i < this.numGlyphs; i++) {
      for(var j = 0; j < this.glyphRows; j++) {
        for(var k = 0; k < this.glyphCols; k++) {
          this.glyphs[i][j][k] = lines[i * (this.glyphRows + 1) + j].charAt(k) == 'x';
        }
      }
    }
  }
  
  public void fillScreen(boolean val) {
    for(var j = 0; j < this.rows; j++) {
      for(var i = 0; i < this.cols; i++) {
        this.setPixel(j, i, val);
      }
    }
  }
  
  public void drawGlyph(int glyph, int row, int col) {
    for(var j = 0; j < this.glyphRows; j++) {
      for(var i = 0; i < this.glyphCols; i++) {
        this.setPixel(row + j, col + i, this.glyphs[glyph][j][i]);
      }
    }
  }
  
  public void drawGlyphs(int[] glyphs, int row, int col, int spacing) {
    for(var i = 0; i < glyphs.length; i++) {
      this.drawGlyph(glyphs[i], row, col + glyphCols * i + spacing * i);
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
    for(var j = 0; j < SCREEN_ROWS; j++) {
      for(var i = 0; i < SCREEN_COLS; i++) {
        if(vram[j][i]) {
          amount[j][i] = clamp(amount[j][i] + (1.0 / fadeTime), 0.0, 1.0);
        } else {
          amount[j][i] = clamp(amount[j][i] - (1.0 / fadeTime), 0.0, 1.0);
        }
      
        graphics.fill(lerpColor(palette[0], palette[1], amount[j][i]));
        graphics.stroke(lerpColor(strokePalette[0], strokePalette[1], amount[j][i]));
        graphics.rect(i * 6, j * 6, 6, 6);
      }
    }
  }
}
