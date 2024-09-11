package round2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinDistance {

    public int minTotalDistance(int[][] grid) {
        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    rows.add(i);
                }
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    cols.add(j);
                }
            }
        }

        int medianRow = findMedian(rows);
        int medianCol = findMedian(cols);

        return calculateDistance(cols, medianCol) + calculateDistance(rows, medianRow);
    }

    private int findMedian(List<Integer> list) {
        Collections.sort(list);
        int mid = list.size() / 2;
        return list.get(mid);
    }

    private int calculateDistance(List<Integer> points, int median) {
        int distance = 0;
        for (int point : points) {
            distance += Math.abs(point - median);
        }
        return distance;
    }


}
