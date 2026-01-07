package leetCode;

public class LC122MAXProfit2 {

    /**
     * LC122 - Best Time to Buy and Sell Stock II（可以进行多次交易）
     * 常见贪心解法：只要今天价格小于明天价格，就在今天买入、明天卖出，将所有上升段的差值累加。
     * 该实现还展示了动态规划的另一种思想（通过记录到某天为止的最大收益）。
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int res = 0;

        int minPrice = prices[0];

        int[] maxProfit = new int[prices.length];
        maxProfit[0] = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] <= prices[i-1]) minPrice = prices[i];

            if (maxProfit[i-1] < prices[i] - minPrice) {
                maxProfit[i -1] = 0;
                maxProfit[i] = prices[i] - minPrice;
            }

        }

        for (int i = 0; i < prices.length; i++) {
            res += maxProfit[i];
        }
        return res;
    }

    // 更常见且简洁的贪心实现
    public int maxProfit2(int[] prices) {
        int res = 0;

        for (int i =1; i < prices.length; i++) {
            if (prices[i] > prices[i-1]) {
                res += prices[i] - prices[i-1];
            }
        }

        return res;
    }

    public static void main(String[] args) {
        LC122MAXProfit2 lc122MAXProfit2 = new LC122MAXProfit2();
        int[] arr = new int[]{8,3,6,2,8,8,8,4,2,0,7,2,9,4,9};

        int res = lc122MAXProfit2.maxProfit(arr);

        System.out.println(res);
    }

}
