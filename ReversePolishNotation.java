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
            //Если символ - цифра, то считываем все число
            if (!summond.isOperator())
                outputExpression.add(summond);
            else { //Если оператор
                Operator summonOperator = ((Operator) summond);
                if (summonOperator.type.equals("left_parenthesis")) //Если символ - открывающая скобка
                    operStack.push((Operator) summond);
                else if (summonOperator.type.equals("right_parenthesis")) //Если символ - закрывающая скобка
                {
                    //Выписываем все операторы до открывающей скобки в строку
                    Operator operator = operStack.pop();

                    while (!operator.type.equals("left_parenthesis")) {
                        outputExpression.add(operator);
                        operator = operStack.pop();
                    }
                } else //Если любой другой оператор
                {
                    if (operStack.size() > 0) //Если в стеке есть элементы
                        if (summonOperator.getPriority() <= operStack.peek().getPriority()) //И если приоритет нашего оператора
                            // меньше или равен приоритету оператора на вершине стека
                            outputExpression.add(operStack.pop());
                    operStack.push(summonOperator); //Если стек пуст,
                    // или же приоритет оператора выше - добавляем операторов на вершину стека

                }
            }
        }
        //Когда прошли по всем символам, выкидываем из стека все оставшиеся там операторы в строку
        while (operStack.size() > 0)
            outputExpression.add(operStack.pop());
        return outputExpression;
    }
}