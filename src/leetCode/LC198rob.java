package leetCode;

public class LC198rob {

    public int rob(int[] nums) {
        //这种最多，最高问题，一般都是动态规划吧
        int n = nums.length;
        int[] dp = new int[n + 1];

        dp[0] = 0;
        dp[1] = nums[0];

        for (int i = 2; i <= n; i++) {
            //有两种情况 偷或者不偷
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }

        return dp[n];

    }
}
