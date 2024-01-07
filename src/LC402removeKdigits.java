import com.sun.scenario.effect.impl.state.LinearConvolveKernel;

import java.util.Deque;
import java.util.LinkedList;

public class LC402removeKdigits {
    public String removeKdigits(String num, int k) {

        //全部移除
        if (k == num.length()) return "0";
        //借助栈这个数据结构
        Deque<Character> deque = new LinkedList<>();

        int length = num.length();

        for (int i = 0; i < num.length(); ++i) {
            char digit = num.charAt(i);
            while (!deque.isEmpty() && k > 0 && deque.peekLast() > digit) {
                deque.pollLast();
                k--;
            }
            deque.offerLast(digit);
        }

        for(int i = 0; i < k; i++) {
            deque.pollLast();
        }

        StringBuilder ret = new StringBuilder();
        boolean leadingZero = true;

        while (!deque.isEmpty()) {
            char digit = deque.pollFirst();
            if (leadingZero && digit == '0') {
                continue;
            }
            leadingZero = false;
            ret.append(digit);
        }
        return ret.length() == 0 ? "0" : ret.toString();

        //消除前导0
    }

    public static void main(String[] args) {
//        String test = "01";
//        System.out.println(test.substring(1));

        LC402removeKdigits lc402removeKdigits = new LC402removeKdigits();

        String str = "10";

        lc402removeKdigits.removeKdigits(str, 2);


    }

}
