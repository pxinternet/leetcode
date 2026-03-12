package leetCode;

/**
 * LC72 - 编辑距离
 *
 * 题目概要：将 word1 转换为 word2 所需的最少单字符编辑次数（插入、删除、替换）。
 *
 * 解法说明：DP。dp[i][j] 表示 word1[0..i) 到 word2[0..j) 的最少步数。
 * 若 word1[i-1]==word2[j-1] 则 dp[i][j]=dp[i-1][j-1]；否则取 min(插入、删除、替换)+1。
 * 原因：子问题最优解可递推至原问题，满足 DP 条件。
 *
 * 时间复杂度：O(m * n)
 * 空间复杂度：O(m * n)
 */
public class LC72minDistance {

    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) dp[i][0] = i;
        for (int j = 1; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1])) + 1;
                }
            }
        }
        return dp[m][n];
    }
}
