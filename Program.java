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
        //Calc.calculate(input);
        HashSet<Vector> vectors1 = new HashSet<>();
        vectors1.add(new Vector(new int[]{3, 1}));
        vectors1.add(new Vector(new int[]{3, 1, 0}));

        HashSet<Vector> vectors2 = new HashSet<>();
        vectors2.add(new Vector(new int[]{4, 5, 6}));
        vectors2.add(new Vector(new int[]{4, 5, 6, 3}));
        vectors2.add(new Vector(new int[]{4}));

        HashSet<Vector> vectors3 = new HashSet<>();
        vectors3.add(new Vector(new int[]{0, 5, 1, 3, 4}));
        vectors3.add(new Vector(new int[]{0, 5}));

        Operand op1 = new Operand(-1, 3, vectors1);
        Operand op2 = new Operand(0, 4, vectors2);
        Operand op3 = new Operand(10, -2, vectors3);
        System.out.println(Operand.sum(Operand.sum(op1, op2), op3));
    }
}