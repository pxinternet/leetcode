package leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * LC567 - 字符串的排列
 *
 * 题目（概要）：判断 s2 是否包含 s1 的某个排列作为子串。
 *
 * 解法说明：
 * - 核心思想：滑动窗口，窗口长度固定为 s1.length()。用 map 记录 s1 各字符需求，counter 为未满足种类数。
 * - 当 counter==0 且窗口长度==s1.length() 时，找到 s1 的排列。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 示例：s1="ab", s2="eidbaooo" → true
 */
public class LC567checkInclusion {

    /**
     * 滑动窗口判断 s2 是否包含 s1 的排列
     *
     * @param s1 模式串
     * @param s2 主串
     * @return 是否包含
     */
    public boolean checkInclusion(String s1, String s2) {

        int m = s1.length();
        int n = s2.length();

        if (m > n) return false;

        Map<Character, Integer> map = new HashMap<>();

        for (char c : s1.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) +1);
        }

        int left = 0;
        int right = 0;

        int counter = map.size();

        while (right < n) {
            char c = s2.charAt(right);

            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) counter--;
            }
            right++;

            while(counter == 0) {
                char tmp = s2.charAt(left);
                if (map.containsKey(tmp)) {
                    map.put(tmp, map.get(tmp) + 1);
                    if (map.get(tmp) > 0) counter++;
                }

                if (right - left == m) {
                    return true;
                }
                left++;
            }


        }
        return false;
    }
}
