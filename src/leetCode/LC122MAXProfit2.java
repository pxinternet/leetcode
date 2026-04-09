package leetCode;

/**
 * LC122MAXProfit2 - 买卖股票的最佳时机 II（可多次交易）
 *
 * 题目（概要）：可完成多笔交易（买-卖-买-卖...），求最大利润。
 *
 * 解法说明：贪心，所有「今天买明天卖」的正收益累加，等价于在每个上升段低点买高点卖。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 示例：prices=[7,1,5,3,6,4] → 7（(5-1)+(6-3)=7）
 */
public class LC122MAXProfit2 {

    /**
     * DP 风格实现（记录到某天的收益）
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
