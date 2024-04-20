package leetCode;

import java.util.ArrayList;
import java.util.List;

public class LC140wordBreak {

    List<String> allPath = new ArrayList<>();
    public List<String> wordBreak(String s, List<String> wordDict) {

        backtrack(s, wordDict, new StringBuilder());
        return allPath;
    }

    public void backtrack(String s, List<String> wordDict, StringBuilder path) {
        if (s.isEmpty()) {
            allPath.add(path.toString());
            return;
        }
        int preLength = path.length();
        for (String word : wordDict) {
            if(s.startsWith(word)) {
                if (preLength > 0) {
                    path.append(" ");
                }
                path.append(word);
                backtrack(s.substring(word.length()), wordDict, path);
                path.setLength(preLength);
            }
        }
    }
}
