package leetCode;

/**
 * LC221 - 最大正方形
 *
 * 题目概要：在由 '0' 和 '1' 组成的矩阵中，找出只包含 '1' 的最大正方形面积。
 *
 * 解法说明：DP。dp[i][j] 表示以 (i,j) 为右下角的最大正方形边长。若 matrix[i][j]=='1'，
 * 则 dp[i][j] = 1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])。
 *
 * 时间复杂度：O(m * n)
 * 空间复杂度：O(m * n)
 */
public class LC221maximalSquare {

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
