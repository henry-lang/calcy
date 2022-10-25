static enum TokenType {
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

static class Token {
  final TokenType type;
  final Double num;
  final String name;

  public Token(TokenType type) {
    this.type = type;
    this.num = null;
    this.name = null;
  }

  public Token(double num) {
    this.type = TokenType.NUM;
    this.num = num;
    this.name = null;
  }

  public Token(String name) {
    this.type = TokenType.NAME;
    this.num = null;
    this.name = name;
  }
}

static abstract class Node {
} // Syntax tree node

static class NumberNode extends Node {
  public double value;

  public NumberNode(double value) {
    this.value = value;
  }
}

static enum UnaryOp {
  NEGATE
}

static class UnaryNode extends Node {
  public UnaryOp op;
  public Node operand;

  public UnaryNode(UnaryOp op, Node operand) {
    this.op = op;
    this.operand = operand;
  }
}

static enum BinaryOp {
  ADD,
  SUBTRACT,
  MULTIPLY,
  DIVIDE,
  POWER
}

static class BinaryNode extends Node {
  public BinaryOp op;
  public Node lhs;
  public Node rhs;

  public BinaryNode(BinaryOp op, Node lhs, Node rhs) {
    this.op = op;
    this.lhs = lhs;
    this.rhs = rhs;
  }
}

static class ParserException extends Exception {
  public ParserException(String message) {
    super(message);
  }
}

static class Parser {
  private int pos;
  private ArrayList<Token> tokens;
  private Environment env;

  public Parser(ArrayList<Token> tokens, Environment env) {
    this.pos = 0;
    this.tokens = tokens;
    this.env = env;
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

  private void consume(TokenType type) throws ParserException {
    if (this.token().type == type) {
      this.advance();
    } else {
      throw new ParserException("Bad Token");
    }
  }

  private Node getFactor() throws ParserException {
    var token = this.token();

    switch(token.type) {
      case NUM: {
        this.advance();
        return new NumberNode(token.num);
      }

      case NAME: {
        this.advance();
        var next = this.token();
        switch(next.type) {
          default: {
            return new NumberNode(env.getVar(token.name));
          }
        }
      }

      case MINUS: {
        this.advance();
        var factor = this.getFactor();

        return new UnaryNode(UnaryOp.NEGATE, factor);
      }

      default: {
        throw new ParserException("Expected Expr");
      }
    }
  }

  private Node getPower() throws ParserException {
    var factor = this.getFactor();

    while (this.token().type == TokenType.CARET) {
      this.advance();
      factor = new BinaryNode(BinaryOp.POWER, factor, this.getFactor());
    }

    return factor;
  }

  private Node getTerm() throws ParserException {
    var power = this.getPower();

    outer:
    while(true) {
      switch(this.token().type) {
        case STAR: {
          this.advance();
          power = new BinaryNode(BinaryOp.MULTIPLY, power, this.getPower());
          
          break;
        }
        
        case SLASH: {
          this.advance();
          power = new BinaryNode(BinaryOp.DIVIDE, power, this.getPower());
          
          break;
        }
        
        default: {
          break outer;
        }
      }
    }
    
    return power;
  }

  private Node getExpression() throws ParserException {
    var term = this.getTerm();
    
    outer:
    while(true) {
      switch(this.token().type) {
        case PLUS: {
          this.advance();
          term = new BinaryNode(BinaryOp.ADD, term, this.getPower());
          
          break;
        }
        
        case MINUS: {
          this.advance();
          term = new BinaryNode(BinaryOp.SUBTRACT, term, this.getPower());
          
          break;
        }
        
        default: {
          break outer;
        }
      }
    }
    
    return term;
  }
}

static class Evaluator {
  private Environment env;

  public Evaluator(Environment env) {
    this.env = env;
  }

  private static boolean isNumeric(int value) {
    return (value >= '0' && value <= '9') || value == '.';
  }

  private static boolean isName(int value) {
    return (value >= 'a' && value <= 'z') || value == '|';
  }

  public ArrayList<Token> tokenize(int[] expression) {
    var tokens = new ArrayList<Token>();

    for (var i = 0; i < expression.length; ++i) {
      var c = expression[i];
      switch(c) {
        case '+': { tokens.add(new Token(TokenType.PLUS)); break; }
        case '-': { tokens.add(new Token(TokenType.MINUS)); break; }
        case '*': {
          if (i + 1 < expression.length && expression[i + 1] == '*') {
            ++i;
          }
          break;
        }
        case '/': { tokens.add(new Token(TokenType.SLASH)); break; }
        case '^': { tokens.add(new Token(TokenType.CARET)); break; }
        case '(': { tokens.add(new Token(TokenType.OPEN_PAREN)); break; }
        case ')': { tokens.add(new Token(TokenType.CLOSE_PAREN)); break; }
  
        default: {
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
              tokens.add(new Token(Double.valueOf(String.valueOf(chars))));
            }
            catch(NumberFormatException e) {
              println("input not valid");
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
            println(String.valueOf(chars));
            tokens.add(new Token(String.valueOf(chars)));
          }
  
          break;
        }
      }
    }

    return tokens;
  }

  // This function returns glyphs to show on the screen as a reponse.
  public int[] evaluate(String expression) {
    return new int[] {};
  }
}
