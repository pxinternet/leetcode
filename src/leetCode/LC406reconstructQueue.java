package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LC406 - 根据身高重建队列
 *
 * 题目（概要）：people[i]=[h,k] 表示身高 h、前面有 k 个身高>=h。重建满足条件的排列。
 *
 * 算法原理：
 * - 贪心选择性质：先安排高的人，再安排矮的人。矮的人插入时，不会影响已插入高者的 k 值（因为 k 只计「身高>=自身」的人，矮者不计入高者的前面）。
 * - 为何插入第 k 位：对身高 h 的人，前面要有 k 个身高>=h 的。按 h 降序插入时，当前 list 中已有的都是>=h 的，故直接插入第 k 个位置（0-indexed）即可满足「前面恰有 k 个>=h」。
 * - k 升序的意义：同身高时，k 小的应在前（前面少的人排前面），保证插入顺序正确。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：按 h 降序、h 相同时 k 升序排序。
 * - 步骤 2：依次将 people[i] 插入 list 的第 k 位（list.add(k, p)）；插入后其前面恰有 k 个元素，且均为已插入的更高或同高者。
 *
 * 关键洞察：
 * - 矮者后插入不会破坏高者的 k：k 只数身高>=自己的人，矮者插入到高者前面也不影响高者「前面>=自己」的个数。
 * - 易错：排序第二条件必须为 o2[0]-o1[0]（身高降序），写成 o1[1] 会错误。
 *
 * 时间复杂度：O(n^2)（list 插入）
 * 空间复杂度：O(n)
 *
 * 示例：people=[[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]] → [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
 */
public class LC406reconstructQueue {

    /** 重建队列，使每个人前面恰有 k 个身高>=自身的人 */
    public int[][] reconstructQueue(int[][] people) {
        // h 降序、k 升序
        Arrays.sort(people, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0]);
        List<int[]> list = new ArrayList<>();

        for (int[] p : people) {
            list.add(p[1], p);  // 插入第 k 位，保证前面恰有 k 个已插入的（均>=当前身高）
        }

        return list.toArray(new int[list.size()][2]);
    }
}
