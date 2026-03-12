package leetCode;

/**
 * LC714 - 买卖股票的最佳时机含手续费
 *
 * 题目（概要）：给定股票每日价格 prices 和每笔交易的手续费 fee，可多次交易，求最大利润。
 * 每笔交易（买入+卖出）需支付 fee。同一时间最多持有一股。
 *
 * 解法说明：
 * - 核心思想：贪心。维护 min 为当前持仓成本；当 prices[i]-min>fee 时卖出（收益足以覆盖手续费），
 *   并将新的「等效买入价」设为 prices[i]-fee，以便后续若继续涨可连续获利。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 边界与注意事项：
 * - min 初始为 prices[0]；只在「卖出有利可图」时更新 min 为 prices[i]-fee
 *
 * 示例：prices=[1,3,2,8,4,9], fee=2 → 8（1 买 8 卖、4 买 9 卖，扣费后 5+3=8）
 */
public class LC714maxProfit {

    /**
     * 含手续费的最大利润（贪心）
     *
     * 关键点：卖出后 min=prices[i]-fee，相当于把「未实现利润」的一部分留给下一段，避免重复扣费
     *
     * @param prices 每日股价
     * @param fee    每笔交易手续费
     * @return 最大利润
     */
    public int maxProfit(int[] prices, int fee) {
        if (prices == null || prices.length < 2) return 0;

        int res = 0;
        int min = prices[0];

        for (int i = 1; i < prices.length; i++) {
            if (min > prices[i]) {
                min = prices[i];
            } else if (prices[i] - min > fee) {
                res += prices[i] - min - fee;
                min = prices[i] - fee;   // 等效买入价，便于后续连续上涨时累加利润
            }
        }
        return res;
    }
}
