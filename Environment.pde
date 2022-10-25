import java.util.function.Function;

static class Func {
  public final int arity; // How many args
  public final Function<ArrayList<Double>, Double> func;
  
  public Func(int arity, Function<ArrayList<Double>, Double> func) {
    this.arity = arity;
    this.func = func;
  }
}

static class Environment {
  public static final Environment DEFAULT = new Environment()
    .withVar("pi", Math.PI)
    .withVar("tau", 2 * Math.PI)
    .withFunc("sqrt", 1, args -> Math.sqrt(args.get(0)))
    .withFunc("min", 2, args -> Math.min(args.get(0), args.get(1)))
    .withFunc("max", 2, args -> Math.max(args.get(0), args.get(1)));
  
  private HashMap<String, Double> vars;
  private HashMap<String, Func> funcs;
  
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
    
  public Environment withFunc(String name, int arity, Function<ArrayList<Double>, Double> func) {
    this.insertFunc(name, arity, func);
    return this;
  }
  
  public void insertVar(String name, double val) {
    this.vars.put(name, val);
  }
  
  public void insertFunc(String name, int arity, Function<ArrayList<Double>, Double> func) {
    this.funcs.put(name, new Func(arity, func));
  }
  
  public double runFunc(String name, ArrayList<Double> args) {
    var func = this.funcs.get(name);
    if(func.arity != args.size()) {
      return Double.NaN;
    }
    return func.func.apply(args);
  }
}
