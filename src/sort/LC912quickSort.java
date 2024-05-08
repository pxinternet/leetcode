package sort;

public class LC912quickSort {

    public int[] sortArray(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    void quickSort(int[] nums, int l, int r) {
        if (l == r) return;
        int x = nums[l], i = l - 1, j = r + 1;

        while (i < j) {
            do i++; while( nums[i] < x);
            do j--; while(nums[j] > x);
            if (i < j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        quickSort(nums, l, j);
        quickSort(nums, j + 1, r);
    }

    public static void main(String[] args) {
        LC912quickSort lc912quickSort = new LC912quickSort();
        int[] nums = new int[]{5,4,3,2,1};
        int[] res = lc912quickSort.sortArray(nums);

    }

}
