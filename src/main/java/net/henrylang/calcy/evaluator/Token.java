package net.henrylang.calcy.evaluator;

public class Token {
    public enum Type {
        NUM,
        NAME,

        PLUS,
        MINUS,
        STAR,
        SLASH,
        CARET,

        OPEN_PAREN,
        CLOSE_PAREN,

        END
    }

    public final Type type;
    public final double num;
    public final String name;

    public Token(Type type) {
        this.type = type;
        this.num = 0;
        this.name = null;
    }

    public Token(double num) {
        this.type = Type.NUM;
        this.num = num;
        this.name = null;
    }

    public Token(String name) {
        this.type = Type.NAME;
        this.num = 0;
        this.name = name;
    }
}