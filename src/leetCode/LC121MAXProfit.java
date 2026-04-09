package leetCode;

/**
 * LC121MAXProfit - 买卖股票的最佳时机（只允许一次交易）
 *
 * 题目（概要）：给定数组 prices，其中 prices[i] 表示某股票第 i 天的价格。只能完成一笔交易（买入一次、卖出一次），
 * 求所能获得的最大利润。如果不能获得任何利润，返回 0。
 *
 * 解法说明：
 * - 核心思想：遍历每一天，假设在第 i 天卖出，则最优买入日是在 [0, i-1] 中价格最低的那一天
 * - 维护 leftIndex：表示截至当前，历史最低价格的索引（即最优买入日）
 * - 对于每个 i，计算 prices[i] - prices[leftIndex] 作为"在第 i 天卖出的收益"，并更新全局最大值 res
 *
 * 时间复杂度：O(n)，一次遍历
 * 空间复杂度：O(1)
 *
 * 边界与注意事项：
 * - 空数组或单元素数组返回 0（无法完成买卖）
 * - 若所有天价格单调递减，最大利润为 0
 *
 * 示例：
 * - prices = [7,1,5,3,6,4] → 5（第 2 天买入 1，第 5 天卖出 6，利润 5）
 * - prices = [7,6,4,3,1] → 0（无法获得利润）
 */
public class LC121MAXProfit {

    /**
     * 计算单次交易能获得的最大利润
     *
     * 关键点逐行说明：
     * - leftIndex：记录历史最低价格的下标，视为最优买入日
     * - 从 i=1 开始遍历（第 0 天无法卖出），若 prices[i] 更低则更新 leftIndex
     * - 每步计算当前卖出收益 prices[i]-prices[leftIndex]，并更新 res
     *
     * @param prices 每日股票价格数组
     * @return 最大利润，不能获利时返回 0
     */
    public int maxProfit(int[] prices) {
        // 边界条件：不足 2 天无法完成买卖（至少需要买一天、卖一天）
        if (prices == null || prices.length < 2) return 0;

        int res = 0;
        // leftIndex：截至当前，历史最低价格的下标，作为"在第 i 天卖出"时的最优买入日
        int leftIndex = 0;

        for (int i = 1; i < prices.length; i++) {
            // 若第 i 天价格更低，更新最优买入日为 i（例如：prices=[7,1,5]，i=1 时 leftIndex 更新为 1）
            if (prices[i] < prices[leftIndex])
                leftIndex = i;

            // 假设第 i 天卖出，收益 = prices[i]-prices[leftIndex]，更新全局最大利润
            res = Math.max(res, prices[i] - prices[leftIndex]);
        }

        return res;
    }

    public static void main(String[] args) {
        LC121MAXProfit lc121MAXProfit = new LC121MAXProfit();
//        int[] arr = new int[] {7, 1, 5, 3, 6, 4};
        int[] arr = new int[] {7,6,4,3,1};
        int res = lc121MAXProfit.maxProfit(arr);
        System.out.println(res);
    }
}
