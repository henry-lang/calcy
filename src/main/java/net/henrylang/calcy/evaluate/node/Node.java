package net.henrylang.calcy.evaluate.node;

import net.henrylang.calcy.evaluate.Environment;
import net.henrylang.calcy.evaluate.EvaluateException;

public interface Node {
    double eval(Environment env) throws EvaluateException;
}