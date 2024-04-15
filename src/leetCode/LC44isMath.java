package leetCode;

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
