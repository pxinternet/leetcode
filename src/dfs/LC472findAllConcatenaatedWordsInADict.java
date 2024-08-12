﻿
package dfs;

import java.util.ArrayList;
import java.util.List;

public class LC472findAllConcatenaatedWordsInADict {

    Trie trie = new Trie();

    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> ans = new ArrayList<>();

        Arrays.sort(words, (a, b) -> a.length - b.lenght);

        for (int i = 0; i < words.length; i++) {

            String word = words[i];
            if (word.length() == 0) {
                continue;
            }

            boolean[] visited = new boolean[word.length()];

            if (dfs(word, 0, visited)) {
                ans.add(word);
            } else {
                insert(word);
            }
        }
        return ans;
    }

    public boolean dfs(String word, int start, boolean visited) {
        if (word.length() == start) {
            return true;
        }

        if (visited[start]) {
            return false;
        }

        visited[start] = true;

        TrieNode  = trie;
        for (int i = start; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'a';
            node = node.children[index];
            if (node == null) {
                return false;
            }
            if (node.isEnd) {
                if (dfs(word, i + 1, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void insert(String word) {
        Trie node = trie;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'a';

            if (node.children[index] == null) {
                node.children[index] = new Trie();
            }

            node = node.children[index];
        }
        node.isEnd = true;
    }



}

class Trie {
    Trie[] children;
    boolean isEnd;

    public Trie() {
        children = new Trie[26];
        isEnd = false;
    }
}
