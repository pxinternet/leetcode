package leetCode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * LC316 - 去除重复字母（最小字典序）
 *
 * 题目（概要）：给定字符串 s，去除重复字母使每个字母只出现一次，且结果字典序最小。
 *
 * 解法说明：
 * - 核心思想：单调栈。维护一个字典序递增的栈，遍历时：若当前字符已在栈中则跳过；
 *   否则若栈顶大于当前字符且栈顶后续还会出现，则弹出栈顶；最后将当前字符入栈。
 * - count 记录每位剩余出现次数，inStack 记录是否已在栈中。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)（字符集固定）
 *
 * 边界与注意事项：
 * - s 仅含小写字母
 *
 * 示例：s="bcabc" → "abc"；s="cbacdcbc" → "acdb"
 */
public class LC316removeDuplicateLetters {

    /**
     * 单调栈求最小字典序去重结果
     *
     * 关键点：栈顶可弹当且仅当栈顶>当前 且 栈顶后续还有（count>0）
     *
     * @param s 字符串
     * @return 去重后字典序最小的结果
     */
    public String removeDuplicateLetters(String s) {
        int[] count = new int[26];
        boolean[] inStack = new boolean[26];
        Deque<Character> stack = new LinkedList<>();

        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }

        for (char ch : s.toCharArray()) {
            count[ch - 'a']--;

            if (inStack[ch - 'a']) continue;

            while (!stack.isEmpty() && ch < stack.peek() && count[stack.peek() - 'a'] > 0) {
                inStack[stack.pop() - 'a'] = false;
            }

            stack.push(ch);
            inStack[ch - 'a'] = true;
        }

        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
            res.append(stack.pollLast());
        }
        return res.toString();
    }
}
