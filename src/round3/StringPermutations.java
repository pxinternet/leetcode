package round3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * StringPermutations - 字符串全排列（含重复字符去重）
 *
 * 题目（概要）：给定字符串 s，返回所有不重复的全排列。重复字符产生的相同排列只保留一个。
 *
 * 算法原理：
 * - 回溯：按位填字符，used 标记已用；先排序便于去重。
 * - 去重：若 chars[i]==chars[i-1] 且 used[i-1]==false，说明同一层已选过该值，跳过。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：chars 排序；backtrack(chars, sb, used, result)。
 * - 步骤 2：sb.length()==chars.length 时加入结果。
 * - 步骤 3：遍历 i，used[i] 跳过；重复字符且 used[i-1] 未用则跳过（剪枝）。
 * - 步骤 4：used[i]=true，append，递归，回溯。
 *
 * 关键洞察：used[i-1]==false 表示同一层前面选过相同字符，当前分支重复。
 *
 * 时间复杂度：O(n! × n)
 * 空间复杂度：O(n)
 *
 * 示例："aab" → ["aab","aba","baa"]
 */
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
