package leetCode;

/**
 * LC87 - 扰乱字符串
 *
 * 题目（概要）：可对字符串反复做「分割+交换子串」操作。判断 s2 是否可由 s1 经过若干次扰乱得到。
 *
 * 解法说明：
 * - 典型解法：记忆化 DFS。枚举分割点 k，判断 (s1[0..k], s1[k..n]) 与 (s2 的某两段) 是否互为扰乱。不交换与交换两种情形。
 * - 本文件为占位，完整实现需 memo + dfs。
 *
 * 时间复杂度：O(n^4)
 * 空间复杂度：O(n^3)
 *
 * 示例：s1="great", s2="rgeat" → true
 */
public class LC87isScramble {

    // 完整实现待补充：dfs(i, j, len) 表示 s1[i..i+len) 与 s2[j..j+len) 是否互为扰乱
}
