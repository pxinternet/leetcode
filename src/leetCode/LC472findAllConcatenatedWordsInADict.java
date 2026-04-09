package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LC472 - 连接词
 *
 * 题目（概要）：给定字符串数组 words，找出所有可由数组中至少两个 shorter 单词连接而成的单词。
 *
 * 解法说明：
 * - 核心思想：按长度排序，短词先加入 Trie。对每个词，DFS 判断是否能被 Trie 中已有词切分（至少两段）。
 * - 若能被切分则加入结果，否则将词加入 Trie。
 *
 * 时间复杂度：O(n * L^2)，n 为词数，L 为平均长度
 * 空间复杂度：O(total chars)
 *
 * 边界与注意事项：
 * - 空串跳过；visited 防同一位置重复尝试
 *
 * 示例：words=["cat","cats","catsdogcats","dog",...] → ["catsdogcats"]
 */
public class LC472findAllConcatenatedWordsInADict {

    private static class Trie {
        Trie[] children = new Trie[26];
        boolean isEnd = false;
    }

    private final Trie trie = new Trie();

    /**
     * 按长度排序后，逐个判断能否由更短词连接
     *
     * @param words 单词数组
     * @return 所有连接词
     */
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> ans = new ArrayList<>();
        Arrays.sort(words, (a, b) -> a.length() - b.length());

        for (String word : words) {
            if (word.isEmpty()) continue;
            boolean[] visited = new boolean[word.length()];
            if (dfs(word, 0, visited)) {
                ans.add(word);
            } else {
                insert(word);
            }
        }
        return ans;
    }

    /** DFS 判断 word[start..] 能否被 Trie 中词切分（至少一段） */
    private boolean dfs(String word, int start, boolean[] visited) {
        if (start == word.length()) return true;
        if (visited[start]) return false;

        visited[start] = true;
        Trie node = trie;

        for (int i = start; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            if (node.children[idx] == null) break;
            node = node.children[idx];
            if (node.isEnd && dfs(word, i + 1, visited)) {
                return true;
            }
        }
        visited[start] = false;
        return false;
    }

    private void insert(String word) {
        Trie node = trie;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new Trie();
            }
            node = node.children[idx];
        }
        node.isEnd = true;
    }
}
