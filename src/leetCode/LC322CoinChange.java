package leetCode;

import java.util.Arrays;

public class LC322CoinChange {

    public int coinChange(int[] coins, int amount) {
        //最值问题都是优先拿动态规划吧
        if(amount == 0) return 0;
        int max = amount + 1;
        int n = coins.length;

        int[] dp = new int[amount + 1];

        Arrays.fill(dp, max);

        dp[0] = 0;

        for (int i = 1; i <=amount; i++) {
            for (int coin : coins) {
                if (i - coin >= 0) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];

    }

}
