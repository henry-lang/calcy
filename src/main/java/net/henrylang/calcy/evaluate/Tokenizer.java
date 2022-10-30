package net.henrylang.calcy.evaluate;

import java.util.ArrayList;
import java.util.Arrays;

public class Tokenizer {
    private static boolean isNumeric(int value) {
        return (value >= '0' && value <= '9') || value == '.';
    }

    private static boolean isName(int value) {
        return (value >= 'a' && value <= 'z') || value == '|';
    }

    public static ArrayList<Token> tokenize(int[] expression) throws EvaluateException {
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
                        tokens.add(new Token(Token.Type.CARET));
                    } else {
                        tokens.add(new Token(Token.Type.STAR));
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
                case ',':
                {
                    tokens.add(new Token(Token.Type.COMMA));
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
                            throw new EvaluateException("Invalid Num");
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
                        throw new EvaluateException("Bad Token");
                    }
                }
            }
        }

        return tokens;
    }
}
