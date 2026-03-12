package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * LC56merge - 合并区间
 *
 * 题目（概要）：给定区间数组 intervals，其中 intervals[i] = [start_i, end_i]，合并所有重叠区间，
 * 返回不重叠的区间数组。
 *
 * 解法说明：
 * - 核心思想：按左端点排序，遍历时若当前区间与上一段重叠则扩展右端点，否则开启新区间
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(log n) 排序栈
 *
 * 边界与注意事项：
 * - 空数组需单独处理
 *
 * 示例：[[1,3],[2,6],[8,10],[15,18]] → [[1,6],[8,10],[15,18]]
 */
public class LC56merge {

    /**
     * 合并重叠区间
     *
     * @param intervals 区间数组，每个区间为 [start, end]
     * @return 合并后的不重叠区间数组
     */
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return new int[0][];

        List<int[]> resList = new ArrayList<>();
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        int start = intervals[0][0];
        int end = intervals[0][1];

        for (int[] interval : intervals) {
            if (end < interval[0]) {
                resList.add(new int[]{start, end});
                start = interval[0];
                end = interval[1];
            } else {
                end = Math.max(end, interval[1]);
            }
        }
        resList.add(new int[]{start, end});

        return resList.toArray(new int[0][]);
    }
}
