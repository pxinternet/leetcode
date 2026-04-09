package leetCode;

import java.util.*;

/**
 * LC49groupAnagrams - 字母异位词分组
 *
 * 题目（概要）：给定字符串数组 strs，将字母异位词组合在一起。字母异位词指字母相同但排列不同的字符串。
 *
 * 解法说明：
 * - 核心思想：将每个字符串按字符排序后的结果作为 key，相同 key 的为异位词
 * - 用 HashMap<key, List<String>> 分组
 *
 * 时间复杂度：O(n * k log k)，n 为字符串个数，k 为字符串平均长度
 * 空间复杂度：O(n * k)
 *
 * 边界与注意事项：
 * - strs 可能为空
 *
 * 示例：["eat","tea","tan","ate","nat","bat"] → [["eat","tea","ate"],["tan","nat"],["bat"]]
 */
public class LC49groupAnagrams {

    /**
     * 将字母异位词分组
     *
     * @param strs 字符串数组
     * @return 分组后的列表，每组内为异位词
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        Map<String, List<String>> groupmap = new HashMap<>();

        for (String str : strs) {
            String key = sortStrForKey(str);
            groupmap.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }

        res.addAll(groupmap.values());
        return res;
    }

    /**
     * 将字符串按字符排序，作为异位词的 key
     */
    private String sortStrForKey(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

}
