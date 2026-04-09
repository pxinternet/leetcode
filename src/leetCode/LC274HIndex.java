package leetCode;

import java.util.Arrays;

/**
 * LC274 - H 指数
 *
 * 题目（概要）：citations[i] 为第 i 篇论文被引次数。H 指数：最大的 h 使得至少有 h 篇论文被引次数 >= h。
 *
 * 解法说明：
 * - 核心思想：排序后从大到小，找到最大的 h 使得 citations[n-1-h+1..n-1] 均 >= h，即 citations[n-h] >= h。
 * - 等价于：从后往前，h 从 0 递增，直到 citations[i] <= h 为止。
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(1)
 *
 * 示例：citations=[3,0,6,1,5] → 3（有 3 篇至少被引 3 次）
 */
public class LC274HIndex {

    /**
     * 排序后从大到小找最大 h
     *
     * 关键点：i 从 n-1 往前，h 递增，当 citations[i] <= h 时停止，此时 h 即为答案
     */
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int n = citations.length;
        int h = 0;
        int i = n - 1;

        while (i >= 0 && citations[i] > h) {
            h++;
            i--;
        }
        return h;
    }
}
