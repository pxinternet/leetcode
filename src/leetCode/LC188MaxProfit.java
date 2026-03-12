package leetCode;

/**
 * LC188 - 买卖股票的最佳时机 IV（最多 k 次交易）
 *
 * 题目（概要）：给定股票每日价格 prices 和最多交易次数 k，求最大利润。
 * 同一时间最多持有一股。若 k >= n/2 则等价于无限次交易（贪心：所有上涨段都吃）。
 *
 * 解法说明：
 * - 核心思想：DP。dp[i][j] 表示最多 i 次交易、到第 j 天结束时的最大利润。
 * - 转移：dp[i][j] = max(dp[i][j-1], prices[j] + preBuy)，其中 preBuy = max(preBuy, dp[i-1][j] - prices[j])。
 * - preBuy 表示「第 i-1 次交易完成后、在第 j 天买入」的最优净成本。
 *
 * 时间复杂度：O(n * k)，k>=n/2 时退化为 O(n)
 * 空间复杂度：O(k * n)
 *
 * 边界与注意事项：
 * - 空数组返回 0
 * - k>=n/2 时转化为无限次交易，贪心累加所有 prices[i]-prices[i-1]>0 的差
 *
 * 示例：k=2, prices=[2,4,1] → 2；k=2, prices=[3,2,6,5,0,3] → 7
 */
public class LC188MaxProfit {

    /**
     * 最多 k 次交易下的最大利润
     *
     * @param k      最多交易次数
     * @param prices 每日股价
     * @return 最大利润
     */
    public int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        // k >= n/2 时等价于无限次交易，贪心累加所有上涨段
        if (k >= prices.length / 2) {
            int maxProfit = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i - 1]) {
                    maxProfit += prices[i] - prices[i - 1];
                }
            }
            return maxProfit;
        }

        // dp[i][j]：最多 i 次交易，到第 j 天结束时的最大利润
        int[][] dp = new int[k + 1][prices.length];

        for (int i = 1; i <= k; i++) {
            // preBuy：在第 j 天买入时的最优净成本（= 上次卖出收益 - 当前价格）
            int preBuy = dp[i - 1][0] - prices[0];

            for (int j = 1; j < prices.length; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], prices[j] + preBuy);
                preBuy = Math.max(preBuy, dp[i - 1][j] - prices[j]);
            }
        }

        return dp[k][prices.length - 1];
    }
}
