package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LC131 - 分割回文串
 *
 * 题目概要：将字符串 s 分割成若干回文子串，返回所有可能的分割方案。
 *
 * 解法说明：预处理 f[i][j] 表示 s[i..j] 是否回文；再 DFS 枚举分割点，若 s[i..j] 回文则加入并递归 j+1。
 *
 * 时间复杂度：O(n^2) 预处理 + O(2^n) 最坏 DFS
 * 空间复杂度：O(n^2)
 */
public class LC131partition {

    private boolean[][] f;
    private final List<List<String>> ret = new ArrayList<>();
    private final List<String> ans = new ArrayList<>();
    private int n;

    public List<List<String>> partition(String s) {
        n = s.length();
        f = new boolean[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(f[i], true);
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                f[i][j] = (s.charAt(i) == s.charAt(j)) && f[i + 1][j - 1];
            }
        }
        dfs(s, 0);
        return ret;
    }

    private void dfs(String s, int i) {
        if (i == n) {
            ret.add(new ArrayList<>(ans));
            return;
        }
        for (int j = i; j < n; j++) {
            if (f[i][j]) {
                ans.add(s.substring(i, j + 1));
                dfs(s, j + 1);
                ans.remove(ans.size() - 1);
            }
        }
    }
}
