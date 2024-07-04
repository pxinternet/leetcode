package leetCode;

import java.util.Arrays;

public class LC2589findMinimumTime {

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
