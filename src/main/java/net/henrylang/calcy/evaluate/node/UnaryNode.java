package net.henrylang.calcy.evaluate.node;

import net.henrylang.calcy.evaluate.Environment;
import net.henrylang.calcy.evaluate.EvaluateException;

public class UnaryNode implements Node {
    public enum Type {
        NEGATE
    }

    public Type type;
    public Node operand;

    public UnaryNode(Type type, Node operand) {
        this.type = type;
        this.operand = operand;
    }

    @Override
    public double eval(Environment env) throws EvaluateException {
        return switch (this.type) {
            case NEGATE -> -this.operand.eval(env);
        };
    }
}