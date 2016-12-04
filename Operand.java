package calculator;

import vector.Vector;

import java.util.Set;

/**
 * Created by melon on 05.12.16.
 */
public class Operand implements IProcessable {
    public Operand(int real, int imagine, Set<Vector> vectors) {
        this.real = real;
        this.imagine = imagine;
        this.vectors = vectors;
    }

    public int real;
    public int imagine;
    public Set<Vector> vectors;

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("Operand(%s, %s, vectors)", real, imagine);
    }
}