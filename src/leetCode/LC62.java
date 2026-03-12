package leetCode;

/**
 * LC62 - 不同路径
 *
 * 题目（概要）：机器人从 (0,0) 到 (m-1,n-1)，每次只能向下或向右移动一格，求有多少条不同路径。
 *
 * 解法说明：
 * - 动态规划，dp[i][j] 表示到 (i,j) 的路径数
 * - dp[i][j] = dp[i-1][j] + dp[i][j-1]；第一行和第一列均为 1
 *
 * 时间复杂度：O(m * n)
 * 空间复杂度：O(m * n)
 *
 * 示例：m=3, n=7 → 28
 */
public class LC62 {

    /**
     * 计算从左上角到右下角的不同路径数
     *
     * @param m 行数
     * @param n 列数
     * @return 路径数
     */
    public int uniquePaths(int m, int n) {
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
