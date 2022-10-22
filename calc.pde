boolean[][] vram; // Changes which will be blitted to the screen
float[][] amount; // Amount of darkness at each cell
int fadeTime = 10;

color[] palette = {color(208, 238, 209), color(77, 76, 49)};
color[] strokePalette = {color(188, 218, 189), color(57, 56, 29)};

final int NUM_GLYPHS = 10;
final int GLYPH_WIDTH = 5;
final int GLYPH_HEIGHT = 7;
boolean[][][] glyphs = new boolean[NUM_GLYPHS][GLYPH_HEIGHT][GLYPH_WIDTH]; // 5x7xNUM_GLYPHS glyph array

void setPixel(int row, int col, boolean val) {
  vram[row][col] = val;
}

void loadGlyphs() {
  var lines = loadStrings("glyphs.txt");
  for(var i = 0; i < NUM_GLYPHS; i++) {
    for(var j = 0; j < GLYPH_HEIGHT; j++) {
      for(var k = 0; k < GLYPH_WIDTH; k++) {
        glyphs[i][j][k] = lines[i * (GLYPH_HEIGHT + 1) + j].charAt(k) == 'x';
      }
    }
  }
}

void fillScreen(boolean val) {
  for(var j = 0; j < 64; j++) {
    for(var i = 0; i < 94; i++) {
      setPixel(j, i, val);
    }
  }
}

void drawGlyph(int glyph, int row, int col) {
  for(var j = 0; j < 7; j++) {
    for(var i = 0; i < 5; i++) {
      setPixel(row + j, col + i, glyphs[glyph][j][i]);
    }
  }
}

void blit() {
  for(int j = 0; j < 64; j++) {
    for(int i = 0; i < 94; i++) {
      if(vram[j][i]) {
        amount[j][i] = clamp(amount[j][i] + (1.0 / fadeTime), 0.0, 1.0);
      } else {
        amount[j][i] = clamp(amount[j][i] - (1.0 / fadeTime), 0.0, 1.0);
      }
      
      fill(lerpColor(palette[0], palette[1], amount[j][i]));
      stroke(lerpColor(strokePalette[0], strokePalette[1], amount[j][i]));
      rect(i * 6, j * 6, 6, 6);
    }
  }
}

void setup() {
  size(565, 384);
  loadGlyphs();
  vram = new boolean[64][94];
  amount = new float[64][94];
  
  for(int j = 0; j < 64; j++) {
    for(int i = 0; i < 94; i++) {
      amount[j][i] = 0;
    }
  }
}

void draw() {;
  fillScreen(false);
  drawGlyph(frameCount / 20 % 10, 5, 5);
  blit();
}

float clamp(float val, float min, float max) {
  return Math.max(min, Math.min(max, val));
}
