package leetCode;

import java.util.*;

public class LC438findAnagrams {

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (p.length() > s.length()) return res;

        Map<Character, Integer> map = new HashMap<>();
        for (char c : p.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) +1);
        }
        int counter = map.size();
        int begin = 0, end = 0;

        while (end < s.length()) {
            char c = s.charAt(end);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) counter--;
            }
            end++;

            while (counter == 0) {
                char tempc = s.charAt(begin);
                if (map.containsKey(tempc)) {
                    map.put(tempc, map.get(tempc) + 1);
                    if (map.get(tempc) > 0) counter++;
                }

                if (end - begin == p.length()) {
                    res.add(begin);
                }
                begin++;
            }
        }

        return res;

    }

    //滑动窗口的套路
    public static void slidingWindows(String s) {
        Map<Character, Integer> window = new HashMap<>();
        int left = 0; int right = 0;

        while (right < s.length()) {
            char c = s.charAt(right);
            window.put(c, window.getOrDefault(c, 0) + 1);

            right++;

            while (left < right && window.get(c) > 1) {
                char d = s.charAt(left);
                window.put(d, window.get(d) - 1);
                left++;
            }
        }
    }

    public List<Integer> findAnagramsBetter(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();

        List<Integer> res = new ArrayList<>();

        if (pLen > sLen) return res;

        int[] sCount = new int[26];
        int[] pCount = new int[26];

        for (int i = 0; i < pLen; i++) {
            sCount[s.charAt(i) - 'a']++;
            pCount[p.charAt(i) - 'a']++;
        }

        if (Arrays.equals(sCount, pCount)) {
            res.add(0);
        }

        for (int i = 0; i < sLen - pLen; i++) {
            sCount[s.charAt(i) - 'a']--;
            sCount[s.charAt(i + pLen) - 'a']++;

            if (Arrays.equals(sCount, pCount)) {
                res.add(i + 1);
            }
        }
        return res;

    }


}
