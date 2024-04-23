package leetCode;

public class LC45jump {

    //动态规划
    public int jump(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];

        dp[n - 1] = 0;
        for (int i = n - 2; i >= 0; i--) {
            dp[i] = n + 1;
            for (int j = 1; j <= nums[i] && i + j < n; j++) {
                dp[i] = Math.min(dp[i] , dp[i + j] + 1);
            }
            System.out.println("dp[i] = " + dp[i]);
        }
        return dp[0];
    }

    public int jumpBetter(int[] nums) {
        int steps = 0;
        int maxPosition = 0;
        int end = 0;

        for (int i = 0; i <nums.length - 1; i++) {
            maxPosition = Math.max(maxPosition, i + nums[i]);
            System.out.println("i = " + i + " end = " + end + " maxPosition = " + maxPosition);
            if (i == end) {
                end = maxPosition;
                steps++;
                if (end >= nums.length - 1) {
                    break;
                }
            }}
        return steps;
    }

    public static void main(String[] args) {
        LC45jump lc45jump = new LC45jump();
        int[] nums = new int[]{0};

        int res = lc45jump.jumpBetter(nums);
        System.out.println(res);
    }
}