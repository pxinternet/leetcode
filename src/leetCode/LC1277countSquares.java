package leetCode;

public class LC1277countSquares {


    //动态规划：dp[i][j]代表点i,j能构成的最大边长的正方形

    public int countSquares(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] dp = new int[m][n];

        int ans = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    dp[i][j] = 0;
                } else {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i-1][j-1], dp[i][j - 1])) + 1;
                    }
                }
                ans += dp[i][j];
            }
        }
        return ans;
    }

}
