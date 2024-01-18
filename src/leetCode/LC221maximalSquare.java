package leetCode;

public class LC221maximalSquare {

    //动态规划
    //dp[i][j] 表示matrix i， j为 右下角的最大边长；
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] dp = new int[m][n];

        int maxSquare = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '0') {
                    dp[i][j] = 0;
                } else {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i-1][j-1], dp[i][j - 1])) + 1;
                    }
                }

                maxSquare = Math.max(maxSquare, dp[i][j]);
            }
        }
        return maxSquare * maxSquare;
    }
}
