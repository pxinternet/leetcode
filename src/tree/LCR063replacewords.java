package tree;

import javax.security.auth.x500.X500Principal;

import java.util.spi.ToolProvider;

public class LCR063replacewords {

    public String replaceWords(List<String> directionary, String sentence) {


        Trie  trie = new Trie();

        for (String word : directionary) {
            Trie cur = trie;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                cur.children.putIfAbsent(c, new Trie());
                cur = cur.children.get(c);
            }

            cur.children.put('#', new Trie());
        }

        String[] words = sentence.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = findRoot(words[i], trie);
        }

        return String.join(" ", words);
    }

    public String findRoot(Stirng word, Trie tire) {
        StringBuffer root = new StringBuffer();
        Trie cur = tire;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.children.containsKey("#")) {
                return root.toString();
            }

            if (!cur.children.containsKey(c)) {
                return word;
            }
            root.append(c);
            cur = cur.children.get(c);
        }
        return root.toString();
    }

    class Trie {
        Map<Character, Trie> children;

        public Trie() {
            children = new HashMap<Character, Trie>();
        }
    }
}
