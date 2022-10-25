static enum TokenType {
  NUM,
  NAME,
  
  PLUS,
  MINUS,
  STAR,
  SLASH,
  
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
    
    for(var i = 0; i < expression.length; ++i) {
      var c = expression[i];
      switch(c) {
        case '+': {tokens.add(new Token(TokenType.PLUS)); println("ahoy"); break;}
        case '-': {tokens.add(new Token(TokenType.MINUS)); break;}
        case '*': {
          if(i + 1 < expression.length && expression[i + 1] == '*') {
            ++i;
          }
          break;
        }
        case '/': {tokens.add(new Token(TokenType.SLASH)); break;}
        case '(': {tokens.add(new Token(TokenType.OPEN_PAREN)); break;}
        case ')': {tokens.add(new Token(TokenType.CLOSE_PAREN)); break;}
        
        default: {
          if(isNumeric(c)) {
            var startIdx = i;
            while(i + 1 < expression.length && isNumeric(expression[i + 1])) {
              ++i;
            }
            var sub = Arrays.copyOfRange(expression, startIdx, i + 1);
            var chars = new char[sub.length];
            for(var j = 0; j < sub.length; j++) {
              chars[j] = (char) sub[j];
            }
            
            try {
              tokens.add(new Token(Double.valueOf(String.valueOf(chars))));
            } catch(NumberFormatException e) {
              println("input not valid");
            }
          } else if(isName(c)) {
            var startIdx = i;
            while(i + 1 < expression.length && isName(expression[i + 1])) {
              ++i;
            }
            var sub = Arrays.copyOfRange(expression, startIdx, i + 1);
            var chars = new char[sub.length];
            for(var j = 0; j < sub.length; j++) {
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
