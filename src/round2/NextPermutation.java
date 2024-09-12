package round2;

public class NextPermutation {

    public static void nextPermutation(int[] nums) {

        int n = nums.length;
        int i = n - 2;

        //找到第一个需要替换的元素
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            int j = n - 1;
            //用更大的元素替换
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }

        //翻转i后面的元素
        reverse(nums, i + 1, n - 1);

    }
}
