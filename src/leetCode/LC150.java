package leetCode;

import java.util.Deque;

public class LC150 {

    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<Integer>();

        int n = tokens.length;

        for (int i = 0; i < n; i++) {
            String token = tokens[i];
            if (isNumber(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                int num2 = stack.pop();
                int num1 = stack.pop();

                switch (token) {
                    case "+":
                        stack.push(num1 + num2);
                        break;
                        case "-":
                        stack.push(num1 - num2);
                        break;
                    case "*":
                        stack.push(num1 * num2);
                        break;
                    case "/":
                        stack.push(num1 / num2);
                        break;

                    default:
                        throw new AssertionError();
                }
            }
        }

        return stack.pop();
    }

}
