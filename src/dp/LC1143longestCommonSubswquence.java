package dp;

/**
 * LC1143 - 最长公共子序列
 *
 * 题目（概要）：求两字符串 text1、text2 的最长公共子序列长度（不要求连续）。
 *
 * 算法原理：
 * - 最优子结构：若 text1[i-1]==text2[j-1]，则 LCS(text1[0..i), text2[0..j)) = 1 + LCS(text1[0..i-1), text2[0..j-1))；
 *   否则 LCS = max(LCS(i-1,j), LCS(i,j-1))，即「舍弃 text1 末字符」或「舍弃 text2 末字符」中的较大者。
 * - 状态定义：dp[i][j] = LCS(text1[0..i), text2[0..j))。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：dp[i][0]=dp[0][j]=0，空串与任意串的 LCS 为 0。
 * - 步骤 2：若 text1[i-1]==text2[j-1]，dp[i][j]=dp[i-1][j-1]+1；否则 dp[i][j]=max(dp[i-1][j], dp[i][j-1])。
 *
 * 关键洞察：经典二维 DP，递推只依赖左上、左、上三格。
 */
public class LC1143longestCommonSubswquence {

    public int longestCommonSubsequence(String text1, String text2) {
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];

        dp[0][0] = 0;

        int len1 = text1.length();
        int len2 = text2.length();

        for (int i = 1; i <= len1; i++) {
            dp[i][0] = 0;
        }

        for (int j = 1; j <= len2; j++) {
            dp[0][j] = 0;
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;  // 末字符相等，纳入 LCS
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);  // 舍弃 text1 或 text2 的末字符
                }
            }
        }
        return dp[len1][len2];
    }

}
