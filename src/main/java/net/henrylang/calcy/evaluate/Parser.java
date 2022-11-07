package net.henrylang.calcy.evaluate;

import net.henrylang.calcy.evaluate.node.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private int pos;
    private final ArrayList<Token> tokens;

    public Parser(ArrayList<Token> tokens) {
        this.pos = 0;
        this.tokens = tokens;
    }

    private Token token() {
        return this.tokens.get(this.pos);
    }

    private Token peek() {
        if (this.pos + 1 < this.tokens.size()) {
            return this.tokens.get(this.pos + 1);
        }

        return null;
    }

    private void advance() {
        if (this.pos + 1 < this.tokens.size()) {
            this.pos++;
        }
    }

    private void consume(Token.Type type) throws EvaluateException {
        if (this.token().type == type) {
            this.advance();
        } else {
            throw new EvaluateException("Bad Token");
        }
    }

    private Node getFactor() throws EvaluateException {
        var token = this.token();

        switch (token.type) {
            case NUM -> {
                this.advance();
                return new NumberNode(token.num);
            }
            case NAME -> {
                var current = this.token();
                this.advance();
                if (this.token().type == Token.Type.OPEN_PAREN) {
                    this.advance();
                    var args = new ArrayList<Node>();
                    outer:
                    while (true) {
                        switch (this.token().type) {
                            case CLOSE_PAREN -> {
                                break outer;
                            }
                            case COMMA -> this.advance();
                            default -> args.add(this.getExpression());
                        }
                    }
                    return new FuncCallNode(current.name, args);
                } else {
                    return new VarNode(current.name);
                }
            }
            case MINUS -> {
                this.advance();
                var factor = this.getFactor();

                return new UnaryNode(UnaryNode.Type.NEGATE, factor);
            }
            default -> throw new EvaluateException("Expected Expr");
        }
    }

    private Node getPower() throws EvaluateException {
        var factor = this.getFactor();

        while (this.token().type == Token.Type.CARET) {
            this.advance();
            factor = new BinaryNode(BinaryNode.Type.POWER, factor, this.getFactor());
        }

        return factor;
    }

    private Node getTerm() throws EvaluateException {
        var power = this.getPower();

        outer:
        while (true) {
            switch (this.token().type) {
                case STAR -> {
                    this.advance();
                    power = new BinaryNode(BinaryNode.Type.MULTIPLY, power, this.getPower());

                }
                case SLASH -> {
                    this.advance();
                    power = new BinaryNode(BinaryNode.Type.DIVIDE, power, this.getPower());
                }
                default -> {
                    break outer;
                }
            }
        }

        return power;
    }

    public Node getExpression() throws EvaluateException {
        var term = this.getTerm();

        outer:
        while(true) {
            switch (this.token().type) {
                case PLUS -> {
                    this.advance();
                    term = new BinaryNode(BinaryNode.Type.ADD, term, this.getTerm());
                }
                case MINUS -> {
                    this.advance();
                    term = new BinaryNode(BinaryNode.Type.SUBTRACT, term, this.getTerm());
                }
                default -> {
                    break outer;
                }
            }
        }

        return term;
    }
}
