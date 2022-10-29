package net.henrylang.calcy.evaluator.node;

import net.henrylang.calcy.evaluator.Environment;

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
    public double eval(Environment env) {
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