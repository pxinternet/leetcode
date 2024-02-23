package leetCode;

import java.util.LinkedList;
import java.util.Queue;

public class LC994OrangesRotting {

    public int orangesRotting(int[][] grid) {

        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    freshCount++;
                }
            }
        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        int time = 0;

        while (!queue.isEmpty() && freshCount > 0) {
            time++;
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] point = queue.poll();
                for (int[] dir : directions) {
                    int x = point[0] + dir[0];
                    int y = point[1] + dir[1];

                    if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length) {
                        continue;
                    }

                    if (grid[x][y] == 1) {
                        grid[x][y] = 2;
                        freshCount--;
                        queue.offer(new int[] {x, y});
                    }
                }
            }
        }

        return freshCount == 0 ? time : -1;

    }
}
