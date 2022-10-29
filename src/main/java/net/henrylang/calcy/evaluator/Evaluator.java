package net.henrylang.calcy.evaluator;

import java.util.ArrayList;
import java.util.Arrays;

public class Evaluator {
    public static class Exception extends java.lang.Exception {
            private final int[] glyphs;

            public Exception(int[] glyphs) {
                this.glyphs = glyphs;
            }

            public int[] getGlyphs() {
                return this.glyphs;
            }
    }

    private final Environment env;

    public Evaluator(Environment env) {
        this.env = env;
    }

    private static boolean isNumeric(int value) {
        return (value >= '0' && value <= '9') || value == '.';
    }

    private static boolean isName(int value) {
        return (value >= 'a' && value <= 'z') || value == '|';
    }

    public ArrayList<Token> tokenize(int[] expression) throws Exception {
        var tokens = new ArrayList<Token>();

        for (var i = 0; i < expression.length; ++i) {
            var c = expression[i];
            switch(c) {
                case '+':
                {
                    tokens.add(new Token(Token.Type.PLUS));
                    break;
                }
                case '-':
                {
                    tokens.add(new Token(Token.Type.MINUS));
                    break;
                }
                case '*':
                {
                    if (i + 1 < expression.length && expression[i + 1] == '*') {
                        ++i;
                    }
                    break;
                }
                case '/':
                {
                    tokens.add(new Token(Token.Type.SLASH));
                    break;
                }
                case '^':
                {
                    tokens.add(new Token(Token.Type.CARET));
                    break;
                }
                case '(':
                {
                    tokens.add(new Token(Token.Type.OPEN_PAREN));
                    break;
                }
                case ')':
                {
                    tokens.add(new Token(Token.Type.CLOSE_PAREN));
                    break;
                }

                default:
                {
                    if (isNumeric(c)) {
                        var startIdx = i;
                        while (i + 1 < expression.length && isNumeric(expression[i + 1])) {
                            ++i;
                        }
                        var sub = Arrays.copyOfRange(expression, startIdx, i + 1);
                        var chars = new char[sub.length];
                        for (var j = 0; j < sub.length; j++) {
                            chars[j] = (char) sub[j];
                        }

                        try {
                            tokens.add(new Token(Double.parseDouble(String.valueOf(chars))));
                        }
                        catch(NumberFormatException e) {
                            throw new Exception(new int[]{'I', 'n', 'v', 'a', 'l', 'i', 'd', ' ', 'N', 'u', 'm'});
                        }
                    } else if (isName(c)) {
                        var startIdx = i;
                        while (i + 1 < expression.length && isName(expression[i + 1])) {
                            ++i;
                        }
                        var sub = Arrays.copyOfRange(expression, startIdx, i + 1);
                        var chars = new char[sub.length];
                        for (var j = 0; j < sub.length; j++) {
                            chars[j] = (char) sub[j];
                        }

                        tokens.add(new Token(String.valueOf(chars)));
                    } else {
                        throw new Exception(new int[]{'B', 'a', 'd', ' ', 'T', 'o', 'k', 'e', 'n'});
                    }
                }
            }
        }

        return tokens;
    }

    // This function returns glyphs to show on the screen as a response.
    public int[] evaluate(int[] expression) {
        try {
            var tokens = this.tokenize(expression);
            var parser = new Parser(tokens, this.env);
            var result = parser.getExpression().eval(this.env);
            var chars = String.format("%.17g", result).replaceFirst("\\.?0+(e|$)", "$1").toCharArray();
            var intVersion = new int[chars.length];
            for(int i = 0; i < chars.length; i++) {
                intVersion[i] = chars[i];
            }
            return intVersion;
        } catch(Exception e) {
            return e.getGlyphs();
        }
    }
}
