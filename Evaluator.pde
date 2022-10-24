static enum TokenType {
  NUM,
  NAME,
  
  PLUS,
  MINUS,
  STAR,
  SLASH,
  
  OPEN_PAREN,
  CLONE_PAREN,
  
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
  
  public ArrayList<Token> tokenize(int[] expression) {
    var tokens = new ArrayList<Token>();
    
    for(var i = 0; i < expression.length; ++i) {
      var c = expression[i];
      switch(c) {
        case '+': {tokens.add(new Token(TokenType.PLUS)); break;}
        case '-': {tokens.add(new Token(TokenType.MINUS)); break;}
        case '*': {
          if(i + 1 < expression.length && expression[i + 1] == '*') {
            ++i;
          }
          break;
        }
        case '/': {tokens.add(new Token(TokenType.SLASH)); break;}
        
        default: {
          if(isNumeric(c)) {
            var startIdx = i;
            var endIdx = i + 1;
            while(endIdx < expression.length && isNumeric(expression[++i])) {
              endIdx++;
            }
            var sub = Arrays.copyOfRange(expression, startIdx, endIdx);
            var chars = new char[sub.length];
            for(var j = 0; j < sub.length; j++) {
              chars[j] = (char) sub[j];
            }
            
            try {
              tokens.add(new Token(Double.valueOf(String.valueOf(chars))));
            } catch(NumberFormatException e) {
              println("input not valid");
            }
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
