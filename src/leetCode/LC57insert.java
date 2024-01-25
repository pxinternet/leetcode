package leetCode;

import java.util.ArrayList;
import java.util.List;

public class LC57insert {

    public int[][] insert(int[][] intervals, int[] newInterval) {
        //如果无重叠的话，那么end也是无重叠

        List<int[]> res = new ArrayList<>();

        boolean newMerged = false;

        for (int[] interval : intervals) {
            if (interval[1] < newInterval[0]) {
                res.add(interval);
            } else if (interval[0] > newInterval[1]) {
                if (!newMerged) {
                    res.add(newInterval);
                    newMerged = true;
                }
                res.add(interval);
            } else {
                newInterval[0] = Math.min(interval[0], newInterval[0]);
                newInterval[1] = Math.max(interval[1], newInterval[1]);

            }
        }

        if (!newMerged) {
            res.add(newInterval);
        }

        int[][] resArray = new int[res.size()][2];
        for (int i = 0; i < res.size(); i++) {
            resArray[i] = res.get(i);
        }
        return resArray;
    }
}
