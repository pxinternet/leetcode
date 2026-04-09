package leetCode;

import java.util.Stack;

/**
 * Calculator - 基本计算器（LC224 简化版：只含 + - * /，不含括号）
 *
 * 题目（概要）：实现一个能够计算 +、-、*、/ 的基本计算器，输入为由数字、空格和运算符组成的字符串。
 *
 * 解法说明：
 * - 遇到数字则累积 currentNumber
 * - 遇到运算符或末尾时，根据上一运算符 operation 处理：+ 入栈正数，- 入栈负数，*、/ 弹栈与当前数运算后入栈
 * - 最后将栈中所有数求和
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：s = "3+2*2" → 7
 */
public class Calculator {

    /**
     * 计算表达式的值
     *
     * @param s 含数字、空格、+、-、*、/ 的字符串
     * @return 计算结果
     */
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int currentNumber = 0;
        char operation = '+';

        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            if (Character.isDigit(currentChar)) {
                currentNumber = currentNumber * 10 + (currentChar - '0');
            }

            if (!Character.isDigit(currentChar) && currentChar != ' ' || i == s.length() - 1) {
                switch (operation) {
                    case '+':
                        stack.push(currentNumber);
                        break;
                    case '-':
                        stack.push(-currentNumber);
                        break;
                    case '*':
                        stack.push(stack.pop() * currentNumber);
                        break;
                    case '/':
                        stack.push(stack.pop() / currentNumber);
                        break;
                    default:
                        break;
                }
                operation = currentChar;
                currentNumber = 0;
            }
        }

        int result = 0;
        for (int num : stack) {
            result += num;
        }
        return result;

    }

}
