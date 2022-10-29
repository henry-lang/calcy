package net.henrylang.calcy.evaluator.node;

import net.henrylang.calcy.evaluator.Environment;

public interface Node {
    double eval(Environment env);
}