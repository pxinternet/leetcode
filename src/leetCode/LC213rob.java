package leetCode;

/**
 * LC213rob - 打家劫舍 II（环形）
 *
 * 题目（概要）：房屋围成环形，相邻不能偷，求最大金额。
 *
 * 解法说明：环形拆成两条链——不偷第一间 [1,n-1] 与 不偷最后一间 [0,n-2]，分别求 LC198 再取 max。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：nums=[2,3,2] → 3（偷索引 1）
 */
public class LC213rob {

    /**
     * 分两种情况：抢第一家则不能抢最后一家；不抢第一家则可能抢最后一家
     */
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
