package leetCode;

import java.util.*;

/**
 * LC3 最长无重复字符子串（滑动窗口）
 *
 * 思路：使用滑动窗口维护当前无重复字符的区间 [i, rk]（i 为左指针，rk 为右指针），以及一个集合/映射记录窗口内已出现的字符。
 * 每个字符最多进入和移出窗口一次，因此时间复杂度 O(n)，空间复杂度 O(|charset|)，通常为 O(1)（例如 ASCII 范围）。
 */
public class LC3 {
    /**
     * 返回 s 中不含重复字符的最长子串的长度
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
