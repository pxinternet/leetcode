package round2;

import java.util.Arrays;

public class PartitionArray {

    public int minDifferencePartition(int[] nums) {
        int totalSum = Arrays.stream(nums).sum();
        int target = totalSum / 2;

        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int num : nums) {
            for (int i = target; i >= num; i--) {
                dp[i] = dp[i] || dp[i - num];
            }
        }

        for (int i = target; i >= 0; i--) {
            if (dp[i]) {
                int sumA = i;
                int sumB = totalSum - i;
                return Math.abs(sumA - sumB);
            }
        }
        return -1;
    }

}
