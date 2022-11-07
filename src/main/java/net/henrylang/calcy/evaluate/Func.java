package net.henrylang.calcy.evaluate;

import java.util.List;
import java.util.function.Function;

public class Func {
    private final int arity; // How many args
    private final Function<List<Double>, Double> func;

    public Func(int arity, Function<List<Double>, Double> func) {
        this.arity = arity;
        this.func = func;
    }

    public double run(List<Double> args) throws EvaluateException {
        if(this.arity != args.size()) {
            throw new EvaluateException("Invalid Args");
        }
        return this.func.apply(args);
    }
}
