package leetCode;

/**
 * LC1277 - 统计全为 1 的正方形子矩阵
 *
 * 题目（概要）：给定 0/1 矩阵，求所有元素全为 1 的正方形子矩阵个数。
 *
 * 解法说明：
 * - 核心思想：DP。dp[i][j] 表示以 (i,j) 为右下角的最大正方形边长；递推 dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1（若 matrix[i][j]==1）。
 * - 以 (i,j) 为右下角的正方形个数 = dp[i][j]（边长 1 到 dp[i][j] 各一个），累加所有 dp 即可。
 *
 * 时间复杂度：O(m * n)
 * 空间复杂度：O(m * n)
 *
 * 示例：matrix=[[0,1,1,1],[1,1,1,1],[0,1,1,1]] → 15
 */
public class LC1277countSquares {

    /**
     * DP：dp[i][j] 为以 (i,j) 为右下角的最大正方形边长，累加得总数
     */
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
