package leetCode;

/**
 * LC45 - 跳跃游戏 II
 *
 * 题目（概要）：数组 nums[i] 表示从索引 i 可跳的最大步数，求从索引 0 到达最后索引的最少跳跃次数。
 *
 * 解法说明：
 * - 解法一（DP）：dp[i] 表示从 i 到终点的最少步数，从后往前递推。dp[i]=min(dp[i+j]+1) 其中 j 为可达步数。
 * - 解法二（贪心）：维护 end 为当前步能到达的右边界，maxPosition 为从 [0,end] 起跳能到达的最远位置；
 *   当 i==end 时说明必须再跳一步，步数+1 并更新 end=maxPosition。
 *
 * 时间复杂度：DP O(n^2)，贪心 O(n)
 * 空间复杂度：DP O(n)，贪心 O(1)
 *
 * 边界与注意事项：
 * - 单元素数组返回 0（已在终点）
 *
 * 示例：nums=[2,3,1,1,4] → 2（0→1→4）
 */
public class LC45jump {

    /**
     * DP：dp[i] 为从 i 到 n-1 的最少跳跃次数
     *
     * 关键点：从后往前，dp[i] = min(dp[i+1],...,dp[i+nums[i]]) + 1
     *
     * @param nums 每格最大跳跃步数
     * @return 最少跳跃次数
     */
    public int jump(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];

        dp[n - 1] = 0;
        for (int i = n - 2; i >= 0; i--) {
            dp[i] = n + 1;   // 初始为不可达
            for (int j = 1; j <= nums[i] && i + j < n; j++) {
                dp[i] = Math.min(dp[i], dp[i + j] + 1);
            }
        }
        return dp[0];
    }

    /**
     * 贪心：每次在 current 步能到达的区间内，选择能跳最远的位置作为下一步的起点
     *
     * 关键点：end 为 current 步的右边界，i 到达 end 时说明必须再跳一步，steps++
     *
     * @param nums 每格最大跳跃步数
     * @return 最少跳跃次数
     */
    public int jumpBetter(int[] nums) {
        if (nums == null || nums.length <= 1) return 0;
        int steps = 0;
        int maxPosition = 0;   // 从当前步区间内起跳能到达的最远位置
        int end = 0;           // 当前步能到达的右边界

        for (int i = 0; i < nums.length - 1; i++) {
            maxPosition = Math.max(maxPosition, i + nums[i]);
            if (i == end) {    // 到达当前步边界，必须再跳一步
                end = maxPosition;
                steps++;
                if (end >= nums.length - 1) break;
            }
        }
        return steps;
    }

    public static void main(String[] args) {
        LC45jump lc45jump = new LC45jump();
        int[] nums = new int[]{0};

        int res = lc45jump.jumpBetter(nums);
        System.out.println(res);
    }
}