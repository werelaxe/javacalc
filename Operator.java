package calculator;

/**
 * Created by melon on 05.12.16.
 */
public class Operator implements IProcessable {
    public Operator(String type) {
        this.type = type;
    }

    public String type;

    public int getPriority() {
        if (type.equals("left_parenthesis"))
            return 0;
        if (type.equals("right_parenthesis"))
            return 1;
        if (type.equals("add"))
            return 2;
        if (type.equals("subtract"))
            return 3;
        if (type.equals("multiply"))
            return 4;
        if (type.equals("int_div"))
            return 4;
        return 5;
    }

    @Override
    public boolean isOperator() {
        return true;
    }

    @Override
    public String toString() {
        return type;
    }
}