package round3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * AutoCompleteSystem - 搜索自动补全系统（LeetCode 642 思路）
 *
 * 题目（概要）：输入字符流，每次 input(c) 返回以当前输入为前缀的补全建议，按权重降序、字典序；'#' 结束并清空。
 *
 * 算法原理：
 * - Trie：字典树存储所有词，节点存 word、weight；输入前缀后 DFS 收集子树内所有完整词。
 * - LRU 缓存：相同前缀的补全结果缓存，避免重复 Trie 遍历。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：insertWord 沿 Trie 插入，叶子设 isEnd、word、weight。
 * - 步骤 2：input(c)：'#' 清空 currentInput；否则 append，查缓存，无则 getSuggestions(prefix) 并缓存。
 * - 步骤 3：getSuggestions 沿 prefix 走到节点，collectSuggections 收集所有完整词，按 weight 降序、字典序排序。
 *
 * 关键洞察：Trie 前缀共享；缓存键为前缀字符串；weight 用于排序。
 *
 * 时间复杂度：input 均摊 O(prefix 长度 + 补全数×log(补全数))
 * 空间复杂度：O(字典总字符数 + 缓存)
 *
 * 示例：输入 "i" → ["is","ideal"...]（按权重）
 */
public class AutoCompleteSystem {

    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        String word = null;
        boolean isEnd = false;
        int weight = 0;
    }

    static class LRUCache<K, V> extends LinkedHashMap<K, V> {

        private final int capacity;

        public LRUCache(int capacity) {
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
            return size() > capacity;
        }


    }

    private TrieNode root;

    private Map<String, Integer> wordToWight;

    private StringBuilder currentInput;

    private LRUCache<String, List<String>> cache;

    public AutoCompleteSystem(List<String> dictionary, Map<String, Integer> wordToWeight, int capacity) {
        this.wordToWight = wordToWeight;
        root = new TrieNode();
        currentInput = new StringBuilder();

        cache = new LRUCache<>(capacity);

        for (String word : dictionary) {
            insertWord(word, wordToWeight.getOrDefault(word, 0));
        }

    }

    private void insertWord(String word, int weight) {
        TrieNode node = root;

        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }

        node.isEnd = true;
        node.word = word;
        node.weight = weight;
    }

    public List<String> input(char c) {
        if (c == '#') {
            currentInput = new StringBuilder();
            return Collections.emptyList();
        } else {
            currentInput.append(c);

            String prefix = currentInput.toString();

            if (cache.containsKey(prefix)) {
                return cache.get(prefix);
            }

            List<String> res = getSuggestions(prefix);

            cache.put(prefix, res);

            return res;
        }
    }

    private List<String> getSuggestions(String prefix) {
        TrieNode node = root;

        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return Collections.emptyList();
            }
            node = node.children.get(c);
        }

        List<String> suggestions = new ArrayList<>();

        collectSuggections(node, suggestions);

        suggestions.sort(
                (a, b) -> {
                    int wA = wordToWight.getOrDefault(a, 0);
                    int wB = wordToWight.getOrDefault(b, 0);

                    if (wA != wB)
                        return wB - wA;
                    return a.compareTo(b);
            }
        );

        return suggestions;
    }

    private void collectSuggections(TrieNode node, List<String> suggestions) {

        if (node.isEnd) {
            suggestions.add(node.word);
        }

        for (TrieNode child : node.children.values()) {
            collectSuggections(child, suggestions);
        }

    }
}
