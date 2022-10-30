package net.henrylang.calcy.evaluate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Environment {
    public static final Environment DEFAULT = new Environment()
            .withVar("pi", Math.PI)
            .withVar("tau", 2 * Math.PI)
            .withVar("e", Math.E)
            .withFunc("sqrt", 1, args -> Math.sqrt(args.get(0)))
            .withFunc("min", 2, args -> Math.min(args.get(0), args.get(1)))
            .withFunc("max", 2, args -> Math.max(args.get(0), args.get(1)));

    private final HashMap<String, Double> vars = new HashMap<>();
    private final HashMap<String, Func> funcs = new HashMap<>();

    public Double getVar(String name) {
        return this.vars.getOrDefault(name, null);
    }
    public Func getFunc(String name) {
        return this.funcs.getOrDefault(name, null);
    }

    public Environment withVar(String name, double val) {
        this.insertVar(name, val);
        return this;
    }

    public Environment withFunc(String name, int arity, Function<List<Double>, Double> func) {
        this.insertFunc(name, arity, func);
        return this;
    }

    public void insertVar(String name, double val) {
        this.vars.put(name, val);
    }

    public void insertFunc(String name, int arity, Function<List<Double>, Double> func) {
        this.funcs.put(name, new Func(arity, func));
    }

    public double runFunc(String name, ArrayList<Double> args) {
        var func = this.funcs.get(name);
        if(func.arity != args.size()) {
            return Double.NaN;
        }

        return func.run(args);
    }
}
