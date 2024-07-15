package leetCode;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import java.util.TreeMap;

import java.util.Collections;

import java.sql.ResultSet;

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
