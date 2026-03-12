package round2;

import java.util.HashMap;
import java.util.Map;

/**
 * UniqChar - 字符串中第一个只出现一次的字符（LeetCode 387）
 *
 * 题目（概要）：给定字符串 s，找出第一个只出现一次的字符的索引；不存在则返回 -1。
 *
 * 算法原理：
 * - 两次遍历：第一次统计每个字符出现次数；第二次按 s 顺序找第一个计数为 1 的字符。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：遍历 s，charCountMap.put(c, count+1)。
 * - 步骤 2：按索引遍历 s，若 charCountMap.get(c)==1 则返回该索引。
 * - 步骤 3：未找到返回 -1。
 *
 * 关键洞察：必须按 s 的顺序遍历才能得到"第一个"；仅哈希计数无法保证顺序。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1) 或 O(k)，k 为字符集大小
 *
 * 示例："leetcode" → 0（'l'）；"aabb" → -1
 */
public class UniqChar {

    public int findUniqChar(String s) {
        Map<Character, Integer> charCountMap = new HashMap<>();

        for (char c : s.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < s.length(); i++) {
            if (charCountMap.get(s.charAt(i)) == 1) {
                return i;
            }
        }

        return -1;
    }
}
