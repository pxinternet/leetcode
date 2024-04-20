package leetCode;

public class LC53maxSubArray {

    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];

        int maxSum = dp[0];

        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            maxSum = Math.max(maxSum, dp[i]);
        }
        return maxSum;

    }

    public int maxSubArrayPre(int[] nums) {
        int pre = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {

            pre = pre < 0 ? nums[i] : pre + nums[i];
            maxSum = Math.max(pre, maxSum);
        }
        return maxSum;

    }
}
