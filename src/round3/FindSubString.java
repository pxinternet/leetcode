package round3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindSubString {

    public List<Integer> findSunString(String s, String[] words) {
        List<Integer> res = new ArrayList<>();

        Map<String, Integer> wordsCount = new HashMap<>();

        int wordLength = words[0].length();

        int n = s.length();

        int sum = 0;
        for (String word : words) {
            sum += word.length();
            wordsCount.put(word, wordsCount.getOrDefault(word, 0) + 1);
        }

        if (sum > n)
            return res;

        Map<String, Integer> wordsMap = new HashMap<>();

        String[] subStrings = new String[n - sum + 1];

        for (int i = 0; i < n - sum + 1; i++) {
            subStrings[i] = s.substring(i, i + sum);
        }

        for (int i = 0; i < subStrings.length; i++) {
            wordsMap.clear();

            int j = 0;

            while (j < words.length) {
                String word = subStrings[i].substring(j * wordLength, (j + 1) * wordLength);

                if (wordsCount.containsKey(word)) {
                    int counts = wordsCount.getOrDefault(word, 0);
                    int currentCounts = wordsMap.getOrDefault(word, 0);
                    if (counts > 0 && counts > currentCounts) {
                        wordsMap.put(word, currentCounts + 1);
                    } else {
                        break;
                    }

                } else {
                    break;

                }
            }

            if (j == words.length) {
                res.add(i);
            }
        }

        return res;


    }

}
