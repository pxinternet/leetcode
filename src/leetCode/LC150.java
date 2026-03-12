package leetCode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * LC150 - 逆波兰表达式求值
 *
 * 题目（概要）：给定逆波兰式 tokens（数字和 +、-、*、/），求表达式的值。
 *
 * 解法说明：
 * - 核心思想：栈。遇数字入栈；遇运算符弹出两个数，计算后入栈。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 边界与注意事项：
 * - 除法向零取整；保证表达式有效
 *
 * 示例：tokens=["2","1","+","3","*"] → 9
 */
public class LC150 {

    /**
     * 栈模拟求值
     */
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<>();

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

    /** 反转链表（LC206 的实现） */
    public ListNode reverseList(ListNode head) {

        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        return prev;

    }

}
