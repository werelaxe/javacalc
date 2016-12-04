package calculator;

import lexer.Lexer;
import lexer.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by melon on 05.12.16.
 */
public class Program {
    public static void main(String[] args) {
        String input;
        try {
            input = new Scanner(new File("src/lexer/in_math.txt")).useDelimiter("\\A").next();
        } catch (FileNotFoundException e) {
            input = "2 + 2 * 2";
        }

        Lexer lexer = new Lexer();
        lexer.register("math", new MathGrammar());
        ArrayList<Token> lexems = lexer.getLexems("math", input);
        ArrayList<Token> finalLexems = new ArrayList<>();
        System.out.println(input);
        for (Token lexeme : lexems) {
            if (lexeme.getType() != "whitespace")
                finalLexems.add(lexeme);
        }
        for (Token lexeme : lexems) {
            System.out.println(lexeme);
        }

    }
}