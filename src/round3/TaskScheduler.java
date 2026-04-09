package round3;

import java.util.Arrays;
import java.util.Comparator;

/**
 * TaskScheduler - 任务调度最短完成时间（二分 + 贪心）
 *
 * 题目（概要）：tasks 为每个任务的耗时，k 个工人；每个工人同时只能做一个任务，任务不可拆分。求完成所有任务的最短时间。
 *
 * 算法原理：
 * - 二分答案：答案在 [max(tasks), sum(tasks)] 内；canFinished(limit) 判断 limit 时间内能否完成。
 * - 贪心分配：按任务耗时顺序，尽量塞满当前工人再启用下一工人；若单任务>limit 则不可行。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：二分 mid，canFinished(tasks, k, mid) 为真则 right=mid，否则 left=mid+1。
 * - 步骤 2：canFinished 中遍历任务，currentLoad+task<=limit 则累加，否则 requireProcess++、currentLoad=task。
 * - 步骤 3：requireProcess>k 或 task>limit 返回 false。
 *
 * 关键洞察：最小完成时间单调，可二分；贪心分配为最优（将任务尽量塞满工人）。
 *
 * 时间复杂度：O(n log(sum))
 * 空间复杂度：O(1)
 *
 * 示例：tasks=[1,4,5,6,2,34,2,3,1], k=3 → 最小完成时间
 */
public class TaskScheduler {

    // 主方法：计算完成所有任务的最短时间
    public static int minCompletionTime(int[] tasks, int k) {

        // 如果任务数组为空或任务数量为0，直接返回0
        if (tasks == null || tasks.length == 0)
            return 0;
        // 如果工人数小于等于0，返回-1表示无效输入
        if (k <= 0)
            return -1;

        // 左边界：任务中最大的单个任务时间（因为至少需要这么多时间完成）
        int left = Arrays.stream(tasks).max().getAsInt();
        // 右边界：所有任务时间的总和（所有任务由一个工人完成的情况）
        int right = Arrays.stream(tasks).sum();

        // 二分查找最小的完成时间
        while (left < right) {
            int mid = left + (right - left) / 2; // 计算中间值

            // 如果可以在mid时间内完成任务，缩小右边界
            if (canFinished(tasks, k, mid)) {
                right = mid;
            } else { // 否则增加左边界
                left = mid + 1;
            }
        }
        return left; // 最终左边界即为最小完成时间
    }

    // 辅助方法：判断是否可以在给定时间限制内完成任务
    private static boolean canFinished(int[] tasks, int k, int limit) {
        int requireProcess = 1; // 当前需要的工人数
        int currentLoad = 0; // 当前工人已分配的任务总时间

        for (int task : tasks) {
            // 如果单个任务时间超过限制，直接返回false
            if (task > limit)
                return false;

            // 如果当前工人可以继续分配任务
            if (currentLoad + task <= limit) {
                currentLoad += task;
            } else { // 否则需要分配给下一个工人
                requireProcess++;
                currentLoad = task; // 当前工人开始新的任务
                // 如果需要的工人数超过k，返回false
                if (requireProcess > k)
                    return false;
            }
        }
        return true; // 所有任务可以在限制时间内完成
    }

    public static void main(String[] args) {
        // 示例任务数组
        int[] tasks = { 1, 4, 5, 6, 2, 34, 2, 3, 1 };

        // 自定义排序示例：按任务时间降序排序
        Integer[] newArr = Arrays.stream(tasks).boxed().toArray(Integer[]::new);
        Arrays.sort(newArr, Comparator.reverseOrder());
        System.out.println(Arrays.toString(newArr));

        // 计算最短完成时间示例
        int k = 3; // 工人数
        int result = minCompletionTime(tasks, k);
        System.out.println("最短完成时间: " + result);
    }
}
