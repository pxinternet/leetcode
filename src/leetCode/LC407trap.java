package leetCode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class LC407trap {

    public int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length == 0 || heightMap[0].length == 0) {
            return 0;
        }

        int m = heightMap.length;
        int n = heightMap[0].length;

        boolean[][] visisted = new boolean[m][n];
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt(a -> a[2]));

        for (int i = 0; i < m; i++) {
            priorityQueue.offer(new int[] { i, 0, heightMap[i][0] });
            priorityQueue.offer(new int[] { i, n - 1, heightMap[i][n - 1] });
            visisted[i][0] = true;
            visisted[i][n - 1] = true;
        }

        for (int j = 1; j < n - 1; j++) {
            priorityQueue.offer(new int[] { 0, j, heightMap[0][j] });
            priorityQueue.offer(new int[] { m - 1, j, heightMap[m - 1][j] });
            visisted[0][j] = true;
            visisted[m - 1][j] = true;
        }

        int[] directions = { -1, 0, 1, 0, -1 };
        int waterTrapped = 0;

        while (!priorityQueue.isEmpty()) {
            int[] cell = priorityQueue.poll();
            int x = cell[0];
            int y = cell[1];
            int height = heightMap[x][y];

            for (int k = 0; k < 4; k++) {
                int nx = x + directions[k];
                int ny = y + directions[k + 1];

                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visisted[nx][ny]) {
                    visisted[nx][ny] = true;
                    waterTrapped += Math.max(0, height - heightMap[nx][ny]);
                    priorityQueue.offer(new int[] { nx, ny, Math.max(height, heightMap[nx][ny]) });
                }
            }
        }
        return waterTrapped;
    }

}
