package leetCode;

import java.util.Arrays;

/**
 * LC132 - 分割回文串 II
 *
 * 题目（概要）：给定字符串 s，求最少需要几次分割，使得分割后的每一段都是回文串。
 *
 * 算法原理：
 * - 最优子结构：若在位置 j 分割，则 s[0..i] 的最少分割 = min(1 + s[j+1..i] 的最少分割)，其中 s[j+1..i] 为回文。
 * - 状态定义：f[i] = s[0..i] 的最少分割次数；g[i][j] = s[i..j] 是否为回文。
 * - 设计动机：先 O(n^2) 预处理回文，再 DP 枚举最后一段的起点，保证转移只依赖已算出的子问题。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：g[i][j] 从右下往左上填，g[i][j] = (s[i]==s[j]) && g[i+1][j-1]；对角线 g[i][i]=true。
 * - 步骤 2：f[i]：若 g[0][i] 则 f[i]=0；否则 f[i] = min(f[j]+1)，j 满足 g[j+1][i]=true。
 * - 步骤 3：g 的填表顺序保证 g[i+1][j-1] 先于 g[i][j] 计算。
 *
 * 关键洞察：
 * - g 必须从 i 大往 i 小、j 小往 j 大填，否则子区间未计算。
 * - 单字符、空串分割次数为 0；若整串已是回文则 f[n-1]=0。
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n^2)
 *
 * 示例：s="aab" → 1（分割成 "aa" 和 "b"）
 */
public class LC132minCut {

    /**
     * 求使每段均为回文的最少分割次数
     *
     * 关键点：g[i][j] 从 i 大 j 小往 i 小 j 大填；f 从 0 到 n-1 递推
     *
     * @param s 字符串
     * @return 最少分割次数
     */
    public int minCut(String s) {
        if (s == null || s.length() <= 1) return 0;

        int n = s.length();
        boolean[][] g = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(g[i], true);
        }

        // g[i][j]：s[i..j] 是否回文；从右下往左上填，保证 g[i+1][j-1] 已计算
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                g[i][j] = (s.charAt(i) == s.charAt(j)) && g[i + 1][j - 1];  // 两端相等且内部回文
            }
        }

        // f[i]：s[0..i] 的最少分割次数
        int[] f = new int[n];
        Arrays.fill(f, Integer.MAX_VALUE);

        for (int i = 0; i < n; i++) {
            if (g[0][i]) {
                f[i] = 0;   // s[0..i] 整体回文，无需分割
            } else {
                for (int j = 0; j < i; j++) {
                    if (g[j + 1][i]) {   // s[j+1..i] 回文，则可在 j 处割一刀
                        f[i] = Math.min(f[i], f[j] + 1);
                    }
                }
            }
        }
        return f[n - 1];
    }
}
