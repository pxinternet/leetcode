package round3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FindSubString - 串联所有单词的子串（LeetCode 30）
 *
 * 题目（概要）：s 和等长单词数组 words，求 s 中所有包含 words 中全部单词（每个恰好一次）子串的起始索引。
 * 单词顺序可任意。
 *
 * 算法原理：
 * - 滑动窗口：按单词长度 wordLength 为步长，对 offset=0..wordLength-1 分别做滑动窗口。
 * - 词频匹配：need 为目标词频，window 为当前窗口词频；窗口内匹配数等于 wordCount 时记录 left。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：need 统计 words 词频；right 以 wordLength 步长移动。
 * - 步骤 2：若 word 在 need 中，更新 window、count；若 window[word]>need[word] 则收缩 left。
 * - 步骤 3：count==wordCount 时记录 left，左移一格继续找。
 * - 步骤 4：word 不在 need 中则清空窗口，left=right+wordLength。
 *
 * 关键洞察：offset 枚举避免漏掉不以 word 边界为起点的匹配；单词为单位滑动。
 *
 * 时间复杂度：O(n × wordLength)
 * 空间复杂度：O(words.length)
 *
 * 示例：s="barfoothefoobarman", words=["foo","bar"] → [0,9]
 */
public class FindSubString {

    public List<Integer> findSunString(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        // 边界检查
        if (s == null || words == null || words.length == 0) return res;

        int wordLength = words[0].length();
        int wordCount = words.length;
        int totalLen = wordLength * wordCount;
        int n = s.length();
        if (n < totalLen) return res;

        // 构建目标词频表
        Map<String, Integer> need = new HashMap<>();
        for (String w : words) need.put(w, need.getOrDefault(w, 0) + 1);

        // 对于每个可能的偏移量做滑动窗口（步长为 wordLength）
        for (int offset = 0; offset < wordLength; offset++) {
            int left = offset; // 窗口左边界
            int count = 0; // 当前窗口内匹配的单词数量
            Map<String, Integer> window = new HashMap<>();

            // 以 wordLength 为步长向右滑动
            for (int right = offset; right + wordLength <= n; right += wordLength) {
                String word = s.substring(right, right + wordLength);

                if (need.containsKey(word)) {
                    window.put(word, window.getOrDefault(word, 0) + 1);
                    count++;

                    // 当某个单词出现次数超过需要的次数时，收缩左边界直到不超标
                    while (window.get(word) > need.get(word)) {
                        String leftWord = s.substring(left, left + wordLength);
                        window.put(leftWord, window.get(leftWord) - 1);
                        left += wordLength;
                        count--;
                    }

                    // 当窗口包含了所有单词时，记录起始索引
                    if (count == wordCount) {
                        res.add(left);
                        // 移动左边界以继续寻找下一个可能的位置
                        String leftWord = s.substring(left, left + wordLength);
                        window.put(leftWord, window.get(leftWord) - 1);
                        left += wordLength;
                        count--;
                    }

                } else {
                    // 遇到不在 words 中的单词，清空窗口并跳过
                    window.clear();
                    count = 0;
                    left = right + wordLength;
                }
            }
        }

        return res;


    }

}
