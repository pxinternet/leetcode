package leetCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LC30FindSubString {

    public List<Integer> findSubstring(String s, String[] words) {

        List<Integer> res = new ArrayList<>();

        Map<String, Integer> wordCounts = new HashMap<>();

        int wordLength = words[0].length();

        int n = s.length();
        int sum = 0;
        for (String word : words) {
            sum += word.length();
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }

        if (sum > s.length()) return res;

        for (int i = 0; i < n - sum + 1; i++) {
            //因为字符长度是相等的，这点比较好
            int j = 0;
            Map<String, Integer> wordsMap = new HashMap<>();
            while(j < words.length) {
                String word = s.substring(i + j *wordLength, i + (j + 1) * wordLength);
                if (wordCounts.containsKey(word)) {
                    int counts = wordCounts.getOrDefault(word, 0);
                    int currentCounts = wordsMap.getOrDefault(word, 0);
                    if (counts > 0 && counts > currentCounts) {
                        wordsMap.put(word, currentCounts + 1);
                    } else {
                        break;
                    }
                } else {
                    break;
                }
                j++;
            }
            if (j == words.length) {
                res.add(i);
            }
        }

        return res;
    }


    public static void main(String[] args) {
        String str = "abcdef";

        int j = 0;
        while (j < 3) {
            String word = str.substring(0 + j * 2, 0 +(j +1) * 2);
            System.out.println(word);
            j++;
        }
    }

}
