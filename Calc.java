package calculator;

import lexer.Lexer;
import lexer.Token;
import numbers.ComplexNumber;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import vector.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

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
        } else {
            ComplexNumber[] coordinates = new ComplexNumber[dimCount + 1];
            int currentCoordinate = 0;
            for (Token t : lexems) {
                if (t.getType().equals("number")) {
                    coordinates[currentCoordinate] = ComplexNumber.parseComplexNumber(t.getText());
                    currentCoordinate++;
                }
            }
            HashSet<Vector> set = new HashSet<>();
            set.add(new Vector(coordinates));
            finalSummands.add(new Operand(new ComplexNumber(0, 0), set));
        }
        return finalSummands;
    }

    public static ArrayList<IProcessable> parse(ArrayList<Token> lexems) {
        ArrayList<IProcessable> finalSummonds = new ArrayList<>();
        int i = 0;
        while (i < lexems.size()) {
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
                for (IProcessable bufferSummond : parseParentheses(parTokens)) {
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
                    return new Operand(new ComplexNumber(Integer.parseInt(lexeme.getText()), 0), new HashSet<>());
                if (lexeme.getSubType().equals("special"))
                    return new Operand(new ComplexNumber(0, Integer.parseInt(lexeme.getText())), new HashSet<>());
            }
        }
        throw new IllegalArgumentException();
    }

    public static Operand calculateExpression(ArrayList<IProcessable> reversedSummonds) {
        Stack<IProcessable> processStack = new Stack<>();
        for (IProcessable summond : reversedSummonds) {
            if (!summond.isOperator()) {
                processStack.push(summond);
            } else {
                Operator operator = (Operator) summond;
                Operand firstOperand = (Operand) processStack.pop();
                Operand secondOperand = (Operand) processStack.pop();
                if (operator.type.equals("add"))
                    processStack.push(Operand.sum(firstOperand, secondOperand));
                if (operator.type.equals("multiply"))
                    processStack.push(Operand.multiply(firstOperand, secondOperand));
                if (operator.type.equals("subtract"))
                    processStack.push(Operand.subtract(secondOperand, firstOperand));
                if (operator.type.equals("int_div")) {
                    processStack.push(Operand.intDiv(secondOperand, firstOperand));
                }
            }
        }
        return (Operand) processStack.pop();
    }

    public static String processSource(String source) {
        return source + " =";
    }

    public static void calculate(String source) {
        System.out.println(String.format("Source: {%s}", source));

        ArrayList<Token> lexems = removeWhitespaces(processSource(source));

        System.out.println(String.format("Lexems: %s", lexems));

        ArrayList<IProcessable> summonds = parse(lexems);

        System.out.println(String.format("Summonds: %s", summonds));

        ArrayList<IProcessable> reversedSummonds = ReversePolishNotation.reverse(summonds);

        System.out.println(String.format("Reversed summonds: %s", reversedSummonds));

        Operand result = calculateExpression(reversedSummonds);

        System.out.println(String.format("Result: %s", result));
    }
}