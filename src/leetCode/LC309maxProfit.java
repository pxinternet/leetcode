package leetCode;

public class LC309maxProfit {

    public int maxProfit(int[] prices) {

        int n = prices.length;

        int[][] dp = new int[n][5];

        dp[0][0] = 0;
        //持有股票
        dp[0][1] = -prices[0];
        //卖出后持有
        dp[0][2] = 0;
        //冻结期
        dp[0][3] = 0;
        //卖出
        dp[0][4] = 0;

        for (int i = 1; i < n; i++) {
            dp[i][1] = Math.max(dp[i-1][1], Math.max(dp[i-1][2] - prices[i], dp[i-1][3] - prices[i]));
            dp[i][2] = Math.max(dp[i-1][2], dp[i-1][3]);
            dp[i][3] = dp[i - 1][4];
            dp[i][4] = dp[i - 1][1] + prices[i];

        }

        return Math.max(dp[n - 1][4], Math.max(dp[n-1][3], dp[n-1][2]));
    }


    public int maxProfit2(int[] prices) {
        int n = prices.length;

        int[][] dp = new int[n][3];

        dp[0][0] = -prices[0]; //持有股票
        dp[0][1] = 0; //不持有股票且处在冷冻期
        dp[0][2] = 0; //不持有股票且不处在冷冻期

        for(int i = 1; i < n; i++) {
// dp[i][0]：在第i天持有股票的最大利润，可以是前一天就持有的，或者前一天不持有且不处于冷冻期然后在第i天买入
            dp[i][0] = Math.max(dp[i-1][0] , dp[i-1][2] - prices[i]);

// dp[i][1]：在第i天卖出股票的最大利润，等于前一天持有股票然后在第i天卖出的利润
            dp[i][1] = dp[i - 1][0] + prices[i];

// dp[i][2]：在第i天不持有股票且不处于冷冻期的最大利润，可以是前一天就不持有且不处于冷冻期，或者前一天卖出股票进入冷冻期然后第i天结束冷冻期
            dp[i][2] = Math.max(dp[i - 1][1], dp[i -1][2]);
        }
        return Math.max(dp[n - 1][1], dp[n - 1][2]);
    }
}
