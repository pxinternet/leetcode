package leetCode;

public class LC213rob {

    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        //分两种情况，抢第一个家和不强第一家
        return Math.max(rob(nums, 0, n - 2), rob(nums, 1, n - 1));
    }

    public int rob(int[] nums, int start, int end) {
        int n = nums.length;
        int[] dp = new int[n + 1];
        dp[start] = 0;
        dp[start + 1] = nums[start];

        for (int i = start +2; i <= end + 1; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i -2] + nums[i - 1]);
        }
        return dp[end + 1];
    }
}
