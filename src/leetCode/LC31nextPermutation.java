package leetCode;

public class LC31nextPermutation {

    public void nextPermutation(int[] nums) {
        int n = nums.length;

        //一定是从后往前找，从低位开始算
        //
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
