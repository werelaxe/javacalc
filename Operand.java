package calculator;

import numbers.ComplexNumber;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import vector.Vector;

import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by melon on 05.12.16.
 */
public class Operand implements IProcessable {
    public Operand(ComplexNumber value, HashSet<Vector> vectors) {
        this.value = value;
        this.vectors = new HashMap<Integer, Vector>();
        for (Vector v : vectors) {
            this.vectors.put(v.getDimCount(), v);
        }
    }
    private ComplexNumber value;
    public ComplexNumber getValue() {
        return value;
    }
    public HashMap<Integer, Vector> vectors;

    public static Operand sum(Operand firstOperand, Operand secondOperand) {
        int newReal = firstOperand.value.getReal() + secondOperand.value.getReal();
        int newImagine = firstOperand.value.getImagine() + secondOperand.value.getImagine();
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
        return new Operand(new ComplexNumber(newReal, newImagine), vectors);
    }

    public static Operand subtract(Operand firstOperand, Operand secondOperand) {
        int newReal = firstOperand.value.getReal() - secondOperand.value.getReal();
        int newImagine = firstOperand.value.getImagine() - secondOperand.value.getImagine();
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
                vectors.add(vec.multiply(new ComplexNumber(-1, 0)));
            }
        }
        return new Operand(new ComplexNumber(newReal, newImagine), vectors);
    }

    public static Operand multiply(Operand firstOperand, Operand secondOperand) {
        if (firstOperand.vectors.isEmpty() && secondOperand.vectors.isEmpty())
            return new Operand(firstOperand.value.mul(secondOperand.value), new HashSet<>());
        if (!firstOperand.vectors.isEmpty() && !secondOperand.vectors.isEmpty()) {
            int firstSize = firstOperand.vectors.size();
            int secondSize = secondOperand.vectors.size();
            if (firstSize > 1 || secondSize > 1 || firstSize != secondSize)
                throw new IllegalArgumentException();
            ComplexNumber result = firstOperand.value.mul(secondOperand.value);

            for (Vector vec1:firstOperand.vectors.values())
                for (Vector vec2:secondOperand.vectors.values()) {
                    result = result.add(vec1.scalarProduct(vec2));
                }
            return new Operand(result, new HashSet<>());
        }
        if (firstOperand.vectors.isEmpty()) {
            if (firstOperand.value.equals(new ComplexNumber(0, 0)))
                return new Operand(new ComplexNumber(0, 0), new HashSet<>());
            else {
                ComplexNumber newValue = secondOperand.value.mul(firstOperand.value);
                HashSet<Vector> newVectors = new HashSet<>();
                for (Vector vec : secondOperand.vectors.values()) {
                    newVectors.add(vec.multiply(firstOperand.value));
                }
                return new Operand(newValue, newVectors);
            }
        } else {
            if (secondOperand.value.equals(new ComplexNumber(0, 0)))
                return new Operand(new ComplexNumber(0, 0), new HashSet<>());
            else {
                ComplexNumber newValue = firstOperand.value.mul(secondOperand.value);
                HashSet<Vector> newVectors = new HashSet<>();
                for (Vector vec : firstOperand.vectors.values()) {
                    newVectors.add(vec.multiply(secondOperand.value));
                }
                return new Operand(newValue, newVectors);
            }
        }
    }
    public static Operand intDiv(Operand firstOperand, Operand secondOperand) {
        HashSet<Vector> vectors = new HashSet<>();
        for (Vector vec : firstOperand.vectors.values()) {
            vectors.add(vec.intDiv(secondOperand.value));
        }
        return new Operand(firstOperand.value .div(secondOperand.value), vectors);
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
            return String.format("Operand(%s, %s)", value, result.substring(0, result.length() - 1));
        else
            return String.format("Operand(%s, {})", value);
    }
}