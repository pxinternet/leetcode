package leetCode;

public class LC188MaxProfit {
    /**
     * 也是动态规划问题
     * @param k
     * @param prices
     * @return
     */
    public int maxProfit(int k, int[] prices) {
        // 如果价格数组为空，直接返回0
        if (prices.length == 0) {
            return 0;
        }
        // 如果可交易次数大于等于价格数组长度的一半，问题转化为无限次交易的情况
        if ( k >= prices.length / 2) {
            int maxProfit = 0;
            // 遍历价格数组，只要今天的价格高于昨天的，就在昨天买入，今天卖出
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i - 1]) {
                    maxProfit += prices[i] - prices[i - 1];
                }
            }
            // 返回最大利润
            return maxProfit;
        }

        // dp[i][j]表示最多进行i次交易，在第j天结束时的最大利润，k + 1是要考虑0次交易的情况
        int[][] dp = new int[k + 1][prices.length];
        // 遍历交易次数
        for(int i = 1; i <= k; i++) {
            // localMax表示最多进行i-1次交易，在第j天买入的最大利润
            int preBuy = dp[i - 1][0] - prices[0];
            // 遍历价格数组
            for (int j = 1; j < prices.length; j++) {
                // 更新dp[i][j]，可以选择不交易，也可以选择卖出
                dp[i][j] = Math.max(dp[i][j - 1], prices[j] +preBuy);
                // 更新localMax，可以选择不买入，也可以选择买入
                preBuy = Math.max(preBuy, dp[i - 1][j] - prices[j]);
            }
        }
        // 返回最多进行k次交易的最大利润
        return dp[k][prices.length - 1];
    }
}
