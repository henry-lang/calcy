import java.util.Arrays;

enum ApplicationUpdateResult {
  OK,
  QUIT,
}

interface Application {
  public default ApplicationUpdateResult update(long frameCount, Screen screen) {
    screen.fill(true);
    return ApplicationUpdateResult.OK;
  }
  
  public default void handleInput(char key, int keyCode) {}
}

class BlankApplication implements Application {}

static class StartupApplication implements Application {
  private static int[] calcy = {'c', 'a', 'l', 'c', 'y'};
  private static int[] author = {'b', 'y', ' ', 'h', 'e', 'n', 'r', 'y', 'l', 'a', 'n', 'g'};
  
  @Override
  ApplicationUpdateResult update(long frameCount, Screen screen) {
    screen.fill(false);
    screen.drawGlyphs(calcy, 1, 1, 1);
    screen.drawGlyphs(author, screen.glyphRows + 3, 1, 1);
    return frameCount > 30 ? ApplicationUpdateResult.QUIT : ApplicationUpdateResult.OK;
  }
}

abstract class InputApplication implements Application {
  protected int[] input;
  protected long cursorFrames; // Used for blinking the cursor
  
  public InputApplication() {
    this.input = new int[] {};
    this.cursorFrames = 0;
  }
  
  @Override
  public ApplicationUpdateResult update(long frameCount, Screen screen) {
    cursorFrames += 1;
    return ApplicationUpdateResult.OK;
  }
  
  protected abstract boolean submitInput(); // Returns whether to clear input array afterwards
  
  @Override
  public void handleInput(char key, int keyCode) {
    this.cursorFrames = 0;
    switch(keyCode) {
      case ENTER: {
        if(this.submitInput()) this.input = new int[] {};
        break;
      }
      case BACKSPACE: {
        this.input = Arrays.copyOf(this.input, this.input.length - 1);
        break;
      }
      case SHIFT:
      case CONTROL:
      case 32: // Space bar
      case ALT: break;
      default: {
        this.input = Arrays.copyOf(this.input, this.input.length + 1);
        this.input[this.input.length - 1] = key;
        break;
      }
    }
  }
}

class EvaluatorApplication extends InputApplication {
  @Override
  public ApplicationUpdateResult update(long frameCount, Screen screen) {
    super.update(frameCount, screen);
    
    screen.fill(false);
    screen.drawGlyphs(this.input, 1, 1, 1);
    if((this.cursorFrames / 30) % 2 == 0) {
      screen.drawGlyph(224, 1, 1 + screen.glyphCols * this.input.length + this.input.length);
    }
    return ApplicationUpdateResult.OK;
  }
  
  @Override
  protected boolean submitInput() {
    if(Arrays.equals(this.input, new int[] {'g', 'r', 'a', 'p', 'h'})) { // I know this is "inefficient" but idc
      applications.add(new GrapherApplication());
      return true;
    }
    
    println(evaluator.tokenize(input).toString());
    
    return true;
  }
}

class GrapherApplication extends InputApplication {
  int[] points;
  
  public GrapherApplication() {
    this.points = new int[screen.cols];
  }
  
  @Override
  public ApplicationUpdateResult update(long frameCount, Screen screen) {
    super.update(frameCount, screen);
    
    screen.fill(false);
    screen.drawLine(2 + screen.glyphRows, 0, 2 + screen.glyphRows, screen.cols - 1);
    screen.drawLine(2 + screen.glyphRows, 0, screen.rows - 1, 0);
    screen.drawLine(screen.rows - 1, 0, screen.rows - 1, screen.cols - 1);
    screen.drawLine(2 + screen.glyphRows, screen.cols - 1, screen.rows - 1, screen.cols - 1);
    screen.drawLine((screen.rows - 2 - screen.glyphRows) / 2 + 2 + screen.glyphRows, 0, (screen.rows - 2 - screen.glyphRows) / 2 + 2 + screen.glyphRows, screen.cols - 1);
    
    for(int x = -1; x < screen.cols + 1; x++) {
      double realX = map(x, -1, screen.cols + 1, -5, 5);
      double sq = realX * realX;
      double screenY = (sq / mouseX * (screen.rows - 2 - screen.glyphRows)) / 2 + (screen.rows - 2 - screen.glyphRows) / 2;
      double realXLast = map(x - 1, -1, screen.cols + 1, -5, 5);
      double sqLast = realXLast * realXLast;
      double screenYLast = (sqLast / mouseX * (screen.rows - 2 - screen.glyphRows)) / 2 + (screen.rows - 2 - screen.glyphRows) / 2;
      screen.drawLine((int) (screen.rows - 2 - screen.glyphRows) - (int) screenYLast, x - 1, (int) (screen.rows - 2 - screen.glyphRows) - (int) screenY, x);
    }
    
    screen.drawGlyphs(this.input, 1, 1, 1);
    if((this.cursorFrames / 30) % 2 == 0) {
      screen.drawGlyph(224, 1, 1 + screen.glyphCols * this.input.length + this.input.length);
    }
    return ApplicationUpdateResult.OK;
  }
  
  @Override
  protected boolean submitInput() {
    return false;
  }
}
