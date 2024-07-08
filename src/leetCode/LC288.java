package leetCode;

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;
import java.util.Comparator;

public class LC288 {


    public List<String> summaryRanges(int[] nums) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int start = nums[i];
            while (i + 1 < nums.length && nums[i+1] == nums[i] + 1) {
                i++;
            }
            if (start != nums[i]) {
                result.add(start + "->" + nums[i]);
            } else {
                result.add(String.valueOf(start));
            }
        }
        return rasult;
    }


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

        for (int[] interval : intervals) {
            if (end < interval[0]) {
                resList.add(new int[]{start, end});
                start = interval[0];
                end = interval[1];
            } else {
                end = Math.max(end, interval[1]);
            }
        }

        resList.add(new int[] {start, end});

        int[][] ans = new int[resList.size()][];

        for (int i = 0; i < resList.size(); i++) {
            ans[i] = resList.get(i);
        }
        return ans;
    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();

        boolean newMerged = false;

        for (int[] interval : intervals) {
            if (interval[1] < newInterval[0]) {
                res.add(interval);
            } else if(interval[0] > newInterval[1]) {
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

        int[][] ans = new int[res.size()][2];

        for (int i = 0; i < res.size(); i++) {
            ans[i] = res.get(i);

        }
        return ans;
    }

    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (o1, o2) -> {
            if (o1[1] > o2[1]) return 1;
            if (o1[1] < o2[1]) return -1;
            return 0;
        });

        int pos = points[0][1];

        int ans = 1;

        for (int[] point : points) {
            if (pos < point[0]) {
                ans++;
                pos = point[1];
            }
        }

        return ans;
    }
}
