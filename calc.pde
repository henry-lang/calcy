boolean[][] vram; // Changes which will be blitted to the screen
boolean[][] lastFrame; // What's actually shown. Useful for calcuating diff between frames for fade.
long[][] frameWhenSet;
int fadeTime = 10;
color[] palette = {color(208, 238, 209), color(77, 76, 49)};

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
      if(vram[j][i] != lastFrame[j][i]) {
        frameWhenSet[j][i] = frameCount;
        lastFrame[j][i] = vram[j][i];
      }
      
      color current = palette[vram[j][i] ? 1 : 0];
      color currentStroke = color(red(current) - 20, green(current) - 20, blue(current) - 20);
      color other = palette[vram[j][i] ? 0 : 1];
      color otherStroke = color(red(other) - 20, green(other) - 20, blue(other) - 20);
      float lerpFactor = clamp((frameCount - frameWhenSet[j][i]) / (float) fadeTime, 0.0, 1.0);
      
      fill(lerpColor(other, current, lerpFactor));
      stroke(lerpColor(otherStroke, currentStroke, lerpFactor));
      rect(i * 6, j * 6, 6, 6);
    }
  }
}

void setup() {
  size(565, 384);
  loadGlyphs();
  vram = new boolean[64][94];
  lastFrame = new boolean[64][94];
  frameWhenSet = new long[64][94];
  
  for(int j = 0; j < 64; j++) {
    for(int i = 0; i < 94; i++) {
      frameWhenSet[j][i] = -fadeTime;
    }
  }
}

void draw() {;
  fillScreen(false);
  /*drawGlyph(frameCount / 20 % 10, 5, 5);*/
  for(var i = 0; i < 94; i++) {
    setPixel((int) (sin(i / 10.0) * map(frameCount % 60, 0, 60, 0, 30) + 31.0), i, true);
  }
  blit();
}

float clamp(float val, float min, float max) {
  return Math.max(min, Math.min(max, val));
}
