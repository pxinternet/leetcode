package round3;

import java.util.Stack;

public class Calculator {

    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();

        int currentNumber = 0;
        char opeartion = '+';

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                currentNumber = currentNumber * 10 + (c - '0');
            }

            if (!Character.isDigit(c) && c != ' ' || i == s.length() - 1) {

                if (c == '(') {
                    int j = i;
                    int braces = 0;
                    for (; i < s.length(); i++) {
                        if (s.charAt(i) == '(')
                            braces++;
                        if (s.charAt(i) == ')')
                            braces--;
                        if (braces == 0)
                            break;
                    }
                    currentNumber = calculate(s.substring(j + 1, i));
                }
            }

            switch (opeartion) {
                case '+' -> stack.push(currentNumber);
                case '-' -> stack.push(-currentNumber);
                case '*' -> stack.push(stack.pop() * currentNumber);
                case '/' -> stack.push(stack.pop() / currentNumber);
                default -> throw new IllegalArgumentException("Invalid operation: " + opeartion);
            }

            opeartion = c;
            currentNumber = 0;

        }

        int sum = 0;
        while (!stack.isEmpty()) {
            sum += stack.pop();
        }
        return sum;
    }

}
