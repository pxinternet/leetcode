package leetCode;

/*
  LC115 - 不同的子序列（Distinct Subsequences）

  题目简述：给定字符串 s 和 t，计算 t 在 s 中作为子序列出现的不同方式的数量（不要求连续，但保持顺序）。

  算法思路（动态规划，DP）：
  - 定义状态：dp[i][j] 表示 s[i..]（从 i 开始到末尾）中匹配 t[j..] 的不同子序列个数。
  - 递推关系：若 s[i] == t[j]，可以选择匹配该字符（dp[i+1][j+1]）或跳过 s[i]（dp[i+1][j]），即 dp[i][j] = dp[i+1][j+1] + dp[i+1][j]；否则只能跳过 s[i]：dp[i][j] = dp[i+1][j]。
  - 边界：当 j 到达 t 的末尾（j == n）时，空串是唯一匹配，dp[i][n] = 1。若 i 到达 s 的末尾但 j 未结束，则无法匹配，dp[m][j] = 0。

  复杂度：时间 O(m*n)，空间 O(m*n)（可优化至 O(n) 通过滚动数组）。
*/
public class LC115numDistinct {

    // 计算 s 中包含 t 的不同子序列个数
    public int numDistinct(String s, String t) {
        int m = s.length(), n = t.length();
        if (m < n) {
            return 0; // s 比 t 短，不可能匹配
        }

        // dp[i][j] 表示 s[i..] 与 t[j..] 的匹配方式个数
        int[][] dp = new int[m+1][n+1];

        // 边界：当 j == n（t 完全匹配），空串匹配方式为 1
        for (int i = 0; i <=m; i++) {
            dp[i][n] = 1;
        }
        // 从后向前填表，保证使用到的子状态已计算
        for (int i = m -1; i>= 0; i--) {
            char sChar = s.charAt(i);
            for (int j = n - 1; j>= 0; j--) {
                char tChar = t.charAt(j);

                if (sChar == tChar) {
                    // 匹配 sChar 或 跳过 sChar
                    dp[i][j] = dp[i+1][j +1] + dp[i+1][j];
                } else {
                    // sChar 与 tChar 不等，只能跳过 sChar
                    dp[i][j] = dp[i + 1][j];
                }
            }
        }
        return dp[0][0];
    }
}
