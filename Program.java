package calculator;

import vector.Vector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
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
        //System.out.println("Calculating " + input);
        Calc.calculate(input);
        HashSet<Vector> vectors = new HashSet<>();
        vectors.add(new Vector(new int[]{3, 6, 8}));
        Operand op = new Operand(10, 2, vectors);
    }
}