package leetCode;

import java.util.HashMap;
import java.util.Map;

public class LC76minWindow {

    public String minWindow(String s, String t) {
        int m = s.length();
        int n = t.length();

        if (m < n) return "";




        Map<Character, Integer> charMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            charMap.put(c, charMap.getOrDefault(c, 0) + 1);
        }

        int minIndex = 0;
        int minLength = m + 1;

        int start =0;
        int end = 0;

        int counter = charMap.size();

        while (end < m) {

            //不包含第一个字符，可以跳过
            char c = s.charAt(end);
            if (charMap.containsKey(c)) {
                charMap.put(c, charMap.get(c) - 1);
                if (charMap.get(c) == 0) counter--;
            }
            end++;

            while (counter == 0) {
                char temp = s.charAt(start);
                if (charMap.containsKey(temp)) {
                    charMap.put(temp, charMap.get(temp) + 1);
                    if (charMap.get(temp) > 0) counter++;
                }
                if (end - start < minLength) {
                    minLength = end - start;
                    minIndex = start;
                }
                start++;
            }

        }



        return minLength > m ? "" : s.substring(minIndex, minIndex + minLength);
    }

    public static void main(String[] args) {
        LC76minWindow lc76minWindow = new LC76minWindow();
        String s = "bba";
        String t = "ab";

        String res = lc76minWindow.minWindow(s, t);

        System.out.println(res);

    }
}
