package calculator;

import vector.Vector;

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
        Operand result = Calc.calculate(input);
        System.out.println("Input = " + input);
        System.out.print("Output = " + result);
    }
}