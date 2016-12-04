package calculator;

import lexer.Lexer;
import lexer.Token;
import vector.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by melon on 05.12.16.
 */
public class Calc {
    public static ArrayList<IProcessable> parse(String source) {
        Lexer lexer = new Lexer();
        lexer.register("math", new MathGrammar());
        ArrayList<Token> lexems = lexer.getLexems("math", source);
        ArrayList<IProcessable> finalSummands = new ArrayList<>();
        for (Token lexeme : lexems) {
            if (!lexeme.getType().equals("whitespace")) {
                if (lexeme.getType().equals("operator"))
                    finalSummands.add(new Operator(lexeme.getSubType()));
                if (lexeme.getType().equals("number")) {
                    if (lexeme.getSubType().equals("real"))
                        finalSummands.add(new Operand(Integer.parseInt(lexeme.getText()), 0, new HashSet<>()));
                    if (lexeme.getSubType().equals("special"))
                        finalSummands.add(new Operand(0, Integer.parseInt(lexeme.getText()), new HashSet<>()));
                }
            }
        }
        return finalSummands;
    }
    public static void calculate(String source) {
        ArrayList<IProcessable> objects = parse(source);
        for (IProcessable obj: objects) {
            System.out.println(obj);
        }
    }
}
