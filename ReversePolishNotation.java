package calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by melon on 05.12.16.
 */
public class ReversePolishNotation {
    public static ArrayList<IProcessable> reverse(ArrayList<IProcessable> summonds) {
        ArrayList<IProcessable> outputExpression = new ArrayList<>();
        Stack<Operator> operStack = new Stack<>();
        for (IProcessable summond : summonds) {
            if (!summond.isOperator())
                outputExpression.add(summond);
            else { //Если оператор
                Operator summonOperator = ((Operator) summond);
                if (summonOperator.type.equals("left_parenthesis"))
                    operStack.push((Operator) summond);
                else if (summonOperator.type.equals("right_parenthesis")) {
                    Operator operator = operStack.pop();
                    while (!operator.type.equals("left_parenthesis")) {
                        outputExpression.add(operator);
                        operator = operStack.pop();
                    }
                } else {
                    if (operStack.size() > 0)
                        if (summonOperator.getPriority() <= operStack.peek().getPriority())
                            outputExpression.add(operStack.pop());
                    operStack.push(summonOperator);
                }
            }
        }
        while (operStack.size() > 0)
            outputExpression.add(operStack.pop());
        return outputExpression;
    }
}