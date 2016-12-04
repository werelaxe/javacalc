package calculator;

import lexer.Lexer;
import lexer.Token;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import vector.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by melon on 05.12.16.
 */
public class Calc {
    public static ArrayList<Token> removeWhitespaces(String source) {
        Lexer lexer = new Lexer();
        lexer.register("math", new MathGrammar());
        ArrayList<Token> lexems = lexer.getLexems("math", source);
        ArrayList<Token> finalLexems = new ArrayList<>();
        for (Token lexeme : lexems) {
            if (!lexeme.getType().equals("whitespace"))
                finalLexems.add(lexeme);
        }
        return finalLexems;
    }
    public static ArrayList<Token> preParse(ArrayList<Token> lexems) {
        ArrayList<Token> finalLexems = new ArrayList<>();
        int i = 0;
        while (i < lexems.size()){
            Token lexeme = lexems.get(i);
            if (lexeme.getSubType().equals("left_parenthesis")) {
                System.out.println("\nStart process pereths");
                Token currentLexeme = new Token("none", "none");
                while (i != lexems.size() && !"right_parenthesis".equals(currentLexeme.getSubType())) {
                    currentLexeme = lexems.get(i);
                    System.out.println(currentLexeme);
                    i++;
                }
                System.out.println("Finish process pereths\n");
            }
            if (i != lexems.size())
                System.out.println(lexems.get(i));
            i++;
        }
        return null;
    }
    public static IProcessable parse(Token lexeme) {
        if (!lexeme.getType().equals("whitespace")) {
            if (lexeme.getType().equals("operator"))
                return new Operator(lexeme.getSubType());
            if (lexeme.getType().equals("number")) {
                if (lexeme.getSubType().equals("real"))
                    return new Operand(Integer.parseInt(lexeme.getText()), 0, new HashSet<>());
                if (lexeme.getSubType().equals("special"))
                    return new Operand(0, Integer.parseInt(lexeme.getText()), new HashSet<>());
            }
        }
        throw new IllegalArgumentException();
    }
    public static void calculate(String source) {
        ArrayList<Token> objects = removeWhitespaces(source);
        preParse(objects);
    }
}
