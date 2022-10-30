package net.henrylang.calcy.evaluate.node;

import net.henrylang.calcy.evaluate.Environment;
import net.henrylang.calcy.evaluate.EvaluateException;

import java.util.ArrayList;
import java.util.List;

public class FuncCallNode implements Node {

    public String name;
    public List<Node> args;

    public FuncCallNode(String name, List<Node> args) {
        this.name = name;
        this.args = args;
    }

    @Override
    public double eval(Environment env) throws EvaluateException {
        var func = env.getFunc(name);
        if(func == null) {
            throw new EvaluateException("Invalid Func");
        }

        if(func.arity != args.size()) {
            throw new EvaluateException("Invalid Args");
        }

        var numArgs = new ArrayList<Double>();
        for (Node node : args) {
            double eval = node.eval(env);
            numArgs.add(eval);
        }

        return func.run(numArgs);
    }
}