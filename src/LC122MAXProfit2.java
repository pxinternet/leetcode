public class LC122MAXProfit2 {

    /**
     * 贪心算法的正确性证明通常需要两个步骤：贪心选择性质和最优子结构。
     * 贪心选择性质：这是指局部最优选择能导致全局最优解。在这个问题中，我们的贪心选择是在每一天都检查一下，如果明天的价格比今天的价格高，那么我们就在今天买入，明天卖出。这个选择是基于当前信息做出的最优选择。  为什么这是一个最优选择呢？假设我们在第i天买入，第j天卖出，其中j>i，且在i和j之间没有任何一天的价格高于j天的价格。那么，如果存在一个k（i<k<j），使得第k天的价格高于第i天的价格，那么我们可以在第k天买入，第j天卖出，这样的利润一定不会比在第i天买入，第j天卖出的利润小。因此，我们的选择是最优的。
     * 最优子结构：这是指问题的最优解包含其子问题的最优解。在这个问题中，如果我们已经知道了前i天的最大利润，那么我们只需要考虑第i+1天是否要进行交易，就可以得到前i+1天的最大利润。  为什么这是一个最优子结构呢？假设我们已经知道了前i天的最大利润，那么在第i+1天，我们有两种选择：一是进行交易，二是不进行交易。如果我们选择进行交易，那么我们的利润就是前i天的最大利润加上第i+1天的价格和第i天的价格之差；如果我们选择不进行交易，那么我们的利润就是前i天的最大利润。因此，我们可以通过前i天的最大利润和第i+1天的价格来决定第i+1天的最大利润，这就是最优子结构
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
