package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LC452findMinArrowShots {
    public int findMinArrowShots(int[][] points) {

        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] > o2[1]) return 1;
                if (o1[1] < o2[1]) return -1;
                else return 0;
            }
        });

        int pos = points[0][1];
        int ans = 1;

        for (int[] point : points) {
            if (point[0] > pos) {
                ans++;
                pos = point[1];
            }
        }
        return ans;
    }
}
