package leetCode;

/**
 * LC41firstMissingPositive - 缺失的第一个正数
 *
 * 题目（概要）：给定未排序整数数组，找出其中没有出现的最小正整数。要求 O(n) 时间、O(1) 空间。
 *
 * 解法一（原地哈希）：将值为 x 且 1<=x<=n 的数放到下标 x-1 处，然后扫描找第一个 nums[i]!=i+1
 *
 * 解法二（标记）：将负数及大于 n 的改为 n+1；用负号标记出现过的数，即 nums[|x|-1]=-|nums[|x|-1]|
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 示例：nums=[3,4,-1,1] → 2
 */
public class LC41firstMissingPositive {

    /**
     * 原地哈希：将 nums[i] 放到 nums[nums[i]-1]
     */
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }


    public int firstMissingPositiveMark(int[] nums) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (nums[i] <= 0) {
                nums[i] = n + 1;
            }
        }

        for (int i = 0; i < n; i++) {
            int num = Math.abs(nums[i]);
            if (num <= n) {
                nums[num - 1] = -Math.abs(nums[num - 1]);
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                return i+1;
            }
        }
        return n + 1;
    }

}
