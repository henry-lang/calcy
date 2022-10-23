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
}
