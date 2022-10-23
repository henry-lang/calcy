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

class StartupApplication implements Application {
  // private int[] input;
  
  public StartupApplication() {
    //this.input = new int[] {};
  }
  
  @Override
  ApplicationUpdateResult update(long frameCount) {
    return frameCount > 30 ? ApplicationUpdateResult.QUIT : ApplicationUpdateResult.OK;
  }
  
  @Override
  void draw(Screen screen) {
    screen.fillScreen(false);
    screen.drawGlyphs(new int[] {'c', 'a', 'l', 'c', 'y'}, 1, 1, 1);
    screen.drawGlyphs(new int[] {'b', 'y', ' ', 'h', 'e', 'n', 'r', 'y', 'l', 'a', 'n', 'g'}, screen.glyphRows + 3, 1, 1);
  }
  
  @Override
  void handleInput(char key, int keyCode) {
    /*switch(keyCode) {
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
    }*/
  }
}
