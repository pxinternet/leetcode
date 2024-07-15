package leetCode;

public class LC153 {

    public int findMin(int[] nums) {

        //这是另外的一种风格的二分
        int left =0; int right =nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }

}
