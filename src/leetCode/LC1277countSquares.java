package leetCode;

/*
 LC1277 - Count Square Submatrices with All Ones
 动态规划：dp[i][j] 表示以 i,j 为右下角的最大正方形边长（由该点向左、上、左上三个点最小值 + 1 得到）
 累加 dp 值即可得到所有以每个点为右下角的正方形总数。
 时间复杂度 O(m*n)，空间 O(m*n)（可优化为 O(n)）
*/
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
                        dp[i][j] = 1; // 边界行/列最大边长为 1
                    } else {
                        // 取左、上、左上三方向的最小边长然后 +1
                        dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i-1][j-1], dp[i][j - 1])) + 1;
                    }
                }
                ans += dp[i][j];
            }
        }
        return ans;
    }

}
