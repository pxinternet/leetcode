package leetCode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * LC402 - 移掉 K 位数字
 *
 * 题目概要：从 num 中移除 k 个数字，使剩余数字最小（不能有前导零）。
 *
 * 解法说明：单调栈（双端队列模拟）。从左到右扫描，若栈顶大于当前 digit 且还能删，则弹栈。
 * 原因：高位数字越大整体数越大，优先删掉「左侧更大」的位可使结果更小。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
public class LC402removeKdigits {

    public String removeKdigits(String num, int k) {
        if (k == num.length()) return "0";

        Deque<Character> deque = new LinkedList<>();
        for (int i = 0; i < num.length(); i++) {
            char digit = num.charAt(i);
            while (!deque.isEmpty() && k > 0 && deque.peekLast() > digit) {
                deque.pollLast();
                k--;
            }
            deque.offerLast(digit);
        }

        for (int i = 0; i < k; i++) deque.pollLast();

        StringBuilder ret = new StringBuilder();
        boolean leadingZero = true;
        while (!deque.isEmpty()) {
            char digit = deque.pollFirst();
            if (leadingZero && digit == '0') continue;
            leadingZero = false;
            ret.append(digit);
        }
        return ret.length() == 0 ? "0" : ret.toString();
    }

    public static void main(String[] args) {
//        String test = "01";
//        System.out.println(test.substring(1));

        LC402removeKdigits lc402removeKdigits = new LC402removeKdigits();

        String str = "10";

        lc402removeKdigits.removeKdigits(str, 2);


    }

}
