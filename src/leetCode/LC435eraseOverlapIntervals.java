package leetCode;

import java.util.Arrays;

/**
 * LC435 - 无重叠区间
 *
 * 题目（概要）：给定区间列表，移除最少的区间使剩余区间两两不重叠。
 *
 * 算法原理：
 * - 贪心选择性质：按右端点升序排序后，每次选「左端点>=上一选中区间右端点」的区间中右端点最小的。这样给后续区间留出最多空间。
 * - 反证直觉：若某最优解不包含当前可选的「右端点最小」区间 A，而包含另一重叠区间 B，则用 A 替换 B 仍合法且不增加重叠，故贪心不劣。
 * - 等价于：求最大不重叠区间数，移除数 = 总数 - 最大不重叠数。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：按右端点 intervals[i][1] 升序排序。
 * - 步骤 2：count=1，end=第一个区间右端点；遍历后续区间，若 intervals[i][0]>=end 则选中，count++，更新 end。
 * - 步骤 3：返回 n - count。
 *
 * 关键洞察：
 * - 与 LC452（最少数箭）类似，都是按右端点排序后贪心；LC435 是「选不重叠」，LC452 是「箭射右端点覆盖重叠」。
 * - 按右端点而非左端点：右端点小意味着「结束得早」，给后面留空间。
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(log n)
 *
 * 示例：intervals=[[1,2],[2,3],[3,4],[1,3]] → 1（移除 [1,3]）
 */
public class LC435eraseOverlapIntervals {

    /** 返回需移除的最少区间数 */
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) return 0;

        Arrays.sort(intervals, (o1, o2) -> o1[1] - o2[1]);

        int n = intervals.length;
        int count = 1;

        int end = intervals[0][1];

        for (int i = 1; i < n; i++) {
            if (intervals[i][0] >= end) {  // 不重叠：当前左端点>=上一右端点
                count++;
                end = intervals[i][1];
            }
        }
        return n - count;
    }
}
