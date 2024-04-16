package leetCode;

public class LC41firstMissingPositive {


    //对于一个长度为 NNN 的数组，其中没有出现的最小正整数只能在 [1,N+1][1, N+1][1,N+1] 中。这是因为如果 [1,N][1, N][1,N] 都出现了，那么答案是 N+1N+1N+1，否则答案是 [1,N][1, N][1,N] 中没有出现的最小正整数。这样一来，我们将所有在 [1,N][1, N][1,N] 范围内的数放入哈希表，也可以得到最终的答案。而给定的数组恰好长度为 NNN，这让我们有了一种将数组设计成哈希表的思路：
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }


    public int firstMissingPositiveMark(int[] nums) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (nums[i] <= 0) {
                nums[i] = n + 1;
            }
        }

        for (int i = 0; i < n; i++) {
            int num = Math.abs(nums[i]);
            if (num <= n) {
                nums[num - 1] = -Math.abs(nums[num - 1]);
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                return i+1;
            }
        }
        return n + 1;
    }

}
