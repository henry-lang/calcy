package net.henrylang.calcy.evaluate.node;

import net.henrylang.calcy.evaluate.Environment;

public class NumberNode implements Node {
    public double value;

    public NumberNode(double value) {
        this.value = value;
    }

    @Override
    public double eval(Environment env) {
        return this.value;
    }
}
