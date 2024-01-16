package leetCode;

public class LC62 {

    public int uniquePaths(int m, int n) {
        //典型的动态规划
        //dp[i][j] 定义为到i,j的位置的走法，
        //dp[i][j] = dp[i-1][j](往下走) + dp[i][j-1](往右走)

        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }

        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] +dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

}
