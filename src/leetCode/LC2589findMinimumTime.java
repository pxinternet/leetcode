package leetCode;

import java.util.Arrays;

/**
 * LC2589 - 完成所有任务的最少时间
 *
 * 题目（概要）：tasks[i]=[start,end,duration]，在 [start,end] 内至少运行 duration 秒，求最少总运行时间（可复用已有运行时段）。
 *
 * 解法说明：按 end 升序排序，贪心从 end 往前填充，最大化时段复用。
 *
 * 时间复杂度：O(n * range)
 * 空间复杂度：O(maxEnd)
 */
public class LC2589findMinimumTime {

    /** 返回完成所有任务的最少运行时间 */
    public int findMinimumTime(int[][] tasks) {
        int n = tasks.length;
        
        Arrays.sort(tasks, (a, b) -> a[1] - b[1]);
        int[] run = new int[tasks[n-1][1] + 1];
        int res = 0;
        
        for (int i = 0; i < n; i++) {
            int start = tasks[i][0];
            int end = tasks[i][1];
            int duration = tasks[i][2];

            for (int j = start; j <= end; j++) {
                duration -= run[j];
            }
            res += Math.max(0, duration);

            for (int j = end; j >= 0 && duration > 0; j--) {
                if (run[j] == 0) {
                    duration--;
                    run[j] = 1;
                }
            }
        }
        return res;

    }

}
