package net.henrylang.calcy.evaluator;

import java.util.List;
import java.util.function.Function;

public class Func {
    public final int arity; // How many args
    public final Function<List<Double>, Double> func;

    public Func(int arity, Function<List<Double>, Double> func) {
        this.arity = arity;
        this.func = func;
    }
}
