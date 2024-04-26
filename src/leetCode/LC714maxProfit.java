package leetCode;

public class LC714maxProfit {

    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;

        int res = 0;
        int min = prices[0];
//
        for (int i = 1; i < n; i++) {
            if (min > prices[i]) {
                min = prices[i];
            } else if (prices[i] - min > fee) {
                res += prices[i] - min - fee;
                min = prices[i] - fee;
            }
        }
        return res;
    }
}
