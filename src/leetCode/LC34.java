package leetCode;

public class LC34 {

    public int[] searchRange(int[] nums, int target) {
        int[] result = {-1, -1};

        int leftIndex = extremeInsertionIndex(nums, target, true);
        if (leftIndex == nums.length || nums[leftIndex] != target) {
            return result;
        }
        result[0] = leftIndex;
        result[1] = extremeInsertionIndex(nums, target, false) - 1;

        return result;
    }


    private int extremeInsertionIndex(int[] nums, int target, boolean first) {
        int low = 0;
        int high = nums.length;

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] > target || (first && nums[mid] == target)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

}
