package calculator;

import lexer.Parser;
import lexer.Token;

/**
 * Created by melon on 05.12.16.
 */
public class MathParser extends Parser {
    public MathParser() {
        super();
        addKeyWord(new Token("+", "operator", "add"));
        addKeyWord(new Token("-", "operator", "subtract"));
        addKeyWord(new Token("*", "operator", "multiply"));
        addKeyWord(new Token("/", "operator", "int_div"));
        addKeyWord(new Token("(", "operator", "left_parenthesis"));
        addKeyWord(new Token(")", "operator", "right_parenthesis"));
        addKeyWord(new Token(",", "operator", "comma"));
        addKeyWord(new Token("i", "operator", "complex"));
    }
}
