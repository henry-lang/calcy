package net.henrylang.calcy.evaluate.node;

import net.henrylang.calcy.evaluate.Environment;
import net.henrylang.calcy.evaluate.EvaluateException;

public class BinaryNode implements Node {
    public enum Type {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        POWER
    }

    public Type type;
    public Node lhs;
    public Node rhs;

    public BinaryNode(Type type, Node lhs, Node rhs) {
        this.type = type;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public double eval(Environment env) throws EvaluateException {
        switch(this.type) {
            case ADD: return this.lhs.eval(env) + this.rhs.eval(env);
            case SUBTRACT: return this.lhs.eval(env) - this.rhs.eval(env);
            case MULTIPLY: return this.lhs.eval(env) * this.rhs.eval(env);
            case DIVIDE: return this.lhs.eval(env) / this.rhs.eval(env);
            case POWER: return Math.pow(this.lhs.eval(env), this.rhs.eval(env));
        }
        return 0;
    }
}