package round3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
