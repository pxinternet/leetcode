package leetCode;

import java.util.List;

public class LC139wordBreak {

    public boolean wordBreak(String s, List<String>wordDict) {
        //重叠子问题
        //最优子结构
        //无后效性

        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        //dp[i] 代表 s (0 -- i - 1) 是不是合法；
        //dp[i] = dp[j] ** check[s[j..i-1]; dp[j] 代表 s[0..j-1]是不是合法
        dp[0] = true;

        for (int i = 1; i <=n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }
}
