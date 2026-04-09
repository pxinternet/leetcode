package leetCode;

/**
 * LC31nextPermutation - 下一个排列
 *
 * 题目（概要）：给定整数数组的排列，原地生成字典序下一个更大的排列；若已是最大则变为最小排列。
 *
 * 解法说明：
 * - 从后往前找第一个升序对 nums[i] < nums[i+1]，即「可增大的最低位」
 * - 在 [i+1, n-1] 中找最小的大于 nums[i] 的数 nums[j]，交换
 * - 将 [i+1, n-1] 反转使其升序（得到最小后缀）
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 示例：[1,2,3] → [1,3,2]；[3,2,1] → [1,2,3]
 */
public class LC31nextPermutation {

    /**
     * 原地生成下一个排列
     *
     * @param nums 排列数组，会被原地修改
     */
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n - 2;
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }

        int begin = 0;
        if (i >= 0) {
            int j = n - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
            begin = i + 1;
        }

        reverse(nums, begin, n - 1);

    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }
}
