package leetCode;

public class LC72minDistance {

    public int minDistance(String word1, String word2) {
        //最短最小最长，这些一般都
        //子问题的最优解构成原问题的最优解
        //无后效性
        //边界条件正确
        //状态转移方程正确

        int m = word1.length() ;
        int n = word2.length();

        int[][] dp = new int[m+1][n+1];

        dp[0][0] = 0;

        for(int i = 1; i < m + 1; i++) {
            //字符串 2是空的话，那么转化的步骤就是字符串长度
            dp[i][0] = i;
        }

        for (int i = 1; i < n + 1; i++) {
            //字符串1 是空的转化成2的步骤为字符串2的长度
            dp[0][i] = i;
        }

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++ ) {
                if (word1.charAt(i-1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    //字符串不相等一定要操作一步
                    //dp[i][j - 1] + 1 代表，从i个字符串转换到 j - 1个字符串的基础上我再增加一个字符,就构成了j的长度；
                    //dp[i-1][j] 代表我从i的位置再执行一次删除就转化为了 i- 1长度的到j的最小步骤
                    //dp[i - 1][j - 1] 代表我做一次替换，把两个相同的字符串消掉了
                    //可以把这个东西看成是一个从后往前的推进
                    //这个公式要从后往前理解******
                    dp[i][j] = Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j -1])) + 1;
                }
            }
        }

        return dp[m][n];
    }

}
