package round3;

import java.util.Arrays;
import java.util.Comparator;

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
