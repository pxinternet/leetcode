package leetCode;

public class LC123MaxProfit {

    /**
     * LC123 - Best Time to Buy and Sell Stock III（最多两次交易）
     * 状态定义（四个变量）：
     * - buy1: 第一次买入后剩下的净收益（负值）
     * - sell1: 第一次卖出后的最大收益
     * - buy2: 第二次买入后的最大净收益（等于 sell1 - price）
     * - sell2: 第二次卖出后的最大收益（最终答案）
     *
     * 状态转移在循环中以当前价格进行逐步更新，时间 O(n)，空间 O(1)
     */

    public int maxProfit(int[] prices) {

        int n = prices.length;

        int buy1 = -prices[0];
        int sell1 = 0;
        int buy2 = -prices[0];
        int sell2 = 0;

        for( int i = 1; i < n; i++) {
            // 更新每个状态：是否执行当前价格的买入/卖出操作以获得更优收益
            buy1 = Math.max(buy1, -prices[i]);
            sell1 = Math.max(sell1, buy1 +prices[i]);
            buy2 = Math.max(buy2, sell1 - prices[i]);
            sell2 = Math.max(sell2, buy2 + prices[i]);
            System.out.println("i : " + i + ", buy1: " + buy1 + ", sell1: " + sell1 + ", buy2 : " + buy2 + ", sell2 : " + sell2);
        }

        return sell2; // 第二次卖出的最大收益
    }

    public static void main(String[] args) {
        LC123MaxProfit lc123MaxProfit = new LC123MaxProfit();

        int[] arr = new int[]{1,2,4,2,5,7,2,4,9,0};

        int res = lc123MaxProfit.maxProfit(arr);

        System.out.println("111");

        System.out.println(res);
    }
}
