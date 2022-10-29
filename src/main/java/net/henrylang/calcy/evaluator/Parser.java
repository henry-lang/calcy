package net.henrylang.calcy.evaluator;

import net.henrylang.calcy.evaluator.node.BinaryNode;
import net.henrylang.calcy.evaluator.node.Node;
import net.henrylang.calcy.evaluator.node.NumberNode;
import net.henrylang.calcy.evaluator.node.UnaryNode;

import java.util.ArrayList;

public class Parser {
    private int pos;
    private final ArrayList<Token> tokens;
    private final Environment env;

    public Parser(ArrayList<Token> tokens, Environment env) {
        this.pos = 0;
        this.tokens = tokens;
        this.env = env;
    }

    private Token token() {
        return this.tokens.get(this.pos);
    }

    /*private Token peek() {
        if (this.pos + 1 < this.tokens.size()) {
            return this.tokens.get(this.pos + 1);
        }

        return null;
    }*/

    private void advance() {
        if (this.pos + 1 < this.tokens.size()) {
            this.pos++;
        }
    }

    /*private void consume(Token.Type type) throws Evaluator.Exception {
        if (this.token().type == type) {
            this.advance();
        } else {
            throw new Evaluator.Exception(new int[] {'B', 'a', 'd', ' ', 'T', 'o', 'k', 'e', 'n'});
        }
    }*/

    private Node getFactor() throws Evaluator.Exception {
        var token = this.token();

        switch(token.type) {
            case NUM:
            {
                this.advance();
                return new NumberNode(token.num);
            }

            case NAME:
            {
                this.advance();
                var next = this.token();
                switch(next.type) {
                    default:
                    {
                        return new NumberNode(env.getVar(token.name));
                    }
                }
            }

            case MINUS:
            {
                this.advance();
                var factor = this.getFactor();

                return new UnaryNode(UnaryNode.Type.NEGATE, factor);
            }

            default:
            {
                throw new Evaluator.Exception(new int [] {'E', 'x', 'p', 'e', 'c', 't', 'e', 'd', ' ', 'E', 'x', 'p', 'r'});
            }
        }
    }

    private Node getPower() throws Evaluator.Exception {
        var factor = this.getFactor();

        while (this.token().type == Token.Type.CARET) {
            this.advance();
            factor = new BinaryNode(BinaryNode.Type.POWER, factor, this.getFactor());
        }

        return factor;
    }

    private Node getTerm() throws Evaluator.Exception {
        var power = this.getPower();

        outer:
        while (true) {
            switch(this.token().type) {
                case STAR:
                {
                    this.advance();
                    power = new BinaryNode(BinaryNode.Type.MULTIPLY, power, this.getPower());

                    break;
                }

                case SLASH:
                {
                    this.advance();
                    power = new BinaryNode(BinaryNode.Type.DIVIDE, power, this.getPower());

                    break;
                }

                default:
                {
                    break outer;
                }
            }
        }

        return power;
    }

    public Node getExpression() throws Evaluator.Exception {
        var term = this.getTerm();

        outer:
        while (true) {
            switch(this.token().type) {
                case PLUS:
                {
                    this.advance();
                    term = new BinaryNode(BinaryNode.Type.ADD, term, this.getPower());

                    break;
                }

                case MINUS:
                {
                    this.advance();
                    term = new BinaryNode(BinaryNode.Type.SUBTRACT, term, this.getPower());

                    break;
                }

                default:
                {
                    break outer;
                }
            }
        }

        return term;
    }
}
