package leetCode;

import java.util.*;

/**
 * LC438 - 找到字符串中所有字母异位词
 *
 * 题目（概要）：给定 s 和 p，找出 s 中所有 p 的字母异位词的起始索引。
 * 字母异位词：长度相同、各字符出现次数相同但顺序不同。
 *
 * 解法说明：
 * - 核心思想：滑动窗口，窗口长度固定为 p.length()。用 map 记录 p 中各字符需求，counter 为未满足种类数。
 * - 当 counter==0 且窗口长度等于 p 长度时，说明找到一组异位词。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)（字符集固定）
 *
 * 边界与注意事项：
 * - p 长度大于 s 时返回空列表
 *
 * 示例：s="cbaebabacd", p="abc" → [0,6]
 */
public class LC438findAnagrams {

    /**
     * 滑动窗口找所有异位词起始索引
     *
     * 关键点：counter==0 时窗口覆盖 p 的字符，再检查长度是否等于 p.length() 以排除超覆盖情况
     *
     * @param s 主串
     * @param p 模式串
     * @return 异位词起始索引列表
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (p.length() > s.length()) return res;

        Map<Character, Integer> map = new HashMap<>();
        for (char c : p.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) +1);
        }
        int counter = map.size();
        int begin = 0, end = 0;

        while (end < s.length()) {
            char c = s.charAt(end);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) counter--;
            }
            end++;

            while (counter == 0) {
                char tempc = s.charAt(begin);
                if (map.containsKey(tempc)) {
                    map.put(tempc, map.get(tempc) + 1);
                    if (map.get(tempc) > 0) counter++;
                }

                if (end - begin == p.length()) {
                    res.add(begin);
                }
                begin++;
            }
        }

        return res;

    }

    //滑动窗口的套路
    public static void slidingWindows(String s) {
        Map<Character, Integer> window = new HashMap<>();
        int left = 0; int right = 0;

        while (right < s.length()) {
            char c = s.charAt(right);
            window.put(c, window.getOrDefault(c, 0) + 1);

            right++;

            while (left < right && window.get(c) > 1) {
                char d = s.charAt(left);
                window.put(d, window.get(d) - 1);
                left++;
            }
        }
    }

    public List<Integer> findAnagramsBetter(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();

        List<Integer> res = new ArrayList<>();

        if (pLen > sLen) return res;

        int[] sCount = new int[26];
        int[] pCount = new int[26];

        for (int i = 0; i < pLen; i++) {
            sCount[s.charAt(i) - 'a']++;
            pCount[p.charAt(i) - 'a']++;
        }

        if (Arrays.equals(sCount, pCount)) {
            res.add(0);
        }

        for (int i = 0; i < sLen - pLen; i++) {
            sCount[s.charAt(i) - 'a']--;
            sCount[s.charAt(i + pLen) - 'a']++;

            if (Arrays.equals(sCount, pCount)) {
                res.add(i + 1);
            }
        }
        return res;

    }


}
