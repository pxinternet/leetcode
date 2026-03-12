package leetCode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * LC452 - 用最少数量的箭引爆气球
 *
 * 题目（概要）：points[i]=[xstart,xend] 表示气球水平直径，垂直射击可引爆重叠气球，求最少箭数。
 *
 * 算法原理：
 * - 贪心选择性质：按右端点升序排序。每次在「当前未覆盖气球中右端点最小者」的右端点处射箭，可覆盖所有与其重叠的气球。
 * - 反证直觉：若不在此右端点射，而偏左射，可能漏掉只与此气球重叠的气球；偏右射则浪费。故射在右端点最优。
 * - 与 LC435 的关联：都是按右端点排序；LC435 求最大不重叠数，LC452 求最少箭数（箭数=按右端点贪心后的「组数」）。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：按 xend 升序排序。
 * - 步骤 2：pos=第一个气球右端点，ans=1；遍历气球，若 point[0]>pos 则需新箭，pos=point[1]，ans++。
 * - 步骤 3：point[0]<=pos 表示与上一箭覆盖重叠，无需新箭。
 *
 * 关键洞察：
 * - 箭射在右端点：可覆盖所有左端点<=该右端点的气球（因已按右端点排序，其右端点>=当前箭位置）。
 * - 下一箭的 pos 取新气球右端点，保证不重叠组之间分界明确。
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(log n)
 *
 * 示例：points=[[10,16],[2,8],[1,6],[7,12]] → 2
 */
public class LC452findMinArrowShots {
    public int findMinArrowShots(int[][] points) {

        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] > o2[1]) return 1;
                if (o1[1] < o2[1]) return -1;
                else return 0;
            }
        });

        int pos = points[0][1];
        int ans = 1;

        for (int[] point : points) {
            if (point[0] > pos) {  // 与上一箭覆盖范围不重叠，需新箭
                ans++;
                pos = point[1];   // 新箭射在当前气球右端点
            }
        }
        return ans;
    }
}
