package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LC56merge {

    public int[][] merge(int[][] intervals) {

        List<int[]> resList = new ArrayList<>();
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] > o2[0]) return 1;
                if (o1[0] < o2[0]) return -1;
                return 0;
            }
        });

        int start = intervals[0][0];
        int end = intervals[0][1];

        for(int[] interval : intervals) {
            if (end < interval[0]) {
                resList.add(new int[]{start, end});
                start = interval[0];
                end = interval[1];
            } else {
                end = Math.max(end, interval[1]);
            }
        }
        resList.add(new int[]{start, end});

        int[][] ans = new int[resList.size()][];

        for (int i = 0; i < resList.size(); i++) {
            ans[i] = resList.get(i);
        }
        return ans;
    }
}
