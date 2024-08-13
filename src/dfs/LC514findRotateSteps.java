package dfs;

import java.util.HashMap;

import javax.security.auth.kerberos.KeyTab;

import java.util.regex.Matcher;

public class LC514findRotateSteps {

    public int findRotateSteps(String ring, String key) {

        int n = ring.length();
        int m = key.length();

        int[][] dp = new int[m + 1][n];

        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        dp[0][0] = 0;

        Map<Character, List<Integer>> charToIndex = new HashMap<>();

        for (int i = 0; i < n; i++) {
            charToIndex.computeIfAbsent(ring.charAt(i), k -> new ArrayList<>()).add(i);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == Integer.MAX_VALUE) {
                    continue;
                }
                for (int k : charToIndex.get(key.charAt(i))) {
                    int diff = Math.abs(j - k);
                    int step = Math.min(diff, n - diff);
                    dp[i + 1][k] = Math.min(dp[i + 1][k], dp[i][j] + step + 1);
               }
            }
        }

        int result = Integer.MAX_VALUE;

        for (int j = 0; j < n; j++) {
            result = Math.min(result, dp[m][j]);
        }
        return result;
    }

}
