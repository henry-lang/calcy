package net.henrylang.calcy.evaluate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Environment {
    public static Environment defaultEnv() {
        return new Environment()
                .withVar("pi", Math.PI)
                .withVar("tau", 2 * Math.PI)
                .withVar("e", Math.E)
                .withFunc("sqrt", 1, args -> Math.sqrt(args.get(0)))
                .withFunc("min", 2, args -> Math.min(args.get(0), args.get(1)))
                .withFunc("max", 2, args -> Math.max(args.get(0), args.get(1)))
                .withFunc("sin", 1, args -> Math.sin(args.get(0)))
                .withFunc("cos", 1, args -> Math.cos(args.get(0)))
                .withFunc("tan", 1, args -> Math.tan(args.get(0)))
                .withFunc("asin", 1, args -> Math.asin(args.get(0)))
                .withFunc("acos", 1, args -> Math.acos(args.get(0)))
                .withFunc("atan", 1, args -> Math.atan(args.get(0)))
                .withFunc("atan2", 2, args -> Math.atan2(args.get(0), args.get(1)))
                .withFunc("sec", 1, args -> 1 / Math.cos(args.get(0)))
                .withFunc("csc", 1, args -> 1 / Math.sin(args.get(0)))
                .withFunc("cot", 1, args -> 1 / Math.tan(args.get(0)))
                .withFunc("arcsec", 1, args -> arcsec(args.get(0)))
                .withFunc("arccsc", 1, args -> arccsc(args.get(0)))
                .withFunc("arccot", 1, args -> arccot(args.get(0)));
    }

    private static double arcsec(double x) {
        return Math.acos(1 / x);
    }

    private static double arccsc(double x) {
        return Math.asin(1 / x);
    }

    private static double arccot(double x) {
        return Math.PI / 2 - Math.atan(x);
    }

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
}
