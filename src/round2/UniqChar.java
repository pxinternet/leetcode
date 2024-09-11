package round2;

import java.util.HashMap;
import java.util.Map;

public class UniqChar {

    public int findUniqChar(String s) {
        Map<Character, Integer> charCountMap = new HashMap<>();

        for (char c : s.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < s.length(); i++) {
            if (charCountMap.get(s.charAt(i)) == 1) {
                return i;
            }
        }

        return -1;
    }
}
