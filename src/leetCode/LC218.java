package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * LC218 - 天际线问题
 *
 * 题目（概要）：给定建筑物 [left, right, height]，输出天际线关键点 [x, height]（高度变化处）。
 *
 * 解法说明：扫描线。将左右边界转为事件 (x, ±height)，左边界高度取负以区分；按 x 排序，同 x 左边界在前。用 TreeMap 维护当前高度及计数，遇左边界加高度、遇右边界减高度，当前最大高度变化时记录关键点。
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(n)
 */
public class LC218 {
    public List<List<Integer>> getSkyLine(int[][] buildings) {
        List<List<Integer>> result = new ArrayList<>();
        List<int[]> heights = new ArrayList<>();

        for (int[] b : buildings) {
            heights.add(new int[]{b[0], -b[2]});
            heights.add(new int[]{b[1], b[2]});
        }

        Collections.sort(heights, (h1, h2) -> {
            if (h1[0] == h2[0]) return h1[1] - h2[1];
            return h1[0] - h2[0];
        });

        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(0, 1);
        int prev = 0;
        for (int[] h : heights) {
            if (h[1] < 0) {
                map.put(-h[1], map.getOrDefault(-h[1], 0) + 1);
            } else {
                if (map.get(h[1]) > 1) {
                    map.put(h[1], map.get(h[1]) - 1);
                } else {
                    map.remove(h[1]);
                }
            }

            int currentMax = map.lastKey();
            if (currentMax != prev) {
                result.add(Arrays.asList(h[0], currentMax));
                prev = currentMax;
            }
        }
        return result;
    }

}
