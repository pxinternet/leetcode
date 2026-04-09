package leetCode;

/**
 * 寻找峰值 / 最大索引 - 二分查找示例
 *
 * 题目（概要）：数组相邻不等（或可含相等），找峰值下标（大于邻居）。
 *
 * 解法说明：二分，mid 与 mid+1 比较决定向左或向右；相等时向右跳过相等段再判断。
 */
public class MaxValueIndex {

    /** 二分找峰值下标 */
    public static int findPeakElement(int[] nums) {
        int low = 0, high = nums.length - 1;

        while(low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] > nums[mid + 1]) {
                high = mid;
            } else if (nums[mid] < nums[mid + 1]){
                low = mid + 1;
            } else {
                int temp = mid;
                while (temp < high && nums[temp] == nums[temp + 1]) {
                    temp++;
                }
                if (temp == high || nums[temp] > nums[temp + 1]) {
                    high = mid;
                }    else {
                    low = temp + 1;
                }

            }
        }
        return low;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {1, 3, 8, 8, 8, 12, 12, 12, 5, 2};
        System.out.println(findPeakElement(nums));
    }
}
