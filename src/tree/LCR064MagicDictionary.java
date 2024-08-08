package tree;

import java.util.HashSet;

public class LCR064MagicDictionary {

    private Set<String> dictionary;

    public LCR064MagicDictionary() {
        dictionary = new HashSet<>();

    }

    public void buildDict(String[] dictionary) {
        for (String word : dictionary) {
            this.dictionary.add(word);
        }
    }

    public boolean search(String searchWord) {

    }

    private boolean isOneEditDistance(String word1, String word2) {
        if (word1.length() != word2.length()) return false;

        int count = 0;

        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                count++;
                if (count > 1) {
                    return false;
                }
            }
        }

        return  count == 1;
    }

}
