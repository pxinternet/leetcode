package leetCode;

import java.util.Arrays;
import java.util.HashMap;

import java.lang.reflect.Array;

import java.util.Arrays;

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
