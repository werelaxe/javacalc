package calculator;

import lexer.Grammar;
import lexer.ParserRules;

import java.util.ArrayList;

/**
 * Created by melon on 04.12.16.
 */
public class MathGrammar extends Grammar {
    public MathGrammar() {
        super(new MathParser(), null);
        ParserRules parserRules = new ParserRules("//", "/*", "*/", false);
        parserRules.addEndNumberSymbol('i');
        this.parserRules = parserRules;
    }
}