package leetCode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * LC407 - 接雨水 II
 *
 * 题目（概要）：m*n 高度图，求下雨后能接多少雨水（3D 版本）。
 *
 * 解法说明：最小堆 + BFS。边界入堆，每次取最小高度扩展，邻格可接水 = max(0, 当前高度 - 邻格高度)，邻格入堆高度取 max。
 *
 * 时间复杂度：O(m*n*log(m*n))
 * 空间复杂度：O(m*n)
 */
public class LC407trap {

    /** 返回 3D 高度图可接雨水总量 */
    public int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length == 0 || heightMap[0].length == 0) return 0;

        int m = heightMap.length;
        int n = heightMap[0].length;
        boolean[][] visited = new boolean[m][n];
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt(a -> a[2]));

        for (int i = 0; i < m; i++) {
            priorityQueue.offer(new int[] { i, 0, heightMap[i][0] });
            priorityQueue.offer(new int[] { i, n - 1, heightMap[i][n - 1] });
            visited[i][0] = true;
            visited[i][n - 1] = true;
        }

        for (int j = 1; j < n - 1; j++) {
            priorityQueue.offer(new int[] { 0, j, heightMap[0][j] });
            priorityQueue.offer(new int[] { m - 1, j, heightMap[m - 1][j] });
            visited[0][j] = true;
            visited[m - 1][j] = true;
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

                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    waterTrapped += Math.max(0, height - heightMap[nx][ny]);
                    priorityQueue.offer(new int[] { nx, ny, Math.max(height, heightMap[nx][ny]) });
                }
            }
        }
        return waterTrapped;
    }

}
