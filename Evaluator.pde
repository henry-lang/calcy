enum TokenType {
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

class Token {
  final TokenType type;
  final Double num;
  final String name;
  
  public Token(TokenType type) {
    this.type = type;
    this.num = null;
    this.name = null;
  }
  
  public Token(TokenType type, double num) {
    this.type = type;
    this.num = num;
    this.name = null;
  }
  
  public Token(TokenType type, String name) {
    this.type = type;
    this.num = null;
    this.name = name;
  }
}

class Evaluator {
  private Environment env;
  
  public Evaluator(Environment env) {
    this.env = env;
  }
  
  private ArrayList<Token> tokenize(String expression) {
    var tokens = new ArrayList<Token>();
    
    for(var i = 0; i < expression.length(); ++i) {
      switch(expression.charAt(i)) {
        case '+': {tokens.add(new Token(TokenType.PLUS)); break;}
        case '-': {tokens.add(new Token(TokenType.MINUS)); break;}
        case '*': {
          if(i + 1 < expression.length() && expression.charAt(i + 1) == '*') {
            ++i;
          }
        }
        case '/': {tokens.add(new Token(TokenType.SLASH)); break;}
        
        default: {
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
