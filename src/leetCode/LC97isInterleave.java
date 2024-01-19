package leetCode;

public class LC97isInterleave {

    public boolean isInterleave(String s1, String s2, String s3) {

        //动态规划：dp[i][j] 的前i，j个字符是 s3的第 i，j 个字符
        //
        int m = s1.length();
        int n = s2.length();

        int s = s3.length();
        if (m + n != s) return false;

        //注意长度不是下标
       boolean[][] dp = new boolean[m + 1][n + 1];

       dp[0][0] = true;

       for (int i = 1; i < m + 1; i++) {
           dp[i][0] = dp[i-1][0] && (s1.charAt(i - 1) == s3.charAt(i - 1));
       }

       for (int j = 1; j < n + 1; j++) {
           dp[0][j] = dp[0][j - 1] && (s2.charAt(j - 1) == s3.charAt(j - 1));
       }

       for (int i = 1; i < m + 1; i++)
           for (int j = 1; j < n + 1; j++) {

                   dp[i][j] =
                           (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
                                   || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));


           }
       return dp[s1.length()][s2.length()];
    }





}
