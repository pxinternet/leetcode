package leetCode;

/**
 * LC981 - 环形子数组最大和（实际为 LC918 算法）
 *
 * 题目（概要）：环形数组，求最大子数组和（可跨首尾）。
 *
 * 解法说明：最大子段和 = max(非环最大和, sum - 最小子段和)。全负时 sum==minS 特判返回 maxS。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class LC981 {

    /** 返回环形数组的最大子数组和 */
    public int maxSubarraySumCircular(int[] nums) {
        int maxS = Integer.MIN_VALUE;
        int minS = 0;
        int maxF = 0, minF = 0, sum = 0;

        for (int x : nums) {
            maxF = Math.max(maxF, 0) + x;
            maxS = Math.max(maxS, maxF);
            minF = Math.min(minF, 0) + x;
            minS = Math.min(minS, minF);
            sum += x;
        }
        return sum == minS ? maxS : Math.max(maxS, sum - minS);
    }
