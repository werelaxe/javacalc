package calculator;

import vector.Vector;

import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by melon on 05.12.16.
 */
public class Operand implements IProcessable {
    public Operand(int real, int imagine, HashSet<Vector> vectors) {
        this.real = real;
        this.imagine = imagine;
        this.vectors = new HashMap<Integer, Vector>();
        for (Vector v : vectors) {
            this.vectors.put(v.getDimCount(), v);
        }
    }

    public int real;
    public int imagine;
    public HashMap<Integer, Vector> vectors;

    public static Operand sum(Operand firstOperand, Operand secondOperand) {
        int newReal = firstOperand.real + secondOperand.real;
        int newImagine = firstOperand.imagine + secondOperand.imagine;
        HashSet<Vector> vectors = new HashSet<>();
        for (Vector vec : firstOperand.vectors.values()) {
            if (!secondOperand.vectors.keySet().contains(vec.getDimCount())) {
                vectors.add(vec);
            } else {
                Vector second = secondOperand.vectors.get(vec.getDimCount());
                Vector finalVector = vec.add(second);
                if (!finalVector.isZeroVector())
                    vectors.add(finalVector);
            }
        }
        for (Vector vec : secondOperand.vectors.values()) {
            if (!firstOperand.vectors.keySet().contains(vec.getDimCount())) {
                vectors.add(vec);
            }
        }
        return new Operand(newReal, newImagine, vectors);
    }

    public static Operand subtract(Operand firstOperand, Operand secondOperand) {
        int newReal = firstOperand.real - secondOperand.real;
        int newImagine = firstOperand.imagine - secondOperand.imagine;
        HashSet<Vector> vectors = new HashSet<>();
        for (Vector vec : firstOperand.vectors.values()) {
            if (!secondOperand.vectors.keySet().contains(vec.getDimCount())) {
                vectors.add(vec);
            } else {
                Vector second = secondOperand.vectors.get(vec.getDimCount());
                Vector finalVector = vec.subtract(second);
                if (!finalVector.isZeroVector()) {
                    vectors.add(finalVector);
                }
            }
        }
        for (Vector vec : secondOperand.vectors.values()) {
            if (!firstOperand.vectors.keySet().contains(vec.getDimCount())) {
                vectors.add(vec.multiply(-1));
            }
        }
        return new Operand(newReal, newImagine, vectors);
    }

    public static Operand multiply(Operand firstOperand, Operand secondOperand) {
        if (firstOperand.vectors.isEmpty()) {
            if (secondOperand.vectors.isEmpty()) {
                return new Operand(firstOperand.real * secondOperand.real - firstOperand.imagine * secondOperand.imagine,
                        firstOperand.real * secondOperand.imagine + firstOperand.imagine * secondOperand.real,
                        new HashSet<>());
            } else {
                HashSet<Vector> vectors = new HashSet<>();
                for (Vector vec : secondOperand.vectors.values()) {
                    vectors.add(vec.multiply(firstOperand.real));
                }
                return new Operand(secondOperand.real * firstOperand.real,
                        secondOperand.imagine * firstOperand.real,
                        vectors);
            }
        } else {
            HashSet<Vector> vectors = new HashSet<>();
            for (Vector vec : firstOperand.vectors.values()) {
                vectors.add(vec.multiply(secondOperand.real));
            }
            return new Operand(firstOperand.real * secondOperand.real,
                    firstOperand.imagine * secondOperand.real,
                    vectors);
        }
    }
    public static Operand intDiv(Operand firstOperand, Operand secondOperand) {
        HashSet<Vector> vectors = new HashSet<>();
        for (Vector vec : firstOperand.vectors.values()) {
            vectors.add(vec.intDiv(secondOperand.real));
        }
        return new Operand(firstOperand.real / secondOperand.real,
                firstOperand.imagine / secondOperand.real,
                vectors);
    }
    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (Vector vector : vectors.values()) {
            buffer.append(vector);
            buffer.append(" ");
        }
        String result = buffer.toString();
        if (!buffer.toString().equals(""))
            return String.format("Operand(%s, %s, %s)", real, imagine, result.substring(0, result.length() - 1));
        else
            return String.format("Operand(%s, %s, {})", real, imagine);
    }
}