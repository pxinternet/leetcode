package leetCode;

import java.util.List;

public class LC120MinimumTotal {
    //动态规划
    //dp[i][j] 代表走到节点i，j的最小路径。
    //dp[0][0] = c[0][0]
    //dp[i][j] = min(dp[i-1][j], dp[i-1][j-1]) + c[i][j];
    //如果 j = 0; dp[i][j] = dp[i -1][j] + c[i][j]
    //如果 j = i; dp[i][j] = dp[i-1][j - 1] + c[i][j]

    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();

        int[][] dp = new int[n][n];

        dp[0][0] = triangle.get(0).get(0);

        for (int i = 1; i < n; i++) {
            dp[i][0] = dp[i-1][0] + triangle.get(i).get(0);
            for (int j = 1; j < i; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i-1][j-1]) + triangle.get(i).get(j);
            }
            dp[i][i] = dp[i - 1][i - 1] + triangle.get(i).get(i);
        }

        int minRes = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            minRes = Math.min(minRes, dp[n-1][i]);
        }
        return minRes;
    }

    public int minimumTotalON(List<List<Integer>> triangle) {

        int n = triangle.size();

        int[] dp = new int[n];

        dp[0] = triangle.get(0).get(0);

        for (int i = 1; i < n; i++) {
            dp[i] = dp[i-1] + triangle.get(i).get(i);

            for (int j = i - 1; j > 0; j--) {
                dp[j] = Math.min(dp[j], dp[j-1]) + triangle.get(i).get(j);
            }

            dp[0] = dp[0] + triangle.get(i).get(0);
        }

        int minRes = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            minRes = Math.min(minRes, dp[i]);
        }
        return minRes;

    }




}
