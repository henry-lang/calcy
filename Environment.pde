import java.util.function.Function;

static class Environment {
  public static final Environment DEFAULT = new Environment()
    .withVar("pi", Math.PI)
    .withVar("tau", 2 * Math.PI)
    .withFunc("sqrt", (args) -> Math.sqrt(args.get(0)));
  
  private HashMap<String, Double> vars;
  private HashMap<String, Function<ArrayList<Double>, Double>> funcs;
  
  private Environment() {
    this.vars = new HashMap<>();
    this.funcs = new HashMap<>();
  }
  
  public double getVar(String name) {
    return this.vars.get(name);
  }
  
  public Environment withVar(String name, double val) {
    this.insertVar(name, val);
    return this;
  }
    
  public Environment withFunc(String name, Function<ArrayList<Double>, Double> func) {
    this.insertFunc(name, func);
    return this;
  }
  
  public void insertVar(String name, double val) {
    this.vars.put(name, val);
  }
  
  public Function<ArrayList<Double>, Double> getFunc(String name) {
    return this.funcs.get(name);
  }
  
  public void insertFunc(String name, Function<ArrayList<Double>, Double> func) {
    this.funcs.put(name, func);
  }
}
