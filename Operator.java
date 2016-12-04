package calculator;

/**
 * Created by melon on 05.12.16.
 */
public class Operator implements IProcessable {
    public Operator(String type) {
        this.type = type;
    }

    public String type;

    @Override
    public boolean isOperator() {
        return true;
    }

    @Override
    public String toString() {
        return type;
    }
}