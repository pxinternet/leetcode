package round3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringPermutations {

    public static List<String> permute(String s) {
        List<String> result = new ArrayList<>();

        if (s == null || s.length() == 0)
            return result;

        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        boolean[] used = new boolean[s.length()];
        StringBuilder sb = new StringBuilder();

        backtrack(chars, sb, used, result);

        return result;

    }

    private static void backtrack(char[] chars, StringBuilder sb, boolean[] used, List<String> result) {
        if (sb.length() == chars.length) {
            result.add(sb.toString());
            return;
        }

        for (int i = 0; i < chars.length; i++) {
            if (used[i])
                continue;
            if (i > 0 && chars[i] == chars[i - 1] && !used[i - 1])
                continue;

            used[i] = true;
            sb.append(chars[i]);
            backtrack(chars, sb, used, result);
            sb.deleteCharAt(sb.length() - 1);
            used[i] = false;
        }
    }

    public static void main(String[] args) {
        String s = "aabbccc";
        List<String> res = permute(s);
        Collections.sort(res);
        System.out.println(res);
    }

}
