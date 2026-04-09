package leetCode;

/**
 * LC162 - 寻找峰值
 *
 * 题目（概要）：数组 nums 中相邻元素不相等，找任意一个峰值元素的下标。峰值定义：大于其相邻元素（边界只需大于唯一邻元素）。
 *
 * 解法说明：
 * - 核心思想：二分。若 nums[mid] < nums[mid+1]，则右半必有峰值（可沿上升走）；否则左半必有峰值。
 *
 * 时间复杂度：O(log n)
 * 空间复杂度：O(1)
 *
 * 边界与注意事项：
 * - 题目保证相邻不等；left<right 保证 mid+1 不越界
 *
 * 示例：nums=[1,2,3,1] → 2；nums=[1,2,1,3,5,6,4] → 1 或 5
 */
public class LC162 {

    /**
     * 二分：nums[mid] < nums[mid+1] 则 right 半有峰，否则 left 半有峰
     *
     * @param nums 整数数组，相邻不等
     * @return 峰值下标
     */
    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
