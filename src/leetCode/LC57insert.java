package leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * LC57 - 插入区间
 *
 * 题目（概要）：无重叠的区间列表按起始排序，插入新区间 newInterval，合并重叠后返回。
 *
 * 算法原理：
 * - 完备性：每个 interval 与 newInterval 的关系只有三种：完全在左（interval[1]<newInterval[0]）、有重叠（相交或包含）、完全在右（interval[0]>newInterval[1]）。
 * - 为何直接修改 newInterval：有重叠时将 interval 合并进 newInterval，不 new 对象，因为后续可能继续与后面的 interval 重叠；合并后的 newInterval 作为「当前已合并区间」传递。
 * - 为何 newMerged：保证 newInterval 只加入一次，且加入时机是「遇到第一个完全在右侧的 interval 时」，此时左侧与 newInterval 重叠的都已合并完毕。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：interval[1]<newInterval[0] → 完全在左，直接 add。
 * - 步骤 2：interval[0]>newInterval[1] → 完全在右；若 newInterval 未加入则先 add newInterval，再 add interval。
 * - 步骤 3：否则有重叠，合并：newInterval=[min(start), max(end)]，不加入 res，继续遍历。
 * - 步骤 4：遍历结束后若 newInterval 未加入（说明在所有 interval 右侧或全部重叠），则 add。
 *
 * 关键洞察：
 * - 与 LC56 合并区间的区别：LC56 是多个区间两两合并；LC57 是单个新区间与有序列表合并，可一次遍历完成。
 * - 易错：重叠判断为 interval[1]>=newInterval[0] 且 interval[0]<=newInterval[1]（即 !(interval[1]<newInterval[0]) 且 !(interval[0]>newInterval[1])）。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：intervals=[[1,3],[6,9]], newInterval=[2,5] → [[1,5],[6,9]]
 */
public class LC57insert {

    /** 插入并合并重叠区间 */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        //如果无重叠的话，那么end也是无重叠

        List<int[]> res = new ArrayList<>();

        boolean newMerged = false;

        for (int[] interval : intervals) {
            if (interval[1] < newInterval[0]) {
                res.add(interval);  // 完全在 newInterval 左侧
            } else if (interval[0] > newInterval[1]) {
                if (!newMerged) {
                    res.add(newInterval);
                    newMerged = true;
                }
                res.add(interval);  // 完全在右侧，newInterval 已合并完毕
            } else {
                // 有重叠，合并到 newInterval（不 new 对象，可能继续与后续重叠）
                newInterval[0] = Math.min(interval[0], newInterval[0]);
                newInterval[1] = Math.max(interval[1], newInterval[1]);
            }
        }

        if (!newMerged) {
            res.add(newInterval);
        }

        int[][] resArray = new int[res.size()][2];
        for (int i = 0; i < res.size(); i++) {
            resArray[i] = res.get(i);
        }
        return resArray;
    }
}
