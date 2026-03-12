package leetCode;

import java.util.List;

/**
 * LC139 - 单词拆分
 *
 * 题目（概要）：给定字符串 s 和单词字典 wordDict，判断 s 能否被拆分成若干字典中的单词（可重复使用）。
 *
 * 算法原理：
 * - 最优子结构：s[0..i) 可拆分 当且仅当 存在分割点 j，使 s[0..j) 可拆分且 s[j..i) 在字典中。
 * - 无后效性：dp[i] 只依赖 dp[0..i-1] 及字典查询，与后续字符无关。
 * - 设计动机：枚举最后一个单词的起始位置 j，将问题化为子问题 s[0..j) 的拆分性。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：dp[0]=true，空串视为可拆分（base case）。
 * - 步骤 2：对 i=1..n，枚举 j=0..i-1，若 dp[j] 且 s.substring(j,i) 在 dict 中，则 dp[i]=true。
 * - 步骤 3：可剪枝：一旦 dp[i]=true 即 break 内层循环。
 *
 * 关键洞察：
 * - 将 wordDict 转为 HashSet 可把 contains 从 O(m) 降至 O(子串长度)，整体 O(n^2 * L)。
 * - 易错：dp[0]=true 不能漏，否则 "leet" 在 dict 时 dp[4] 无法从 dp[0] 转移。
 *
 * 时间复杂度：O(n^2 * L)
 * 空间复杂度：O(n)
 *
 * 示例：s="leetcode", wordDict=["leet","code"] → true
 */
public class LC139wordBreak {

    /**
     * 判断 s 能否被拆分成 wordDict 中的单词
     *
     * 关键点：枚举分割点 j，若 s[0..j) 可拆分且 s[j..i) 在字典中，则 s[0..i) 可拆分
     *
     * @param s        字符串
     * @param wordDict 单词字典
     * @return 能拆分返回 true
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;   // 空串可拆分

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                // 枚举分割点 j：s[0..j) 可拆分 且 s[j..i) 是字典中的单词
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;   // 找到一种拆分方式即可
                    break;
                }
            }
        }
        return dp[n];
    }
}
