package leetCode;

import java.util.*;

/**
 * LC3 - 无重复字符的最长子串
 *
 * 题目（概要）：给定字符串 s，找出其中不含有重复字符的最长子串的长度。
 *
 * 解法说明：
 * - 核心思想：滑动窗口，维护区间 [i, rk]，用 Set 记录窗口内已出现的字符
 * - 左指针 i 右移时移除 s[i-1]；右指针 rk 向右扩展直到遇到重复或到末尾
 * - 每个字符最多进入和移出窗口一次
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(min(n, |charset|))，通常为 O(1)（如 ASCII）
 *
 * 边界与注意事项：
 * - 空串返回 0
 *
 * 示例：s = "abcabcbb" → 3（"abc"）；s = "pwwkew" → 3（"wke"）
 */
public class LC3 {

    /**
     * 返回 s 中不含重复字符的最长子串长度
     *
     * @param s 输入字符串
     * @return 最长无重复子串长度
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;

        // 使用 HashSet 记录当前窗口中存在的字符，支持快速判重
        Set<Character> occ = new HashSet<>();

        int n = s.length();
        // rk 表示右指针，初始化为 -1（表示窗口为空，右边界在左侧），ans 保存最大长度
        int rk = -1, ans = 0;

        for (int i = 0; i < n; ++i) {
            // 当左指针右移一格，删除原来位置的字符
            if (i != 0) {
                occ.remove(s.charAt(i - 1));
            }
            // 将右指针向右移动，直到遇到重复字符或到达末尾
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                occ.add(s.charAt(rk + 1));
                ++rk;
            }

            // 此时窗口为 [i, rk]，长度为 rk - i + 1
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }

    public static void main(String... arg) {
        LC3 lc3 = new LC3();
        String[] tests = new String[]{"abcabcbb", "bbbbb", "pwwkew", "", "au"};
        for (String t : tests) {
            int res = lc3.lengthOfLongestSubstring(t);
            System.out.println(String.format("s=\"%s\" -> %d", t, res));
        }
        // 期望输出：3,1,3,0,2
    }

}
