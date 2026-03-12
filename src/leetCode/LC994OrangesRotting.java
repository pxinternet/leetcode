package leetCode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LC994OrangesRotting - 腐烂的橘子
 *
 * 题目（概要）：网格中 0=空，1=新鲜，2=腐烂。每分钟腐烂会感染相邻新鲜橘子。求全部腐烂所需分钟数，不可能则 -1。
 *
 * 解法说明：多源 BFS，初始将所有腐烂橘子入队，每轮扩展四邻新鲜橘子并更新为腐烂，freshCount 减到 0 即完成。
 *
 * 时间复杂度：O(m*n)
 * 空间复杂度：O(m*n)
 *
 * 示例：grid=[[2,1,1],[1,1,0],[0,1,1]] → 4
 */
public class LC994OrangesRotting {

    /**
     * 多源 BFS 模拟腐烂扩散过程
     */
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
