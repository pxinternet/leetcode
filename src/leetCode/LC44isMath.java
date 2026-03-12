package leetCode;

/**
 * LC44 - 通配符匹配
 *
 * 题目概要：判断 s 是否匹配模式 p，其中 '?' 匹配任意单字符，'*' 匹配任意序列（含空）。
 *
 * 解法一：DP。dp[i][j] 表示 s[0..i) 是否匹配 p[0..j)。'*' 可匹配空(dp[i][j-1])或多字符(dp[i-1][j])。
 *
 * 解法二：贪心。遇到 '*' 记录回溯点，不匹配时回溯到上次 '*' 并尝试多匹配一个字符。
 *
 * 时间复杂度：O(m*n) / O(m+n)
 * 空间复杂度：O(m*n) / O(1)
 */
public class LC44isMath {

    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        boolean[][] dp = new boolean[m + 1][n + 1];

        dp[0][0] = true;

        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j-1];
            }
        }

        for (int i = 1; i <=m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j-1] || dp[i-1][j];
                } else if(p.charAt(j - 1) == '?' || s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }

            }
        }
        return dp[m][n];
    }


    public boolean isMatchGreedy(String s, String p) {

        int m = s.length();
        int n = p.length();

        int i = 0, j = 0;

        int iStart = -1, jStart = -1;

        while (i < m) {
            if (j < n && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?')) {
                i++;
                j++;
            } else if (j < n && p.charAt(j) == '*') {
                iStart = i;
                jStart = j++;
            } else if (iStart >= 0) {
                i = ++iStart;
                j = jStart + 1;
            } else {
                return false;
            }
        }
        while (j < n && p.charAt(j) == '*') j++;
        return j == n;
    }
}
