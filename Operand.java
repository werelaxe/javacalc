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
        return String.format("Operand(%s, %s, %s)", real, imagine, result.substring(0, result.length() - 1));
    }
}