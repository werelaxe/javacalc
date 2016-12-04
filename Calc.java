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
    public static ArrayList<IProcessable> parseParentheses(ArrayList<Token> lexems) {
        int dimCount = 0;
        ArrayList<IProcessable> finalSummands = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        for (Token t : lexems) {
            if (t.getText().equals(",")) {
                dimCount++;
            }
        }
        if (dimCount == 0) {
            for (Token t : lexems) {
                finalSummands.add(parseToken(t));
            }
        }
        else {
            int[] coordinates = new int[dimCount + 1];
            int currentCoordinate = 0;
            for (Token t : lexems) {
                if (t.getType().equals("number")) {
                    coordinates[currentCoordinate] = Integer.parseInt(t.getText());
                    currentCoordinate++;
                }
            }
            HashSet<Vector> set = new HashSet<>();
            set.add(new Vector(coordinates));
            finalSummands.add(new Operand(0, 0, set));
        }
        return finalSummands;
    }
    public static ArrayList<IProcessable> parse(ArrayList<Token> lexems) {
        ArrayList<IProcessable> finalSummonds = new ArrayList<>();
        int i = 0;
        while (i < lexems.size()){
            Token lexeme = lexems.get(i);
            if (lexeme.getSubType().equals("left_parenthesis")) {
                //System.out.println("\nStart process");
                ArrayList<Token> parTokens = new ArrayList<>();
                Token currentLexeme = new Token("none", "none");
                while (i != lexems.size() && !"right_parenthesis".equals(currentLexeme.getSubType())) {
                    currentLexeme = lexems.get(i);
                    parTokens.add(currentLexeme);
                    //System.out.println(currentLexeme);
                    i++;
                }
                //System.out.println("Finish process\n");
                for (IProcessable bufferSummond:parseParentheses(parTokens)) {
                    finalSummonds.add(bufferSummond);
                }
            }
            if (i != lexems.size()) {
                    finalSummonds.add(parseToken(lexems.get(i)));
                }
            i++;
        }
        return finalSummonds;
    }
    public static IProcessable parseToken(Token lexeme) {
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
        System.out.println(String.format("SOURCE{%S}", source));
        ArrayList<Token> lexems = removeWhitespaces(source + " =");
        ArrayList<IProcessable> summonds = parse(lexems);
        for(IProcessable s:summonds) {
            System.out.println(s);
        }
    }
}
