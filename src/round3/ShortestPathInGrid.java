package round3;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ShortestPathInGrid - 网格中最短路径（BFS）
 *
 * 题目（概要）：grid 中 'S' 为起点、'E' 为终点、'#' 为障碍，求最短步数。只能上下左右移动，每格一步。
 *
 * 算法原理：
 * - BFS：从 S 开始层序遍历，首次到达 E 的层数即为最短步数。
 * - 每层对应"多走一步"，自然满足最短性。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：定位 S、E；未找到返回 -1。
 * - 步骤 2：队列 (x, y, steps)；visited 标记；四方向扩展。
 * - 步骤 3：扩展时检查边界、未访问、非障碍；到达 E 返回 steps。
 *
 * 关键洞察：等权图 BFS 即最短路径；层数即步数。
 *
 * 时间复杂度：O(rows × cols)
 * 空间复杂度：O(rows × cols)
 *
 * 示例：2×2 网格 S 左上 E 右下，无障碍 → 2
 */
public class ShortestPathInGrid {

    public static int shortestPath(char[][] grid) {

        int rows = grid.length;
        if (rows == 0)
            return -1;

        int cols = grid[0].length;

        int[] start = null;
        int[] end = null;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 'S') {
                    start = new int[] { i, j };
                }
                if (grid[i][j] == 'E') {
                    end = new int[] { i, j };
                }
            }
        }

        if (start == null || end == null)
            return -1;


        boolean[][] visited = new boolean[rows][cols];

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] { start[0], start[1], 0 });
        visited[start[0]][start[1]] = true;

        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0], y = current[1], steps = current[2];

            if (x == end[0] && y == end[1]) {
                return steps;
            }

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && !visited[newX][newY]
                        && grid[newX][newY] != '#') {
                    visited[newX][newY] = true;
                    queue.offer(new int[] { newX, newY, steps + 1 });
                }
            }
        }
        return -1;
    }

}
