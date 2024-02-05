public class LC189rotate {

    public void rotate(int[] nums, int k) {
        int n = nums.length;

        k = n - k % n;

        //分两端来拆分
        //从0 -- k， k 到 n - 1;
        reverse(nums, 0, k - 1);
        reverse(nums, k, n -1);
        reverse(nums, 0, n - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        int n = nums.length;
        if (start < 0 || end >= n) {
            return;
        }
        while (end > start) {
            int temp = nums[end];
            nums[end] = nums[start];
            nums[start] = temp;
            end--;
            start++;
        }
    }
}
