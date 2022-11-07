package net.henrylang.calcy.evaluate.node;

import net.henrylang.calcy.evaluate.Environment;
import net.henrylang.calcy.evaluate.EvaluateException;

public class VarNode implements Node {
    private final String name;

    public VarNode(String name) {
        this.name = name;
    }

    @Override
    public double eval(Environment env) throws EvaluateException {
        var value = env.getVar(this.name);

        if(value == null) {
            throw new EvaluateException("Invalid Var");
        }

        return value;
    }
}
