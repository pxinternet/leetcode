package leetCode;

/**
 * LC97 - 交错字符串
 *
 * 题目（概要）：判断 s3 是否可由 s1 和 s2 交错组成。交错指保持 s1、s2 内相对顺序，交替取字符。
 *
 * 解法说明：
 * - 核心思想：DP。dp[i][j] 表示 s1 的前 i 个字符与 s2 的前 j 个字符能否交错组成 s3 的前 i+j 个。
 * - 转移：dp[i][j] = (dp[i-1][j] && s1[i-1]==s3[i+j-1]) || (dp[i][j-1] && s2[j-1]==s3[i+j-1])。
 *
 * 时间复杂度：O(m * n)
 * 空间复杂度：O(m * n)
 *
 * 边界与注意事项：
 * - m+n != s3.length 直接返回 false；dp[0][0]=true
 *
 * 示例：s1="aabcc", s2="dbbca", s3="aadbbcbcac" → true
 */
public class LC97isInterleave {

    /**
     * DP 判断 s3 是否为 s1、s2 的交错
     *
     * 关键点：dp[i][j] 对应 s1[0..i)、s2[0..j)、s3[0..i+j)；s3[i+j-1] 来自 s1[i-1] 或 s2[j-1]
     *
     * @param s1 字符串 1
     * @param s2 字符串 2
     * @param s3 目标字符串
     * @return 能否交错组成
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();
        if (m + n != s3.length()) return false;

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
        }
        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
                        || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
            }
        }
        return dp[m][n];
    }
}
