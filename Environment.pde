import java.util.function.Function;

class Environment {
  private HashMap<String, Double> vars;
  private HashMap<String, Function<ArrayList<Double>, Double>> funcs;
  
  public Environment() {
    this.vars = new HashMap<>();
    this.funcs = new HashMap<>();
  }
  
  public double getVar(String name) {
    return this.vars.get(name);
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
