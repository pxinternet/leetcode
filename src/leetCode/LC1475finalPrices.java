package leetCode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LC1475finalPrices {

    public int[] finalPrices(int[] prices) {
        Deque<Integer> stack = new LinkedList<>();

        for (int i = 0; i < prices.length; i++) {
            while(!stack.isEmpty() && prices[i] <= prices[stack.peek()]) {
                prices[stack.pop()] -= prices[i];
            }
            stack.push(i);
        }
        return prices;

    }
}
