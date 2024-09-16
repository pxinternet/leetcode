package round3;

import java.util.LinkedList;
import java.util.Queue;

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
