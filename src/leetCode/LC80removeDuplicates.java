package leetCode;

/**
 * LC80 - 删除有序数组中的重复项 II
 *
 * 题目概要：原地删除有序数组中重复元素，使每个元素最多出现两次，返回新长度。
 *
 * 解法说明：双指针。slow 为写指针，fast 为读指针。可保留条件：nums[slow-2]!=nums[fast]，
 * 即当前写入位置前两个与 fast 不同，保证最多保留两个相同值。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
public class LC80removeDuplicates {

    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (n <= 2) return n;
        int slow = 2, fast = 2;
        while (fast < n) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow++] = nums[fast];
            }
            fast++;
        }
        return slow;
    }
}
