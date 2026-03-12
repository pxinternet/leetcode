package leetCode;

import java.util.Arrays;

/**
 * LC300lengthOfLIS - 最长递增子序列
 *
 * 题目（概要）：给定整数数组 nums，求最长严格递增子序列的长度。子序列不要求连续。
 *
 * 算法原理（DP）：
 * - 最优子结构：以 nums[i] 结尾的 LIS 长度，可由所有 j<i 且 nums[j]<nums[i] 的以 nums[j] 结尾的 LIS 扩展得到。
 * - 无后效性：dp[i] 只依赖 dp[0..i-1]，与后续无关。
 *
 * 算法原理（贪心+二分）：
 * - 关键性质：raising[k] = 长度为 k+1 的 LIS 的「最小末尾」；raising 单调递增。
 * - 替换不变性：用 nums[i] 替换 raising 中第一个>=它的元素，不改变 raising 的单调性和「各长度最小末尾」的语义。
 * - 正确性：最终 len 等于 LIS 长度，因为每次替换/追加都维护了「长度 k 的最小末尾」这一不变量。
 *
 * 核心逻辑（DP 分步）：
 * - 步骤 1：dp[i]=1，以自身开头。
 * - 步骤 2：枚举 j<i，若 nums[j]<nums[i] 则 dp[i]=max(dp[i], dp[j]+1)。
 * - 步骤 3：答案为 max(dp)。
 *
 * 核心逻辑（贪心+二分分步）：
 * - 步骤 1：raising[0]=nums[0]，len=1。
 * - 步骤 2：对 nums[i]，二分找 raising 中第一个>=nums[i] 的位置 pos；raising[pos]=nums[i]。
 * - 步骤 3：若 pos==len，则 len++；最终返回 len。
 *
 * 关键洞察：
 * - 贪心+二分中，raising 只存「最小末尾」，不存真实子序列，但长度正确。
 * - 空数组返回 0。
 *
 * 时间复杂度：DP O(n^2)；贪心+二分 O(n log n)
 * 空间复杂度：O(n)
 *
 * 示例：nums = [10,9,2,5,3,7,101,18] → 4（子序列 [2,3,7,101]）
 */
public class LC300lengthOfLIS {

    /**
     * 动态规划求最长递增子序列长度
     *
     * 关键点：dp[i] 依赖于所有 j < i 且 nums[j] < nums[i] 的 dp[j]
     *
     * @param nums 整数数组
     * @return 最长递增子序列长度
     */
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;

        // dp[i]：以 nums[i] 结尾的最长递增子序列长度
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int max = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {  // nums[j] 可接在 nums[i] 前面，形成更长的 LIS
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    /**
     * 贪心 + 二分求最长递增子序列长度
     *
     * raising[k]：长度为 k+1 的递增子序列中，末尾元素的最小值；数组单调递增
     * 对每个 nums[i]，二分找到第一个 >= nums[i] 的位置并替换，保证序列尽量「小」
     *
     * @param nums 整数数组
     * @return 最长递增子序列长度
     */
    public int lengthOfLISfast(int[] nums) {
        if (nums.length == 0) return 0;

        int[] raising = new int[nums.length];
        int len = 1;
        raising[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int low = 0, high = len;
            // 二分：找第一个 >= nums[i] 的位置，替换后保持「最小末尾」性质
            while (low < high) {
                int mid = (low + high) / 2;
                if (raising[mid] >= nums[i]) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
            raising[low] = nums[i];   // 替换/追加，保证 raising 单调增
            if (low == len) {
                len++;   // 追加到末尾，说明找到更长的 LIS
            }
        }
        return len;
    }
}
