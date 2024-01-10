import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

public class LC123MaxProfit {

    /**
     * 定义状态方程
     * buy1 买了一次
     * sell1 卖了一次
     * buy2 第二次买
     * sell2 第二次卖
     * 最终结果是第二次卖
     * @param prices
     * @return
     */

    public int maxProfit(int[] prices) {

        int n = prices.length;

        int buy1 = -prices[0];
        int sell1 = 0;
        int buy2 = -prices[0];
        int sell2 = 0;

        for( int i = 1; i < n; i++) {
            buy1 = Math.max(buy1, -prices[i]);
            sell1 = Math.max(sell1, buy1 +prices[i]);
            buy2 = Math.max(buy2, sell1 - prices[i]);
            sell2 = Math.max(sell2, buy2 + prices[i]);
            System.out.println("i : " + i + ", buy1: " + buy1 + ", sell1: " + sell1 + ", buy2 : " + buy2 + ", sell2 : " + sell2);
        }

        return sell2;
    }

    public static void main(String[] args) {
        LC123MaxProfit lc123MaxProfit = new LC123MaxProfit();

        int[] arr = new int[]{1,2,4,2,5,7,2,4,9,0};

        int res = lc123MaxProfit.maxProfit(arr);

        System.out.println(res);
    }
}
