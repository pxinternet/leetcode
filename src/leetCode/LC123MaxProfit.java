package leetCode;

/**
 * LC123 - 买卖股票的最佳时机 III（最多两次交易）
 *
 * 题目（概要）：给定股票每日价格 prices，最多完成两笔交易（买-卖-买-卖），求最大利润。
 * 同一时间最多持有一股。
 *
 * 解法说明：
 * - 核心思想：DP 用四个状态变量滚动更新。buy1 为第一次买入后净支出（负收益），sell1 为第一次卖出后收益，
 *   buy2 为第二次买入后净收益（= sell1 - price），sell2 为第二次卖出后收益（最终答案）。
 * - 转移： buy1=max(buy1,-prices[i])；sell1=max(sell1,buy1+prices[i])；
 *   buy2=max(buy2,sell1-prices[i])；sell2=max(sell2,buy2+prices[i])。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 边界与注意事项：
 * - 单元素数组时 buy1=sell1=buy2=sell2 初始化合理
 *
 * 示例：prices=[3,3,5,0,0,3,1,4] → 6（第一次 3→5，第二次 0→4）
 */
public class LC123MaxProfit {

    /**
     * 最多两次交易下的最大利润
     *
     * 关键点：四个状态按顺序更新，每次用当前价格尝试买入/卖出
     *
     * @param prices 每日股价
     * @return 最大利润
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) return 0;

        int buy1 = -prices[0];
        int sell1 = 0;
        int buy2 = -prices[0];
        int sell2 = 0;

        for (int i = 1; i < prices.length; i++) {
            buy1 = Math.max(buy1, -prices[i]);
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy2 = Math.max(buy2, sell1 - prices[i]);
            sell2 = Math.max(sell2, buy2 + prices[i]);
        }

        return sell2;
    }

    public static void main(String[] args) {
        LC123MaxProfit solver = new LC123MaxProfit();
        int[] arr = new int[]{1,2,4,2,5,7,2,4,9,0};
        System.out.println(solver.maxProfit(arr));
    }
}
