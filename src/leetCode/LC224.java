package leetCode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * LC224 - 基本计算器
 *
 * 题目（概要）：字符串含数字、+、-、空格、括号，计算其值。
 *
 * 解法说明：栈记录每层括号前的符号；遇 ( 压栈当前 sign，遇 ) 弹栈；数字按当前 sign 累加。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例："(1+(4+5+2)-3)+(6+8)" → 23
 */
public class LC224 {
    public int calculate(String s) {

        Deque<Integer> ops = new LinkedList<>();
        ops.push(1);
        int sign = 1;

        int ret = 0;
        int n = s.length();
        int i = 0;

        while (i < n) {
            if (s.charAt(i) == ' ') {
                i++;
            } else if (s.charAt(i) == '+') {
                sign = ops.peek();
                i++;
            } else if (s.charAt(i) == '-') {
                sign = -ops.peek();
                i++;
            } else if (s.charAt(i) == '(') {
                ops.push(sign);
                i++;
            } else if (s.charAt(i) == ')') {
                ops.pop();
                i++;
            } else {
                long num = 0;
                while (i < n && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + s.charAt(i) - '0';
                    i++;
                }
                ret += sign * num;
            }
        }
        return ret;


    }
}
