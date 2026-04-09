package leetCode;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * LC630 - 课程表 III
 *
 * 题目（概要）：每门课有 duration、lastDay，需在 lastDay 前完成。求最多能修几门课。
 *
 * 解法说明：
 * - 核心思想：按 lastDay 排序。贪心：按截止时间依次尝试选课；若总时间超当前 lastDay，则去掉已选中学时最长的一门（用大顶堆）。
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(n)
 *
 * 示例：courses=[[100,200],[200,1300],[1000,1250],[2000,3200]] → 3
 */
public class LC630ScheduleCourse {

    /**
     * 按 lastDay 排序 + 贪心 + 大顶堆替换
     */
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, (a, b) -> a[1] - b[1]);
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
        int total = 0;

        for (int[] c : courses) {
            total += c[0];
            heap.offer(c[0]);
            if (total > c[1]) {
                total -= heap.poll();
            }
        }
        return heap.size();
    }
}
