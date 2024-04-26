package leetCode;

import java.util.Arrays;

public class LC435eraseOverlapIntervals {

    public int  eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) return 0;

        Arrays.sort(intervals, (o1, o2) -> o1[1] - o2[1]);

        int n = intervals.length;
        int count = 1;

        int end = intervals[0][1];

        for (int i = 1; i < n; i++) {
            if (intervals[i][0] >= end) {
                count++;
                end = intervals[i][1];
            }
        }
        return n - count;
    }
}
