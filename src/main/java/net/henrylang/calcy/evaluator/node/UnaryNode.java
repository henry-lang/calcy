package net.henrylang.calcy.evaluator.node;

import net.henrylang.calcy.evaluator.Environment;

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
    public double eval(Environment env) {
        switch(this.type) {
            case NEGATE: {
                return -this.operand.eval(env);
            }
        }
        return 0;
    }
}