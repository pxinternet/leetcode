package leetCode;

/**
 * LC64minPathSum - 最小路径和
 *
 * 题目（概要）：给定 m×n 网格 grid，从左上角到右下角，每次向下或向右移动，求路径上数字总和的最小值。
 *
 * 解法说明：
 * - 动态规划，dp[i][j] 表示从 (0,0) 到 (i,j) 的最小路径和
 * - dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + grid[i][j]；首行首列单独初始化
 *
 * 时间复杂度：O(m * n)
 * 空间复杂度：O(m * n)
 *
 * 示例：grid = [[1,3,1],[1,5,1],[4,2,1]] → 7（1→3→1→1→1）
 */
public class LC64minPathSum {

    /**
     * 计算从左上到右下的最小路径和
     *
     * @param grid 网格，每个格子的权值
     * @return 最小路径和
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        dp[0][0] = grid[0][0];

        for(int i = 1; i < m; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }

        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j-1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m -1 ][n -1];
    }
}
