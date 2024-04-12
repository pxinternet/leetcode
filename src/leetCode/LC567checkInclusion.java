package leetCode;

import java.util.HashMap;
import java.util.Map;

public class LC567checkInclusion {

    public boolean checkInclusion(String s1, String s2) {

        int m = s1.length();
        int n = s2.length();

        if (m > n) return false;

        Map<Character, Integer> map = new HashMap<>();

        for (char c : s1.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) +1);
        }

        int left = 0;
        int right = 0;

        int counter = map.size();

        while (right < n) {
            char c = s2.charAt(right);

            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) counter--;
            }
            right++;

            while(counter == 0) {
                char tmp = s2.charAt(left);
                if (map.containsKey(tmp)) {
                    map.put(tmp, map.get(tmp) + 1);
                    if (map.get(tmp) > 0) counter++;
                }

                if (right - left == m) {
                    return true;
                }
                left++;
            }


        }
        return false;
    }
}
