package leetCode;

public class LC80removeDuplicates {

    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (n <= 2) {
            return nums.length;
        }
        int slow = 2, fast = 2;
        while (fast < n) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }
}
