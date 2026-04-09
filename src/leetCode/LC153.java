package leetCode;

/**
 * LC153 - 寻找旋转排序数组中的最小值
 *
 * 题目（概要）：已知长度 n 的数组在预先未知的某个下标处旋转，数组元素互不相同。求其中最小值。要求 O(log n)。
 *
 * 解法说明：
 * - 核心思想：二分查找。若 nums[mid] > nums[right]，最小值在右半；否则在左半（含 mid）
 * - 与 nums[right] 比较可判断 mid 在旋转点的哪一侧
 *
 * 时间复杂度：O(log n)
 * 空间复杂度：O(1)
 *
 * 示例：nums=[3,4,5,1,2] → 1
 */
public class LC153 {

    /**
     * 二分查找最小值
     *
     * 关键点：nums[mid] > nums[right] 时，最小值在 [mid+1, right]；否则在 [left, mid]
     *
     * @param nums 旋转后的升序数组（无重复）
     * @return 最小值
     */
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // nums[mid]>nums[right] 说明 mid 在旋转点左侧，最小值在右半 [mid+1, right]
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }
}
