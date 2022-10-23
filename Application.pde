enum ApplicationUpdateResult {
  OK,
  QUIT,
}

interface Application {
  default ApplicationUpdateResult update(long frameCount) {
    return ApplicationUpdateResult.OK;
  }
  
  default void draw(Screen screen) {
    screen.fillScreen(false);
  }
  
  default void handleInput(char key, int keyCode) {
    
  }
}

class BlankApplication implements Application {}

static class StartupApplication implements Application {
  private static int[] calcy = {'c', 'a', 'l', 'c', 'y'};
  private static int[] author = {'b', 'y', ' ', 'h', 'e', 'n', 'r', 'y', 'l', 'a', 'n', 'g'};
  
  @Override
  ApplicationUpdateResult update(long frameCount) {
    return frameCount > 30 ? ApplicationUpdateResult.QUIT : ApplicationUpdateResult.OK;
  }
  
  @Override
  void draw(Screen screen) {
    screen.fillScreen(false);
    screen.drawGlyphs(calcy, 1, 1, 1);
    screen.drawGlyphs(author, screen.glyphRows + 3, 1, 1);
  }
}

class EvaluatorApplication implements Application {
  private int[] input;
  private long frames;
  
  public EvaluatorApplication() {
    this.input = new int[] {};
    this.frames = 0;
  }
  
  @Override
  ApplicationUpdateResult update(long frameCount) {
    this.frames++;
    return ApplicationUpdateResult.OK;
  }
  
  @Override
  void draw(Screen screen) {
    screen.fillScreen(false);
    screen.drawGlyphs(this.input, 1, 1, 1);
    if((this.frames / 30) % 2 == 0) {
      screen.drawGlyph(224, 1, 1 + screen.glyphCols * this.input.length + this.input.length);
    }
  }
  
  @Override
  void handleInput(char key, int keyCode) {
    this.frames = 0;
    switch(keyCode) {
      case BACKSPACE: {
        this.input = Arrays.copyOf(this.input, this.input.length - 1);
        break;
      }
      case SHIFT:
      case CONTROL:
      case ALT: break;
      default: {
        this.input = Arrays.copyOf(this.input, this.input.length + 1);
        this.input[this.input.length - 1] = key;
        break;
      }
    }
  }
}
