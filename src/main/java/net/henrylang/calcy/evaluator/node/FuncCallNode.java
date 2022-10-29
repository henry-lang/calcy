package net.henrylang.calcy.evaluator.node;

import net.henrylang.calcy.evaluator.Environment;
import net.henrylang.calcy.evaluator.Func;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FuncCallNode implements Node {
    public Func func;
    public ArrayList<Node> args;

    public FuncCallNode(Func func, ArrayList<Node> args) {
        this.func = func;
        this.args = args;
    }

    @Override
    public double eval(Environment env) {
        if(func.arity != args.size()) {
            return Double.NaN;
        }
        var numArgs = args.stream().map(node -> node.eval(env)).collect(Collectors.toList());

        return func.func.apply(numArgs);
    }
}