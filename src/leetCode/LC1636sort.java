package leetCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * LC1636 - 使数组唯一的最小增量（本题实际为按频率排序）
 *
 * 题目（概要）：按元素出现频率升序排序，频率相同时按数值降序。
 *
 * 解法说明：用 Map 统计频率，自定义排序 (a,b) -> 频率相等 ? b-a : freq(a)-freq(b)。
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(n)
 */
public class LC1636sort {
    public int[] frequencySort(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        Integer[] boxedArray = Arrays.stream(nums).boxed().toArray(Integer[]::new);
        Arrays.sort(boxedArray, (a, b) -> map.get(a) == map.get(b) ? b - a : map.get(a) - map.get(b));
        return Arrays.stream(boxedArray).mapToInt(Integer::intValue).toArray();

    }
}
