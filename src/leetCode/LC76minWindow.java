package leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * LC76 - 最小覆盖子串
 *
 * 题目（概要）：在 s 中找包含 t 所有字符的最短子串（t 中字符出现次数不少于 t 中该字符个数）。若不存在返回 ""。
 *
 * 算法原理：
 * - 滑动窗口单调性：右边界扩展只会增加窗口内字符，不会减少覆盖性；左边界收缩只会减少字符。故可在「覆盖」时收缩、不覆盖时扩展。
 * - charMap 含义：初始为 t 中各字符所需数量；扩展时 charMap[c]--（窗口多了一个 c）；charMap[c]==0 表示该字符需求刚好满足；charMap[c]<0 表示多出。
 * - counter 含义：尚未满足的「字符种类数」。counter==0 表示所有种类都已满足（每个 charMap 中 t 的字符都<=0）。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：初始化 charMap 为 t 中各字符计数，counter=charMap.size()。
 * - 步骤 2：end 右移，s[end] 在 charMap 中则 charMap--，若变为 0 则 counter--。
 * - 步骤 3：当 counter==0，窗口覆盖 t，尝试收缩：start 右移，若 s[start] 在 charMap 中则 charMap++，若变为正则 counter++；记录最小窗口。
 *
 * 关键洞察：
 * - need/valid 模式：counter 相当于「尚未满足的种类数」，为 0 即 valid。收缩条件为 counter==0。
 * - 收缩时 charMap 加回，是因为窗口减少了一个字符，该字符的「余量」减少。
 *
 * 时间复杂度：O(m+n)
 * 空间复杂度：O(k)
 *
 * 示例：s="ADOBECODEBANC", t="ABC" → "BANC"
 */
public class LC76minWindow {

    /**
     * 滑动窗口求最小覆盖子串
     *
     * 关键点：end 扩展时 charMap 减，counter 为 0 时收缩 start，charMap 加回
     *
     * @param s 主串
     * @param t 目标字符集
     * @return 最小覆盖子串，不存在则 ""
     */
    public String minWindow(String s, String t) {
        int m = s.length();
        int n = t.length();

        if (m < n) return "";




        // charMap：t 中各字符所需数量，扩展时减，收缩时加
        Map<Character, Integer> charMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            charMap.put(c, charMap.getOrDefault(c, 0) + 1);
        }

        int minIndex = 0;
        int minLength = m + 1;
        int start = 0;
        int end = 0;
        int counter = charMap.size();   // 尚未满足的字符种类数

        while (end < m) {
            char c = s.charAt(end);
            if (charMap.containsKey(c)) {
                charMap.put(c, charMap.get(c) - 1);
                if (charMap.get(c) == 0) counter--;   // 该字符需求刚好满足
            }
            end++;

            while (counter == 0) {   // 窗口已覆盖 t 所有字符，收缩左边界
                char temp = s.charAt(start);
                if (charMap.containsKey(temp)) {
                    charMap.put(temp, charMap.get(temp) + 1);
                    if (charMap.get(temp) > 0) counter++;   // 收缩导致某字符不再满足
                }
                if (end - start < minLength) {
                    minLength = end - start;
                    minIndex = start;
                }
                start++;
            }
        }



        return minLength > m ? "" : s.substring(minIndex, minIndex + minLength);
    }

    public static void main(String[] args) {
        LC76minWindow lc76minWindow = new LC76minWindow();
        String s = "bba";
        String t = "ab";

        String res = lc76minWindow.minWindow(s, t);

        System.out.println(res);

    }
}
