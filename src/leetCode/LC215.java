package leetCode;

/**
 * LC215 - 数组中的第 K 个最大元素
 *
 * 题目（概要）：给定整数数组 nums 和整数 k，找出数组中第 k 个最大的元素。注意是排序后的第 k 大，不是第 k 个 distinct 元素。
 *
 * 解法说明：
 * - 核心思想：快速选择（Quick Select），基于快排的 partition，平均 O(n)
 * - 第 k 大等价于第 n-k 小（0-indexed），用 partition 将数组分为小于/等于/大于 pivot 的三部分
 * - 若 k 在左半部分则递归左半，否则递归右半
 *
 * 时间复杂度：平均 O(n)，最坏 O(n^2)
 * 空间复杂度：O(1)（不含递归栈，递归栈 O(n)）
 *
 * 边界与注意事项：
 * - k 从 1 开始，故转换为下标 n-k
 *
 * 示例：nums = [3,2,1,5,6,4], k = 2 → 5
 */
public class LC215 {

    /**
     * 快速选择求第 k 大元素
     *
     * @param nums 数组，会被原地修改
     * @param k    第 k 大，k 从 1 开始
     * @return 第 k 大的元素值
     */
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        return quickSelect(nums, 0, n - 1, n - k);
    }

    /**
     * 在 [l, r] 范围内找第 k 小（0-indexed）的元素
     *
     * @param nums 数组
     * @param l    左边界
     * @param r    右边界
     * @param k    目标下标
     * @return 第 k 小的元素
     */
    int quickSelect(int[] nums, int l, int r, int k) {
        if (l == r) return nums[k];
        int x = nums[l], i = l - 1, j = r + 1;
        while (i < j) {
            do i++; while (nums[i] < x);
            do j--; while (nums[j] > x);
            if (i < j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        if (k <= j) return quickSelect(nums, l, j, k);
        else return quickSelect(nums, j + 1, r, k);
    }

    public static void main(String[] args) {
        LC215 lc215 = new LC215();

        int[] nums = new int[]{5,4,3,2,1};
        int k = 4;

        int res = lc215.findKthLargest(nums, k);
        System.out.println(res);
    }


}
