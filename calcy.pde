import java.util.Arrays;

final int SCREEN_ROWS = 64;
final int SCREEN_COLS = 94;

Screen screen;

int fadeTime = 10;

color[] palette = {color(208, 238, 209), color(77, 76, 49)};
color[] strokePalette = {color(188, 218, 189), color(57, 56, 29)};

int[] input = new int[] {};



void setup() {
  size(565, 384);
  screen = new Screen(64, 94, 248, 7, 5); // I know these are magic numbers but refer to the constructor of the Screen class.
  
  screen.loadGlyphs("glyphs.txt");
}

void draw() {
  screen.fillScreen(false);
  screen.drawGlyphs(input, 1, 1, 1);
  if((frameCount / 30) % 2 == 0) {
    screen.drawGlyph(224, 1, 1 + screen.glyphCols * input.length + input.length);
  }
  screen.blit(this.g); // Blut the screen onto the window surface (this.g)
}

void keyPressed() {
  switch(keyCode) {
    case BACKSPACE: {
      input = Arrays.copyOf(input, input.length - 1);
      break;
    }
    case SHIFT:
    case CONTROL:
    case ALT: break;
    default: {
      input = Arrays.copyOf(input, input.length + 1);
      input[input.length - 1] = key;
      break;
    }
  }
}

float clamp(float val, float min, float max) {
  return Math.max(min, Math.min(max, val));
}
