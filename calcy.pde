import java.util.Arrays;
import java.util.Stack;

Stack<Application> applications; // Queue of applications to run. The first element is the one currently running.
Screen screen;

void setup() {
  size(564, 384);
  
  applications = new Stack<>();
  applications.push(new BlankApplication()); // Just in case we have no app to run
  applications.push(new EvaluatorApplication());
  applications.push(new StartupApplication());
  
  screen = new Screen(64, 94, 248, 7, 5, 10, 
    new color[] {color(208, 238, 209), color(77, 76, 49)},
    new color[] {color(188, 218, 189), color(57, 56, 29)}
  ); // I know these are magic numbers but refer to the constructor of the Screen class.
  screen.loadGlyphs("glyphs.txt");
}

void draw() {
  while(applications.peek().update(frameCount, screen) == ApplicationUpdateResult.QUIT) {
    applications.pop();
  }
  
  screen.blit(this.g); // Blit the screen onto the window surface (this.g)
}

void keyPressed() {
  applications.peek().handleInput(key, keyCode);
}

float clamp(float val, float min, float max) {
  return Math.max(min, Math.min(max, val));
}
