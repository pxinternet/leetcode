package leetCode;

import java.util.Arrays;

public class LC300lengthOfLIS {

    public int lengthOfLIS(int[] nums) {
        //动态规划
        int n = nums.length;

        //定义dp[i] 为长度为i结尾的最长递增子序列
        //找dp[i] 是从什么转化来的？
        //找退出条件[]
        //max
        int[] dp = new int[n];

        Arrays.fill(dp, 1);

        int max = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }


    public int lengthOfLISfast(int[] nums) {
        if(nums.length == 0) return 0;
        int[] raising = new int[nums.length];
        int len = 1;
        raising[0] = nums[0];
        for(int i = 1; i < nums.length; i++) {
            int low = 0, high = len;
            while(low < high) {
                int mid = (low + high) / 2;
                if(raising[mid] >= nums[i]) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
            raising[low] = nums[i];
            if(low == len) {
                len++;
            }
        }
        return len;
    }
}
